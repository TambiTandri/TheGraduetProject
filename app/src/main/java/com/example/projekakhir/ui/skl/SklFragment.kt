package com.example.projekakhir.ui.skl

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.MainActivity
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentSklBinding
import com.example.projekakhir.network.model.response.verified.VerifiedSklRequestBody
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream


class SklFragment : Fragment() {
    lateinit var binding : FragmentSklBinding
    private val viewModel: SklViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }
    //lembar pengesahan
    private var selectedFileLembarPengesahanFile: File? = null
    private var selectedLembarPengesahanUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileLembarPengesahanLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedLembarPengesahanFile(it) }
    }
    private fun handleSelectedLembarPengesahanFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileLembarPengesahanFile = file
        selectedLembarPengesahanUri = uri
        binding.btnlp.isVisible = false
        binding.dltlp.isVisible = true
        binding.fileLembarPengesahan.isVisible = true
        binding.fileLembarPengesahan.setOnClickListener {
            selectedLembarPengesahanUri?.let { uri ->
                openFileLembarPengesahanIntent(uri)
            }
        }
        // Trigger upload immediately
        observeUploadValsheet()
    }
    private fun openFileLembarPengesahanIntent(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri,"application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            startActivity(intent)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(requireContext(),"Tidak ada aplikasi yang dapat membuka ini",Toast.LENGTH_SHORT).show()
        }
    }
    //artikel
    private var selectedFileArticleFile: File? = null
    private var selectedArticleUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileArticlePengesahanLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        uri?.let { handleSelectedArticleFile(it) }
    }

    private fun handleSelectedArticleFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileArticleFile = file
        selectedArticleUri = uri // simpan Uri file yang dipilih
        binding.buttonartikel.isVisible = false
        binding.dltartikel.isVisible = true
        binding.fileArtikel.isVisible = true
        binding.fileArtikel.setOnClickListener {
            selectedArticleUri?.let { uri ->
                openFileIntent(uri)
            }
        }
        // Trigger upload immediately
        observeUploadArticle()
    }

    private fun openFileIntent(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri,"application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            startActivity(intent)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(requireContext(),"Tidak ada aplikasi yang dapat membuka ini",Toast.LENGTH_SHORT).show()
        }
    }

    //sertifikat kompetensi
    private var selectedFileCompCertFile: File? = null
    private var selectedFileCompCertUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileCompCertPengesahanLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        uri?.let { handleSelectedCompCertFile(it) }
    }
    private fun handleSelectedCompCertFile(uri: Uri){
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileCompCertFile = file
        selectedFileCompCertUri = uri
        binding.btnsk.isVisible = false
        binding.dltsk.isVisible = true
        binding.fileSertifikatKompetensi.isVisible = true
        binding.fileSertifikatKompetensi.setOnClickListener {
            selectedFileCompCertUri?.let { uri ->
                openFileCompCertIntent(uri)
            }
        }
        // Trigger upload immediately
        observeUploadCompCert()
    }

    private fun openFileCompCertIntent(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri,"application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            startActivity(intent)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(requireContext(),"Tidak ada aplikasi yang dapat membuka ini",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSklBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            binding.consLay.visibility = View.GONE
        }
        binding.menuskl.setOnClickListener{
            findNavController().navigate(R.id.action_sklFragment_to_menuDashboardFragment2)
        }
        binding.logoutskl.setOnClickListener {
            clearSession()
        }
        pickFile()
        observeUploadAll()
        viewModel.getStudent()
        observeStudent()
    }

    private fun observeStudent() = with(binding){
        viewModel.getStudentByStudentIdResponse.observe(viewLifecycleOwner){ resources ->
            when(resources){
                is Resource.Success ->{
                    showLoading(false)
                    if (resources.data?.data?.verificationSkl == "WAITING_VERIFICATION"){
                        binding.constraintWaiting.visibility = View.VISIBLE
                        binding.ivCloseWaiting.setOnClickListener {
                            binding.constraintWaiting.visibility = View.GONE
                        }
                    }else if(resources.data?.data?.verificationSkl == "VERIFIED"){
                        binding.constraintAccept.visibility = View.VISIBLE
                        binding.ivCloseAcc.setOnClickListener {
                            binding.constraintAccept.visibility = View.GONE
                        }
                    }else if(resources.data?.data?.verificationSkl == "REJECTED"){
                        binding.constraintReject.visibility = View.VISIBLE
                        binding.tvMessage.text = resources.data.data.messageSkl
                        binding.ivClose.setOnClickListener{
                            binding.constraintReject.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error ->{
                    showLoading(false)
                    Toast.makeText(requireContext(),"Error: ${resources.message}",Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading ->{ showLoading(true)}
            }
        }
    }

    private fun pickFile() {
        eventPickValsheet()
        eventPickArticle()
        eventPickCompCert()
    }

    //lembar pengesahan
    private fun eventPickValsheet() {
        binding.btnlp.setOnClickListener {
            requestFileLembarPengesahanLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadValsheet(){
        selectedFileLembarPengesahanFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "validity_sheet",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadValsheet(body)
        }
        observeUploadValsheetResponse()
    }

    private fun observeUploadValsheetResponse() {
        viewModel.uploadValsheetResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            Toast.makeText(requireContext(),"Upload Lembar Pengesahan Berhasil", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is Resource.Error ->{
                    showLoading(false)
                    Log.e("UploadPdfResponse", "Error: ${resources.message}")
                }
            }
        }
    }
    //article
    private fun eventPickArticle(){
        binding.buttonartikel.setOnClickListener { requestFileArticlePengesahanLauncher.launch("application/pdf") }
    }
    private fun observeUploadArticle(){
        selectedFileArticleFile?.let { file ->
            val requestFile =RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "article",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadArticle(body)
        }
        observeUploadArticleResponse()
    }

    private fun observeUploadArticleResponse() {
        viewModel.uploadArticleResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{showLoading(true)}
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            Toast.makeText(requireContext(),"Upload Artikel berhasil",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is Resource.Error ->{
                    showLoading(false)

                }
            }
        }
    }
    //Competency Certificate
    private fun eventPickCompCert(){
        binding.btnsk.setOnClickListener { requestFileCompCertPengesahanLauncher.launch("application/pdf") }
    }
    private fun observeUploadCompCert(){
        selectedFileCompCertFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "competency_certificate",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadCompCert(body)
        }
        observeUploadCompCertResponse()
    }

    private fun observeUploadCompCertResponse() {
        viewModel.uploadCompCertResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{showLoading(true)}
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            Toast.makeText(requireContext(),"Upload Sertifikat Kompetensi Berhasil",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is Resource.Error ->{
                    showLoading(false)
                }
            }
        }
    }

    private fun observeUploadAll() {
        binding.buttonUpload.setOnClickListener {
            observeVerif()
        }
    }

    private fun observeVerif() {
        viewModel.patchVerif(verifiedSklRequestBody = VerifiedSklRequestBody(verificationSKL = "WAITING_VERIFICATION"))
        viewModel.patchVerifResponse.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        "Kirim Verif berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Kirim Verif Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun clearSession() {
        viewModel.deleteSession()
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }

    private fun showLoading(status:Boolean){
        if (status){
            binding.pbRegister.visibility = View.VISIBLE
        }else{
            binding.pbRegister.visibility = View.INVISIBLE
        }
    }
}