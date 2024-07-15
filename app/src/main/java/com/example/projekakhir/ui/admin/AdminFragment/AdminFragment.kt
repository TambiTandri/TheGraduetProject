package com.example.projekakhir.ui.admin.AdminFragment

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekakhir.MainActivity
import com.example.projekakhir.databinding.FragmentAdminBinding
import com.example.projekakhir.network.model.response.getAllStudent.DataItem
import com.example.projekakhir.network.model.response.verified.VerifiedSklRequestBody
import com.example.projekakhir.ui.admin.AdminFragment.adapter.AdminAdapter
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.Resource

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding

    private val viewModel: AdminViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private val adminAdapter: AdminAdapter by lazy {
        AdminAdapter{dataItem, action ->
            handleVerification(dataItem,action)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.getAllStudent()
        observeAllStudent()
        observePatchVerifResponse()
    }

    private fun observePatchVerifResponse() {
        viewModel.patchVerifResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{ showLoading(true)}
                is Resource.Success ->{
                    showLoading(false)
                    Toast.makeText(requireContext(),"Verifikasi Diterima",Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    showLoading(false)
                    Toast.makeText(requireContext(),"Error Verifikasi",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeAllStudent() {
        viewModel.listStudentResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    it.data?.data?.let { dataItems ->
                        val filteredDataItem = dataItems.filter { dataItem ->
                            dataItem.data.verificationSkl == "WAITING_VERIFICATION"
                        }
                        Log.d("verif", dataItems.toString())
                        if (filteredDataItem.isNotEmpty()){
                            adminAdapter.setItems(filteredDataItem)
                        }
                    }
                }
                is Resource.Error ->{
                    showLoading(false)
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupUi() {
        binding.rvAdminSkl.apply {
            adapter = adminAdapter
            layoutManager =
                LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
        binding.logout.setOnClickListener {
            viewModel.deleteSession()
            startActivity(Intent(requireContext(),MainActivity::class.java))
            activity?.finish()
        }
    }
    private fun handleVerification(dataItem: DataItem,action:String){
        val verifiedSklRequestBody = VerifiedSklRequestBody(action)
        viewModel.patchVerif(verifiedSklRequestBody,dataItem.data.studentId)
    }

    private fun showLoading(status:Boolean){
        if (status){
            binding.pbRegister.visibility = View.VISIBLE
        }else{
            binding.pbRegister.visibility = View.INVISIBLE
        }
    }
}