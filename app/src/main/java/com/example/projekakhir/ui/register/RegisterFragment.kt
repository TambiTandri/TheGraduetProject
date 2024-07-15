package com.example.projekakhir.ui.register

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentRegisterBinding
import com.example.projekakhir.network.model.response.register.RegisterRequestBody
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.ext.safeNavigate
import com.example.projekakhir.utils.Resource
import kotlin.properties.Delegates

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var selectedRole by Delegates.notNull<String>()
    private var selectedRoleEnglish by Delegates.notNull<String>()
    private val viewModel: RegisterViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRegister()
        pickRole()
        toLogin()
        binding.inputUsername.addTextChangedListener(usernameTextWatcher)
        binding.inputPassword.addTextChangedListener(passwordTextWatcher)
        binding.inputNpm.addTextChangedListener(npmTextWatcher)
        binding.inputFirstName.addTextChangedListener(firstNameTextWatcher)
        binding.inputLastName.addTextChangedListener(lastNameTextWatcher)
        binding.inputEmail.addTextChangedListener(emailTextWatcher)

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

    private val npmTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val firstNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val lastNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val emailTextWatcher = object : TextWatcher {
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

        val npmError = if (binding.inputNpm.text.isNullOrBlank()) {
            binding.inputNpmLay.error = "NPM tidak boleh kosong"
            true
        } else if (!binding.inputNpm.text.toString().all { it.isDigit() }) {
            binding.inputNpmLay.error = "Nomor NPM harus berupa angka"
            true
        } else if (binding.inputNpm.text?.length ?: 0 < 10 || binding.inputNpm.text?.length ?: 0 > 10) {
            binding.inputNpmLay.error = "Masukan NPM dengan benar"
            true
        }else{
            binding.inputNpmLay.isErrorEnabled = false
            false
        }

        val firstNameError = if (binding.inputFirstName.text.isNullOrBlank()) {
            binding.inputFirstNameLay.error = "Username tidak boleh kosong"
            true
        } else {
            binding.inputFirstNameLay.isErrorEnabled = false
            false
        }

        val lastNameError = if (binding.inputLastName.text.isNullOrBlank()) {
            binding.inputLastNameLay.error = "Username tidak boleh kosong"
            true
        } else {
            binding.inputLastNameLay.isErrorEnabled = false
            false
        }

        val emailError = if (binding.inputEmail.text.isNullOrBlank()){
            binding.inputEmailLay.error = "Email tidak boleh kosong"
            true
        }else if (!isValidEmail(binding.inputEmail.text.toString())){
            binding.inputEmailLay.error = "Email tidak valid"
            true
        }else{
            binding.inputEmailLay.isErrorEnabled = false
            false
        }
        binding.btnRegister.isEnabled = !usernameError && !passwordError &&!npmError && !firstNameError && !lastNameError && !emailError
    }

    private fun isValidEmail(email:String):Boolean{
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(Regex(emailPattern))
    }

    private fun toLogin() {
        binding.btnBack.setOnClickListener {
            findNavController().safeNavigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun observeRegister() {
        binding.btnRegister.setOnClickListener { btn ->
            val username = binding.inputUsername.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()
            val npm = binding.inputNpm.text.toString().trim()
            val firstName = binding.inputFirstName.text.toString().trim()
            val lastName = binding.inputLastName.text.toString().trim()
            val email = binding.inputEmail.text.toString().trim()

            viewModel.register(RegisterRequestBody(username,password,selectedRoleEnglish,npm,firstName,lastName,email))
            viewModel.registerResponse.observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is Resource.Loading ->{
                            showLoading(true)
                        }
                        is Resource.Success ->{
                            showLoading(false)
                            val data = it.data
                            if (data != null){
                                if (data.status == "success"){
                                    Toast.makeText(requireContext(),"Berhasil Register",Toast.LENGTH_SHORT).show()
                                    btn.findNavController().safeNavigate(R.id.action_registerFragment_to_loginFragment)
                                }
                            }
                        }
                        is Resource.Error ->{
                            showLoading(false)
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            Log.e("Error Bosqu",it?.message!!)
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

    private fun pickRole() {
        val items = listOf("Mahasiswa")
        val roleMap = mapOf(
            "Mahasiswa" to "student",
        )
        val autoComplete = binding.inputRole
        val adapter = ArrayAdapter(requireActivity(), R.layout.list_drop_down, items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                selectedRole = adapterView.getItemAtPosition(i).toString()
                selectedRoleEnglish = roleMap[selectedRole].toString()
                binding.inputNpmLay.visibility = if (selectedRole == "Mahasiswa") View.VISIBLE else View.GONE
                Toast.makeText(
                    requireActivity(),
                    "Role : $selectedRole",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }
}