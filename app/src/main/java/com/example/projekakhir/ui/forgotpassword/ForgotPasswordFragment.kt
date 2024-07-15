package com.example.projekakhir.ui.forgotpassword

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentForgotPasswordBinding
import com.example.projekakhir.databinding.FragmentRegisterBinding
import com.example.projekakhir.network.model.response.forgotpw.ForgotPasswordRequestBody
import com.example.projekakhir.network.model.response.register.RegisterRequestBody
import com.example.projekakhir.utils.Resource
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.ext.safeNavigate

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeForgotPassword()
    }

    private fun observeForgotPassword() {
        binding.btnKirim.setOnClickListener { btn ->
            val email = binding.inputEmail.text.toString().trim()
            viewModel.forgotPassword(ForgotPasswordRequestBody(email))
            viewModel.forgotPasswordResponse.observe(viewLifecycleOwner){
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
                                    Toast.makeText(requireContext(),"Berhasil Kirim Email", Toast.LENGTH_SHORT).show()
                                    btn.findNavController().safeNavigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                                }
                            }
                        }
                        is Resource.Error ->{
                            showLoading(false)
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            Log.e("Error",it?.message!!)
                        }
                    }
                }
            }
        }
    }
    private fun showLoading(status:Boolean){
        if (status){
            binding.pbForgotPassword.visibility = View.VISIBLE
        }else{
            binding.pbForgotPassword.visibility = View.INVISIBLE
        }
    }
}