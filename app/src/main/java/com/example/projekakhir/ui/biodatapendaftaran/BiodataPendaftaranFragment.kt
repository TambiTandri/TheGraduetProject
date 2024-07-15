package com.example.projekakhir.ui.biodatapendaftaran

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.MainActivity
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentBiodataPendaftaranBinding
import com.example.projekakhir.network.model.response.biodatapendaftaran.BiodataPendaftaranRequestBody
import com.example.projekakhir.network.model.response.lecturer.DataItem
import com.example.projekakhir.network.model.response.verified.VerifiedRequestBody
import com.example.projekakhir.ui.biodatapendaftaran.adapter.LecturerAdapter
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

class BiodataPendaftaranFragment : Fragment() {
    //membuat variabel boolean untuk mendeteksi apakah setiap file sudah di upload atau belum
    private var isIjasahUploaded = false
    private var isAkteUploaded = false
    private var isKKUploaded = false
    private var isKTPUploaded = false
    private var isKTMUploaded = false
    private var isFotoUploaded = false
    private var isSKLUploaded = false
    private var isToeicUploaded = false
    private var isSkripsiUploaded = false
    private var isLembarPengesahanUploaded = false

    private val REQUEST_IMAGE_PICK = 1001
    private lateinit var binding: FragmentBiodataPendaftaranBinding
    private var tanggalLahir by Delegates.notNull<String>()
    private var tanggalLulus by Delegates.notNull<String>()
    private var tanggalWisuda by Delegates.notNull<String>()
    private var selectedGender by Delegates.notNull<String>()
    private var selectedProdi by Delegates.notNull<String>()
    private var selectedPembimbingIlmu by Delegates.notNull<String>()
    private var selectedPembimbingAgama by Delegates.notNull<String>()
    private var selectedDosenPenguji by Delegates.notNull<String>()
    private var nidnPembimbingIlmu by Delegates.notNull<String>()
    private var nidnDosenPenguji by Delegates.notNull<String>()
    private var nidnPembimbingAgama by Delegates.notNull<String>()
    private var selectedSemester by Delegates.notNull<String>()
    private var selectedAcademicYear by Delegates.notNull<String>()
    private lateinit var lecturerAdapter: ArrayAdapter<String>
    private lateinit var semesterAdapter: ArrayAdapter<String>
    private lateinit var academicAdapter: ArrayAdapter<String>

