package com.example.projekakhir.ui.dashboardfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.MainActivity
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentDashboardBinding
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.Resource


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeName()
        viewModel.getDoc()
        observeDoc()
        binding.logout.setOnClickListener{
            clearSession()
        }
        binding.menu2.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_menuDashboardFragment)
        }


    }

    private fun observeDoc() = with(binding){
        viewModel.getDocResponse.observe(viewLifecycleOwner){ resource ->
            when(resource){
                is Resource.Success ->{
                    resource.data?.data.let {data ->
                        if (!data?.graduationCertificate.isNullOrEmpty() && !data?.birthCertificate.isNullOrEmpty() && !data?.familyCard.isNullOrEmpty() && !data?.idCard.isNullOrEmpty() && !data?.studentCard.isNullOrEmpty() && !data?.photo.isNullOrEmpty() && !data?.tempGraduationCertificate.isNullOrEmpty() && !data?.toeicCertificate.isNullOrEmpty()){
                            ivCheck1.isVisible = false
                            ivDone1.isVisible = true
                            ivCheck2.isVisible = false
                            ivDone2.isVisible = true
                            ivCheck3.isVisible = false
                            ivDone3.isVisible = true
                            ivCheck4.isVisible = false
                            ivDone4.isVisible = true
                            ivCheck5.isVisible = false
                            ivDone5.isVisible = true
                            ivCheck6.isVisible = false
                            ivDone6.isVisible = true
                            ivCheck7.isVisible = false
                            ivDone7.isVisible = true
                            ivCheck8.isVisible = false
                            ivDone8.isVisible = true
                        }else{
                            ivCheck1.isVisible = true
                            ivDone1.isVisible = false
                            ivCheck2.isVisible = true
                            ivDone2.isVisible = false
                            ivCheck3.isVisible = true
                            ivDone3.isVisible = false
                            ivCheck4.isVisible = true
                            ivDone4.isVisible = false
                            ivCheck5.isVisible = true
                            ivDone5.isVisible = false
                            ivCheck6.isVisible = true
                            ivDone6.isVisible = false
                            ivCheck7.isVisible = true
                            ivDone7.isVisible = false
                            ivCheck8.isVisible = true
                            ivDone8.isVisible = false
                        }
                    }
                }
                is Resource.Error ->{}
                is Resource.Loading ->{}
            }
        }
    }

    private fun clearSession() {
        viewModel.deleteSession()
        startActivity(Intent(requireContext(),MainActivity::class.java))
        activity?.finish()
    }

    private fun observeName() {
        viewModel.fullName.observe(viewLifecycleOwner){ fullName ->
            binding.tvsd.text = "Selamat Datang, $fullName"
        }
    }


}