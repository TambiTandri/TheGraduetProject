package com.example.projekakhir.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentLoginBinding
import com.example.projekakhir.network.model.response.login.LoginRequestBody
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.ext.decodeJwtPayload
import com.example.projekakhir.utils.ext.safeNavigate
import com.example.projekakhir.utils.Resource
import org.json.JSONObject


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLogin()
        binding.btnRegister.setOnClickListener{
            findNavController().safeNavigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvLupaPassword.setOnClickListener {
            findNavController().safeNavigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.inputUsername.addTextChangedListener(usernameTextWatcher)
        binding.inputPassword.addTextChangedListener(passwordTextWatcher)
    }
    private val usernameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun validateFields() {
        val usernameError = if (binding.inputUsername.text.isNullOrBlank()) {
            binding.inputUsernameLay.error = "Username tidak boleh kosong"
            true
        } else {
            binding.inputUsernameLay.isErrorEnabled = false
            false
        }

        val passwordError = if (binding.inputPassword.text.isNullOrBlank()) {
            binding.inputPasswordLay.error = "Password tidak boleh kosong"
            true
        } else if (binding.inputPassword.text?.length ?: 0 < 6) {
            binding.inputPasswordLay.error = "Password minimal 6 karakter"
            true
        } else {
            binding.inputPasswordLay.isErrorEnabled = false
            false
        }

        binding.btnLogin.isEnabled = !usernameError && !passwordError
    }

    private fun observeLogin() {
        binding.btnLogin.setOnClickListener { btn ->
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()
            viewModel.login(LoginRequestBody(username, password))
            viewModel.loginResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading(true)
                        }

                        is Resource.Success -> {
                            showLoading(false)
                            val data = it.data
                            val jsonObject = JSONObject(it.data.toString().decodeJwtPayload())
                            Log.i("TEST NAMA ROLE", jsonObject.getString("role"))
                            if (data?.status == "success") {
                                Toast.makeText(requireContext(), "LoginBerhasil", Toast.LENGTH_LONG)
                                    .show()
                            }
                            when {
                                jsonObject.getString("role").contains("student") -> {
                                    viewModel.saveRoleUser(jsonObject.getString("role").orEmpty())
                                    findNavController().safeNavigate(R.id.action_loginFragment_to_dashboardFragment)
                                    viewModel.saveFirstNameNLastName(jsonObject.getString("first_name").orEmpty(),jsonObject.getString("last_name").orEmpty())
                                    Log.i("test first_name", jsonObject.getString("first_name"))
                                    Log.i("test last_name", jsonObject.getString("last_name"))
                                    viewModel.saveIdNSaveToken(it.data?.data.toString())
                                    viewModel.saveNpmUser(jsonObject.getString("npm").orEmpty())
                                    Log.i("test npm",jsonObject.getString("npm"))
                                    viewModel.saveEmail(jsonObject.getString("email").orEmpty())
                                    Log.i("test email",jsonObject.getString("email"))
                                }
                                jsonObject.getString("role").contains("admin") -> {
                                    findNavController().safeNavigate(R.id.action_loginFragment_to_adminFragment)
                                    viewModel.saveRoleUser(jsonObject.getString("role").orEmpty())
                                    viewModel.saveFirstNameNLastName(jsonObject.getString("first_name").orEmpty(),jsonObject.getString("last_name").orEmpty())
                                    Log.i("test first_name", jsonObject.getString("first_name"))
                                    Log.i("test last_name", jsonObject.getString("last_name"))
                                    viewModel.saveIdNSaveToken(it.data?.data.toString())
                                    viewModel.saveEmail(jsonObject.getString("email").orEmpty())
                                    Log.i("test email",jsonObject.getString("email"))
                                }

                                else -> {
                                    requireActivity().finish()
                                }
                            }
                        }

                        is Resource.Error -> {
                            showLoading(false)
                            Toast.makeText(requireContext(), "Login Gagal", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(status:Boolean){
        if (status){
            binding.pbRegister.visibility = View.VISIBLE
        }else{
            binding.pbRegister.visibility = View.INVISIBLE
        }
    }


}