    //ijasah sma
    private var selectedFileIjasahFile: File? = null
    private var selectedFileIjasahUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileIjasahLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedIjasahFile(it) }
    }
    private fun handleSelectedIjasahFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileIjasahFile = file
        selectedFileIjasahUri = uri
        binding.uploadButton1.isVisible = false
        binding.deleteButton1.isVisible = true
        binding.ivCheckFcIjazah.isVisible = true
        binding.ivCloseFcIjazah.isVisible = false
        binding.deleteButton1.setOnClickListener {
            binding.uploadButton1.isVisible = true
            binding.ivCheckFcIjazah.isVisible = false
            binding.deleteButton1.isVisible = false
            binding.ivCloseFcIjazah.isVisible = true
        }
        // Trigger upload immediately
        observeUploadIjasah()
    }
    //akte kelahiran
    private var selectedFileAkteFile: File? = null
    private var selectedFileAkteUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileAkteLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedAkteFile(it) }
    }
    private fun handleSelectedAkteFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileAkteFile = file
        selectedFileAkteUri = uri
        binding.uploadButton2.isVisible = false
        binding.deleteButton2.isVisible = true
        binding.ivCheckFcAkte.isVisible = true
        binding.ivCloseFcAkte.isVisible = false
        binding.deleteButton2.setOnClickListener {
            binding.uploadButton2.isVisible = true
            binding.ivCheckFcAkte.isVisible = false
            binding.deleteButton2.isVisible = false
            binding.ivCloseFcAkte.isVisible = true
        }
        // Trigger upload immediately
        observeUploadAkte()
    }
    //kartu keluarga
    private var selectedFileKkFile: File? = null
    private var selectedFileKkUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileKkLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedKkFile(it) }
    }
    private fun handleSelectedKkFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileKkFile = file
        selectedFileKkUri = uri
        binding.uploadButton3.isVisible = false
        binding.deleteButton3.isVisible = true
        binding.ivCheckFcKK.isVisible = true
        binding.ivCloseFcKK.isVisible = false
        binding.deleteButton3.setOnClickListener {
            binding.uploadButton3.isVisible = true
            binding.ivCheckFcKK.isVisible = false
            binding.deleteButton3.isVisible = false
            binding.ivCloseFcKK.isVisible = true
        }
        // Trigger upload immediately
        observeUploadKk()
    }
    //ktp
    private var selectedFileKtpFile: File? = null
    private var selectedFileKtpUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileKtpLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedKtpFile(it) }
    }
    private fun handleSelectedKtpFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileKtpFile = file
        selectedFileKtpUri = uri
        binding.uploadButton4.isVisible = false
        binding.deleteButton4.isVisible = true
        binding.ivCheckFcKTP.isVisible = true
        binding.ivCloseFcKTP.isVisible = false
        binding.deleteButton4.setOnClickListener {
            binding.uploadButton4.isVisible = true
            binding.ivCheckFcKTP.isVisible = false
            binding.deleteButton4.isVisible = false
            binding.ivCloseFcKTP.isVisible = true
        }
        // Trigger upload immediately
        observeUploadKtp()
    }
    //ktm
    private var selectedFileKtmFile: File? = null
    private var selectedFileKtmUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileKtmLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedKtmFile(it) }
    }
    private fun handleSelectedKtmFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileKtmFile = file
        selectedFileKtmUri = uri
        binding.uploadButton5.isVisible = false
        binding.deleteButton5.isVisible = true
        binding.ivCheckFcKTM.isVisible = true
        binding.ivCloseFcKTM.isVisible = false
        binding.deleteButton5.setOnClickListener {
            binding.uploadButton5.isVisible = true
            binding.ivCheckFcKTM.isVisible = false
            binding.deleteButton5.isVisible = false
            binding.ivCloseFcKTM.isVisible = true
        }
        // Trigger upload immediately
        observeUploadKtm()
    }
    //foto
    private var selectedFileFotoFile: File? = null
    private var selectedFileFotoUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileFotoLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedFotoFile(it) }
    }
    private fun handleSelectedFotoFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.jpg")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileFotoFile = file
        selectedFileFotoUri = uri
        binding.uploadButton6.isVisible = false
        binding.deleteButton6.isVisible = true
        binding.ivCheckFcPasFoto.isVisible = true
        binding.ivCloseFcPasFoto.isVisible = false
        binding.deleteButton6.setOnClickListener {
            binding.uploadButton6.isVisible = true
            binding.ivCheckFcPasFoto.isVisible = false
            binding.deleteButton6.isVisible = false
            binding.ivCloseFcPasFoto.isVisible = true
        }
        // Trigger upload immediately
        observeUploadPasFoto()
    }
    //skl
    private var selectedFileSklFile: File? = null
    private var selectedFileSklUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileSklLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedSklFile(it) }
    }
    private fun handleSelectedSklFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileSklFile = file
        selectedFileSklUri = uri
        binding.uploadButton7.isVisible = false
        binding.deleteButton7.isVisible = true
        binding.ivCheckFcSKL.isVisible = true
        binding.ivCloseFcSKL.isVisible = false
        binding.deleteButton7.setOnClickListener {
            binding.uploadButton7.isVisible = true
            binding.ivCheckFcSKL.isVisible = false
            binding.deleteButton7.isVisible = false
            binding.ivCloseFcSKL.isVisible = true
        }
        // Trigger upload immediately
        observeUploadSkl()
    }
    //toeic
    private var selectedFileToeicFile: File? = null
    private var selectedFileToeicUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileToeicLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedToeicFile(it) }
    }
    private fun handleSelectedToeicFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileToeicFile = file
        selectedFileToeicUri = uri
        binding.uploadButton8.isVisible = false
        binding.deleteButton8.isVisible = true
        binding.ivCheckFcTOEIC.isVisible = true
        binding.ivCloseFcTOEIC.isVisible = false
        binding.deleteButton8.setOnClickListener {
            binding.uploadButton8.isVisible = true
            binding.ivCheckFcTOEIC.isVisible = false
            binding.deleteButton8.isVisible = false
            binding.ivCloseFcTOEIC.isVisible = true
        }
        // Trigger upload immediately
        observeUploadToeic()
    }
    //skripsi
    private var selectedFileSkripsiFile: File? = null
    private var selectedFileSkripsiUri: Uri? = null //menyimpan Uri dari file yang dipilih
    private val requestFileSkripsiLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri : Uri? ->
        uri?.let { handleSelectedSkripsiFile(it) }
    }
    private fun handleSelectedSkripsiFile(uri: Uri) {
        val file = File(requireContext().cacheDir,"temp_pdf.pdf")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedFileSkripsiFile = file
        selectedFileSkripsiUri = uri
        binding.uploadButton9.isVisible = false
        binding.deleteButton9.isVisible = true
        binding.ivCheckFcSkripsi.isVisible = true
        binding.ivCloseFcSkripsi.isVisible = false
        binding.deleteButton9.setOnClickListener {
            binding.uploadButton9.isVisible = true
            binding.ivCheckFcSkripsi.isVisible = false
            binding.deleteButton9.isVisible = false
            binding.ivCloseFcSkripsi.isVisible = true
        }
        // Trigger upload immediately
        observeUploadSkripsi()
    }
    //lembar pengesahan
    private var selectedFileLembarPengesahanFile: File? = null
    private var selectedFileLembarPengesahanUri: Uri? = null //menyimpan Uri dari file yang dipilih
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
        selectedFileLembarPengesahanUri = uri
        binding.uploadButton10.isVisible = false
        binding.deleteButton10.isVisible = true
        binding.ivCheckLembarPengesahan.isVisible = true
        binding.ivCloseLembarPengesahan.isVisible = false
        binding.deleteButton10.setOnClickListener {
            binding.uploadButton10.isVisible = true
            binding.ivCheckLembarPengesahan.isVisible = false
            binding.deleteButton10.isVisible = false
            binding.ivCloseLembarPengesahan.isVisible = true
        }
        // Trigger upload immediately
        observeUploadLembarPengesahan()
    }
    private val viewModel: BiodataPendaftaranViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataPendaftaranBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutps.setOnClickListener {
            clearSession()
        }
        binding.menups.setOnClickListener {
            findNavController().navigate(R.id.action_biodataPendaftaranFragment_to_menuDashboardFragment)
        }
        viewModel.getListLecturer()
        viewModel.getListSemester()
        viewModel.getListAcademicYear()
        setupUI()
        pickFile()
        observeSaveBiodata()
        observeFullName()
        observeNpm()
        observeEmail()
        observeVerif()
        binding.inputNIK.addTextChangedListener(nikTextWatcher)
        binding.inputtempatlahir.addTextChangedListener(tempatTextWatcher)
        binding.inputTanggallahir.addTextChangedListener(tanggalLahirTextWathcer)
        binding.inputAlamat.addTextChangedListener(alamatTextWatcher)
        binding.inputEmail.addTextChangedListener(emailTextWatcher)
        binding.inputhandphone.addTextChangedListener(inputHandphoneTextWatcher)
        binding.inputtelpon.addTextChangedListener(telponTextWatcher)
        binding.inputtotalsks.addTextChangedListener(sksTextWatcher)
        binding.inputipk.addTextChangedListener(ipkTextWatcher)
        binding.inputjudulskripsi.addTextChangedListener(skripsiTextWatcher)
        binding.inputAcademicYear.addTextChangedListener(academicYearTextWatcher)
        binding.inputSemester.addTextChangedListener(semesterTextWatcher)
        binding.inputpembimbingilmu.addTextChangedListener(pembimbingIlmuTextWatcher)
        binding.inputDosenPenguji.addTextChangedListener(dosenPengujiTextWatcher)
        binding.inputpembimbingagama.addTextChangedListener(pembimbingAgamaTextWatcher)
        binding.inputtgllulus.addTextChangedListener(tglLulusTextWatcher)
        binding.inputtglwisuda.addTextChangedListener(tglWisudaTextWatcher)
    }

    private fun clearSession() {
        viewModel.deleteSession()
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }
    //fungsi untuk memeriksa apakah semua file telah di upload
    private fun checkAllFilesUploaded() {
        val allFilesUploaded = isIjasahUploaded && isAkteUploaded && isKKUploaded &&
                isKTPUploaded && isKTMUploaded && isFotoUploaded &&
                isSKLUploaded && isToeicUploaded && isSkripsiUploaded &&
                isLembarPengesahanUploaded

        binding.btnKirimPendaftaran.isVisible = allFilesUploaded
    }
    //ijasah
    private fun eventPickIjasah() {
        binding.uploadButton1.setOnClickListener {
            requestFileIjasahLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadIjasah(){
        selectedFileIjasahFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "grad_certificate",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadGradCert(body)
        }
        observeUploadIjasahResponse()
    }
    private fun observeUploadIjasahResponse() {
        viewModel.uploadGradCertResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isIjasahUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Ijasah Berhasil", Toast.LENGTH_SHORT).show()
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
    //Akte
    private fun eventPickAkte() {
        binding.uploadButton2.setOnClickListener {
            requestFileAkteLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadAkte(){
        selectedFileAkteFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "birth_certificate",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadBirthCert(body)
        }
        observeUploadAkteResponse()
    }
    private fun observeUploadAkteResponse() {
        viewModel.uploadBirthCertResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isAkteUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Akte Berhasil", Toast.LENGTH_SHORT).show()
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
    //Kartu Keluarga
    private fun eventPickKk() {
        binding.uploadButton3.setOnClickListener {
            requestFileKkLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadKk(){
        selectedFileKkFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "family_card",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadFamilyCard(body)
        }
        observeUploadKkResponse()
    }
    private fun observeUploadKkResponse() {
        viewModel.uploadFamilyCardResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isKKUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Kartu Keluarga Berhasil", Toast.LENGTH_SHORT).show()
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
    //Ktp
    private fun eventPickKtp() {
        binding.uploadButton4.setOnClickListener {
            requestFileKtpLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadKtp(){
        selectedFileKtpFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "idcard",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadIdCard(body)
        }
        observeUploadKtpResponse()
    }
    private fun observeUploadKtpResponse() {
        viewModel.uploadIdCardResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isKTPUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Ktp Berhasil", Toast.LENGTH_SHORT).show()
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
    //Ktm
    private fun eventPickKtm() {
        binding.uploadButton5.setOnClickListener {
            requestFileKtmLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadKtm(){
        selectedFileKtmFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "student_card",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadStudentCard(body)
        }
        observeUploadKtmResponse()
    }
    private fun observeUploadKtmResponse() {
        viewModel.uploadStudentCardResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isKTMUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Ktm Berhasil", Toast.LENGTH_SHORT).show()
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
    //Foto
    private fun eventPickFoto() {
        binding.uploadButton6.setOnClickListener {
            requestFileFotoLauncher.launch("image/*")
        }
    }
    private fun observeUploadPasFoto(){
        selectedFileFotoFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "photo",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadPhoto(body)
        }
        observeUploadFotoResponse()
    }
    private fun observeUploadFotoResponse() {
        viewModel.uploadPhotoResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isFotoUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Pas Foto Berhasil", Toast.LENGTH_SHORT).show()
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
    //Skl
    private fun eventPickSkl() {
        binding.uploadButton7.setOnClickListener {
            requestFileSklLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadSkl(){
        selectedFileSklFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "temp_grad",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadSkl(body)
        }
        observeUploadSklResponse()
    }
    private fun observeUploadSklResponse() {
        viewModel.uploadTempGradResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isSKLUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Skl Berhasil", Toast.LENGTH_SHORT).show()
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
    //Toeic
    private fun eventPickToeic() {
        binding.uploadButton8.setOnClickListener {
            requestFileToeicLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadToeic(){
        selectedFileToeicFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "toeic",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadToeic(body)
        }
        observeUploadToeicResponse()
    }
    private fun observeUploadToeicResponse() {
        viewModel.uploadToeicResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isToeicUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload Toeic Berhasil", Toast.LENGTH_SHORT).show()
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
    //Skripsi
    private fun eventPickSkripsi() {
        binding.uploadButton9.setOnClickListener {
            requestFileSkripsiLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadSkripsi(){
        selectedFileSkripsiFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "thesis",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadThesis(body)
        }
        observeUploadSkripsiResponse()
    }
    private fun observeUploadSkripsiResponse() {
        viewModel.uploadThesisResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Success ->{
                    showLoading(false)
                    val data = resources.data
                    if (data != null){
                        if (data.status == "success"){
                            isSkripsiUploaded = true
                            checkAllFilesUploaded()
                            Toast.makeText(requireContext(),"Upload File Skripsi Berhasil", Toast.LENGTH_SHORT).show()
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
    //Lembar Pengesahan
    private fun eventPickLembarPengesahan() {
        binding.uploadButton10.setOnClickListener {
            requestFileLembarPengesahanLauncher.launch("application/pdf")
        }
    }
    private fun observeUploadLembarPengesahan(){
        selectedFileLembarPengesahanFile?.let { file ->
            val requestFile = RequestBody.create("application/pdf".toMediaTypeOrNull(),file)
            val body = MultipartBody.Part.createFormData(
                name = "validity_sheet",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadValsheet(body)
        }
        observeUploadLembarPengesahanResponse()
    }
    private fun observeUploadLembarPengesahanResponse() {
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
                            isLembarPengesahanUploaded = true
                            checkAllFilesUploaded()
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
    private fun pickFile() {
        eventPickIjasah()
        eventPickAkte()
        eventPickKk()
        eventPickKtp()
        eventPickKtm()
        eventPickFoto()
        eventPickSkl()
        eventPickToeic()
        eventPickSkripsi()
        eventPickLembarPengesahan()
    }
    private val nikTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val tempatTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val tanggalLahirTextWathcer = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val alamatTextWatcher = object : TextWatcher {
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
    private val inputHandphoneTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val telponTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val sksTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val ipkTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val skripsiTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val academicYearTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val semesterTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val pembimbingIlmuTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val dosenPengujiTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val pembimbingAgamaTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val tglLulusTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val tglWisudaTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private fun validateFields() {
        val nikError = if (binding.inputNIK.text.isNullOrBlank()) {
            binding.nik.error = "NIK tidak boleh kosong"
            true
        } else if (!binding.inputNIK.text.toString().all { it.isDigit() }) {
            binding.inputNIK.error = "Nomor NIK harus berupa angka"
            true
        } else if (binding.inputNIK.text?.length ?: 0 < 16 || binding.inputNIK.text?.length ?: 0 > 16) {
            binding.inputNIK.error = "Masukan NIK dengan benar"
            true
        } else {
            binding.nik.isErrorEnabled = false
            false
        }
        val tempatLahir = if (binding.inputtempatlahir.text.isNullOrBlank()) {
            binding.tempatlahir.error = "Tempat Lahir tidak boleh kosong"
            true
        } else {
            binding.tempatlahir.isErrorEnabled = false
            false
        }
        val tanggalLahir = if (binding.inputTanggallahir.text.isNullOrBlank()) {
            binding.tanggallahir.error = "Tanggal Lahir tidak boleh kosong"
            true
        } else {
            binding.tanggallahir.isErrorEnabled = false
            false
        }
        val alamat = if (binding.inputAlamat.text.isNullOrBlank()) {
            binding.alamat.error = "Alamat tidak boleh kosong"
            true
        } else {
            binding.alamat.isErrorEnabled = false
            false
        }
        val email = if (binding.inputEmail.text.isNullOrBlank()) {
            binding.email.error = "Email tidak boleh kosong"
            true
        } else {
            binding.email.isErrorEnabled = false
            false
        }
        val handphone = if (binding.inputhandphone.text.isNullOrBlank()) {
            binding.handphone.error = "No Handphone tidak boleh kosong"
            true
        } else if (!binding.inputhandphone.text.toString().all { it.isDigit() }) {
            binding.inputhandphone.error = "Nomor Telepon harus berupa angka"
            true
        } else if (binding.inputhandphone.text?.length ?: 0 < 10 || binding.inputhandphone.text?.length ?: 0 > 13) {
            binding.inputhandphone.error = "Masukan No Telepon dengan benar"
            true
        } else {
            binding.handphone.isErrorEnabled = false
            false
        }
        val telpon = if (binding.inputtelpon.text.isNullOrBlank()) {
            binding.Telpon.error = "No Telpon tidak boleh kosong"
            true
        } else if (!binding.inputtelpon.text.toString().all { it.isDigit() }) {
            binding.Telpon.error = "Nomor Telepon harus berupa angka"
            true
        } else if (binding.inputtelpon.text?.length ?: 0 < 10 || binding.inputtelpon.text?.length ?: 0 > 13) {
            binding.Telpon.error = "Masukan No Telepon dengan benar"
            true
        } else {
            binding.Telpon.isErrorEnabled = false
            false
        }
        val sks = if (binding.inputtotalsks.text.isNullOrBlank()) {
            binding.totalsks.error = "SKS tidak boleh kosong"
            true
        } else if (!binding.inputtotalsks.text.toString().all { it.isDigit() }) {
            binding.totalsks.error = "Total SKS harus berupa angka"
            true
        } else {
            val totalSks = binding.inputtotalsks.text.toString().toIntOrNull()
            if (totalSks == null || totalSks < 144 || totalSks > 170) {
                binding.totalsks.error = "Masukan total SKS dengan benar"
                true
            } else {
                binding.totalsks.isErrorEnabled = false
                false
            }
        }
        val ipk = if (binding.inputipk.text.isNullOrBlank()) {
            binding.ipk.error = "IPK tidak boleh kosong"
            true
        } else if (binding.inputipk.text.toString().toDoubleOrNull()?.let { it < 2.0 } ?: false) {
            binding.ipk.error = "IPK tidak boleh kurang dari 2.0"
            true
        } else if (binding.inputipk.text.toString().toDoubleOrNull()?.let { it > 4.0 } ?: false) {
            binding.ipk.error = "IPK tidak boleh lebih dari 4.0"
            true
        } else {
            binding.ipk.isErrorEnabled = false
            false
        }
        val skripsi = if (binding.inputjudulskripsi.text.isNullOrBlank()) {
            binding.judulskripsi.error = "Skripsi tidak boleh kosong"
            true
        } else {
            binding.judulskripsi.isErrorEnabled = false
            false
        }
        val tahunAjaran = if (binding.inputAcademicYear.text.isNullOrBlank()) {
            binding.academicYear.error = "Tahun Ajaran tidak boleh kosong"
            true
        } else {
            binding.academicYear.isErrorEnabled = false
            false
        }
        val semester = if (binding.inputSemester.text.isNullOrBlank()) {
            binding.semester.error = "Semester tidak boleh kosong"
            true
        } else {
            binding.semester.isErrorEnabled = false
            false
        }
        val pembimbingIlmu = if (binding.inputpembimbingilmu.text.isNullOrBlank()) {
            binding.pembimbingilmu.error = "Pembimbing Ilmu tidak boleh kosong"
            true
        } else {
            binding.pembimbingilmu.isErrorEnabled = false
            false
        }
        val dosenPenguji = if (binding.inputDosenPenguji.text.isNullOrBlank()) {
            binding.dosenPenguji.error = "Dosen Penguji tidak boleh kosong"
            true
        } else {
            binding.dosenPenguji.isErrorEnabled = false
            false
        }
        val pembimbingAgama = if (binding.inputpembimbingagama.text.isNullOrBlank()) {
            binding.pembimbingagama.error = "Pembimbing Agama tidak boleh kosong"
            true
        } else {
            binding.pembimbingagama.isErrorEnabled = false
            false
        }
        val tglLulus = if (binding.inputtgllulus.text.isNullOrBlank()) {
            binding.tgllulus.error = "Tanggal Lulus tidak boleh kosong"
            true
        } else {
            binding.tgllulus.isErrorEnabled = false
            false
        }
        val tglWisuda = if (binding.inputtglwisuda.text.isNullOrBlank()) {
            binding.tglwisuda.error = "Tanggal Wisuda tidak boleh kosong"
            true
        } else {
            binding.tglwisuda.isErrorEnabled = false
            false
        }
        binding.btnsimpan.isEnabled = !nikError && !tempatLahir &&!tanggalLahir && !alamat && !email && !handphone && !telpon && !sks && !ipk && !skripsi && !tahunAjaran
                && !semester && !pembimbingIlmu && !dosenPenguji && !pembimbingAgama && !tglLulus && !tglWisuda

    }
    private fun observeVerif() {
        binding.btnKirimPendaftaran.setOnClickListener { btn ->
            viewModel.patchVerif(verifiedRequestBody = VerifiedRequestBody(verification = "NOT_VERIFIED"))
            viewModel.patchVerifResponse.observe(viewLifecycleOwner){ resources ->
                when(resources){
                    is Resource.Success ->{
                        showLoading(false)
                        Toast.makeText(requireContext(),"Pendafataran Berhasil dikirim",Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_biodataPendaftaranFragment_to_berhasilSimpanPendaftaranFragment)
                    }
                    is Resource.Loading ->{
                        showLoading(true)
                    }
                    is Resource.Error ->{
                        showLoading(false)
                        Toast.makeText(requireContext(),"Pendaftaran Gagal",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }






    private fun observeEmail(){
        viewModel.email.observe(viewLifecycleOwner){email ->
            val email =email
            val emailEditTable = email?.let { Editable.Factory.getInstance().newEditable(it) }
            binding.inputEmail.text = emailEditTable
        }
    }
    private fun observeFullName() {
        viewModel.fullName.observe(viewLifecycleOwner){ fullName ->
            val fullName = fullName
            val fullEditTable = fullName?.let { Editable.Factory.getInstance().newEditable(it) }
            binding.inputNama.text = fullEditTable
        }
    }
    private fun observeNpm(){
        viewModel.npm.observe(viewLifecycleOwner){ npm ->
            val npm = npm
            val npmEditable = npm?.let { Editable.Factory.getInstance().newEditable(it)}
            binding.inputNPM.text = npmEditable
            val getNpm = binding.inputNPM.text.toString()
            Log.d("getNpm",getNpm)
        }
    }


    private fun observeSaveBiodata() {
       binding.btnsimpan.setOnClickListener { btn ->
           val studentId = binding.inputNPM.text.toString().trim()
           val nik = binding.inputNIK.text.toString().trim()
           val tempatLahir = binding.inputtempatlahir.text.toString().trim()
           val alamat = binding.inputAlamat.text.toString().trim()
           val handphone = binding.inputhandphone.text.toString().trim()
           val noTelp = binding.inputtelpon.text.toString().trim()
           val sks = binding.inputtotalsks.text.toString().trim()
           val ipk = binding.inputipk.text.toString().trim()
           val judulSkripsi = binding.inputjudulskripsi.text.toString().trim()
           viewModel.saveBiodataPendaftaran(BiodataPendaftaranRequestBody(
               studentId,selectedProdi,nik,alamat,tanggalLahir,nidnPembimbingIlmu,nidnDosenPenguji,
               nidnPembimbingAgama,tempatLahir,handphone,noTelp,sks.toInt(),ipk.toFloat(),selectedAcademicYear,
               selectedSemester,selectedDosenPenguji,selectedGender,judulSkripsi,selectedPembimbingIlmu,
               selectedPembimbingAgama,tanggalWisuda,tanggalLulus
           ))
           viewModel.biodataPendaftaranResponse.observe(viewLifecycleOwner){
               if (it!=null){
                   when(it){
                       is Resource.Loading ->{
                           showLoading(true)
                       }
                       is Resource.Success ->{
                           showLoading(false)
                           val data = it.data
                           if (data != null){
                               if (data.status == "success"){
                                   Toast.makeText(requireContext(),"Berhasil Simpan Biodata Pendaftaran",Toast.LENGTH_SHORT).show()
                               }
                           }
                       }
                       is Resource.Error ->{
                           showLoading(false)
                           Toast.makeText(context, it.message,Toast.LENGTH_LONG).show()
                           Log.e("Erro Bosqu",it?.message!!)
                       }
                   }
               }
           }
       }
    }



    private fun setupUI() {
        toMenu()
        pickTanggalLahir()
        pickTanggalLulus()
        pickTanggalWisuda()
        pickJenisKelamin()
        pickJurusan()
        pickIlmu()
        pickSemester()
        pickAcademicYear()
        pickReligi()
        pickDosenPenguji()
    }

    private fun toMenu() {
        binding.menups.setOnClickListener {
            findNavController().navigate(R.id.action_biodataPendaftaranFragment_to_menuDashboardFragment)
        }
    }


    private fun pickDosenPenguji() {
        binding.inputDosenPenguji.setOnClickListener {
            pickPenguji()
        }
    }

    private fun pickReligi() {
        binding.inputpembimbingagama.setOnClickListener {
            pickPembimbingAgama()
        }
    }
    private fun pickSemester(){
        binding.inputSemester.setOnClickListener{
            pilihSemester()
        }
    }
    private fun pickAcademicYear(){
        binding.inputAcademicYear.setOnClickListener {
            pilihAcademicYear()
        }
    }
    private fun pickIlmu() {
        binding.inputpembimbingilmu.setOnClickListener {
            pickPembimbingIlmu()
        }
    }

    private fun pickJurusan() {
        binding.inputprodi.setOnClickListener {
            pickProdi()
        }
    }

    private fun pickJenisKelamin() {
        binding.autocJenisKelamin.setOnClickListener {
            pickGender()
        }
    }

    private fun pickTanggalWisuda() {
        binding.inputtglwisuda.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDateWisuda = Calendar.getInstance()
                    selectedDateWisuda.set(year, monthOfYear,dayOfMonth)

                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id","ID"))

                    tanggalWisuda = dateFormat.format(selectedDateWisuda.time)
                    binding.inputtglwisuda.setText(tanggalWisuda)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()

        }
    }



    private fun pickTanggalLulus() {
        binding.inputtgllulus.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDateLulus = Calendar.getInstance()
                    selectedDateLulus.set(year, monthOfYear,dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id","ID"))
                    tanggalLulus = dateFormat.format(selectedDateLulus.time)
                    binding.inputtgllulus.setText(tanggalLulus)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()

        }
    }

    private fun pickTanggalLahir() {
        binding.inputTanggallahir.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDateLahir = Calendar.getInstance()
                    selectedDateLahir.set(year, monthOfYear,dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id","ID"))
                    tanggalLahir = dateFormat.format(selectedDateLahir.time)
                    binding.inputTanggallahir.setText(tanggalLahir)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
    private fun pilihAcademicYear(){
        academicAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line)
        binding.inputAcademicYear.setAdapter(academicAdapter)
        binding.inputAcademicYear.setOnItemClickListener{parent, _, position, _ ->
            val selectedAcademicYearList = parent.getItemAtPosition(position) as String
            selectedAcademicYear = viewModel.listAcademicYearResponse.value?.payload?.data?.get(position)?.academicYear.toString()
            binding.inputAcademicYear.setText(selectedAcademicYearList,false)
            Log.d("academicYear",selectedAcademicYear)
        }
        binding.inputSemester.setOnClickListener {
            binding.inputSemester.showDropDown()
        }
        viewModel.academicYear.observe(viewLifecycleOwner, Observer { academicYear ->
            val academicYear = academicYear.map { it.academicYear }
            academicAdapter.clear()
            academicAdapter.addAll(academicYear)
            academicAdapter.notifyDataSetChanged()
        })
    }
    private fun pilihSemester(){
        semesterAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line)
        binding.inputSemester.setAdapter(semesterAdapter)
        binding.inputSemester.setOnItemClickListener{parent, _, position, _ ->
            val selectedSemesterList = parent.getItemAtPosition(position) as String
            selectedSemester = viewModel.listSemesterResponse.value?.payload?.data?.get(position)?.semester.toString()
            binding.inputSemester.setText(selectedSemesterList,false)
            Log.d("semester",selectedSemester)
        }
        binding.inputSemester.setOnClickListener{
            binding.inputSemester.showDropDown()
        }
        viewModel.semester.observe(viewLifecycleOwner, Observer { semester ->
            val semester = semester.map { it.semester }
            semesterAdapter.clear()
            semesterAdapter.addAll(semester)
            semesterAdapter.notifyDataSetChanged()
        })
    }
    private fun pickPenguji() {
        lecturerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line)
        binding.inputDosenPenguji.setAdapter(lecturerAdapter)
        binding.inputDosenPenguji.setOnItemClickListener{parent, _, position, _ ->
            val selectedLecturer = parent.getItemAtPosition(position) as String
            selectedDosenPenguji = viewModel.listLecturerResponse.value?.payload?.data?.get(position)?.lecturerName.toString()
            nidnDosenPenguji = viewModel.listLecturerResponse.value?.payload?.data?.get(position)?.nidn.orEmpty()
            Log.d("dosen penguji",selectedDosenPenguji)
            Log.d("Nidn dosen penguji", nidnDosenPenguji)
            binding.inputDosenPenguji.setText(selectedLecturer,false)
        }
        binding.inputDosenPenguji.setOnClickListener{
            binding.inputDosenPenguji.showDropDown()
        }
        viewModel.lecturers.observe(viewLifecycleOwner, Observer { lecturers ->
            val lecturerNames = lecturers.map { it.lecturerName }
            lecturerAdapter.clear()
            lecturerAdapter.addAll(lecturerNames)
            lecturerAdapter.notifyDataSetChanged()
        })
    }

    private fun pickPembimbingIlmu(){
        lecturerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line)
        binding.inputpembimbingilmu.setAdapter(lecturerAdapter)
        binding.inputpembimbingilmu.setOnItemClickListener{parent, _, position, _ ->
            val selectedLecturer = parent.getItemAtPosition(position) as String
            selectedPembimbingIlmu = viewModel.listLecturerResponse.value?.payload?.data?.get(position)?.lecturerName.toString()
            nidnPembimbingIlmu = viewModel.listLecturerResponse.value?.payload?.data?.get(position)?.nidn.orEmpty()
            Log.d("pembimbing ilmu",selectedPembimbingIlmu)
            Log.d("Nidn pembimbing ilmu", nidnPembimbingIlmu)
            binding.inputpembimbingilmu.setText(selectedLecturer,false)
        }
        binding.inputpembimbingilmu.setOnClickListener{
            binding.inputpembimbingilmu.showDropDown()
        }
        viewModel.lecturers.observe(viewLifecycleOwner, Observer { lecturers ->
            val lecturerNames = lecturers.map { it.lecturerName }
            lecturerAdapter.clear()
            lecturerAdapter.addAll(lecturerNames)
            lecturerAdapter.notifyDataSetChanged()
        })
    }


    private fun pickPembimbingAgama(){ // Start suggesting after 1 character
        lecturerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line)
        binding.inputpembimbingagama.setAdapter(lecturerAdapter)
        binding.inputpembimbingagama.setOnItemClickListener{parent, _, position, _ ->
            val selectedLecturer = parent.getItemAtPosition(position) as String
            selectedPembimbingAgama = viewModel.listLecturerResponse.value?.payload?.data?.get(position)?.lecturerName.toString()
            nidnPembimbingAgama = viewModel.listLecturerResponse.value?.payload?.data?.get(position)?.nidn.orEmpty()
            Log.d("pembimbing agama",selectedPembimbingAgama)
            Log.d("Nidn pembimbing agama", nidnPembimbingAgama)
            binding.inputpembimbingagama.setText(selectedLecturer,false)
        }
        binding.inputpembimbingagama.setOnClickListener{
            binding.inputpembimbingagama.showDropDown()
        }
        viewModel.lecturers.observe(viewLifecycleOwner, Observer { lecturers ->
            val lecturerNames = lecturers.map { it.lecturerName }
            lecturerAdapter.clear()
            lecturerAdapter.addAll(lecturerNames)
            lecturerAdapter.notifyDataSetChanged()
        })
    }

    private fun pickProdi() {
        val items = arrayOf("Teknik Informatika", "Perpustakaan dan Sains Informasi (PdSI)")
        val autoComplete = binding.inputprodi
        val adapter = ArrayAdapter(requireActivity(), R.layout.list_drop_down,items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            selectedProdi = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(requireActivity(),"Pilih Prodi: $selectedProdi",Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickGender() {
        val items = arrayOf("Laki - Laki", "Perempuan")
        val autoComplete = binding.autocJenisKelamin
        val adapter = ArrayAdapter(requireActivity(), R.layout.list_drop_down,items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            selectedGender = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(requireActivity(),"Jenis Kelamin: $selectedGender",Toast.LENGTH_SHORT).show()
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