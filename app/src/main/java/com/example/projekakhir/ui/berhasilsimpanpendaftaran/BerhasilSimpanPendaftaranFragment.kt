package com.example.projekakhir.ui.berhasilsimpanpendaftaran

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.projekakhir.MainActivity
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentBerhasilSimpanPendaftaranBinding
import com.example.projekakhir.network.model.response.biodatapendaftaran.update.UpdateBiodataPendaftaranRequestBody
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.Resource
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

class BerhasilSimpanPendaftaranFragment : Fragment() {

    private lateinit var binding: FragmentBerhasilSimpanPendaftaranBinding

    private val viewModel: BerhasilSimpanPendaftaranViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    private lateinit var tanggalLahir : String
    private lateinit var tanggalLulus : String
    private lateinit var tanggalWisuda : String
    private lateinit var selectedGender : String
    private lateinit var selectedProdi : String
    private lateinit var selectedPembimbingIlmu : String
    private lateinit var selectedPembimbingAgama : String
    private lateinit var selectedDosenPenguji : String
    private lateinit var nidnPembimbingIlmu : String
    private lateinit var nidnDosenPenguji : String
    private lateinit var nidnPembimbingAgama : String
    private lateinit var selectedSemester : String
    private lateinit var selectedAcademicYear : String
    private lateinit var lecturerAdapter: ArrayAdapter<String>
    private lateinit var semesterAdapter: ArrayAdapter<String>
    private lateinit var academicAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBerhasilSimpanPendaftaranBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStudent()
        viewModel.getDoc()
        viewModel.getListLecturer()
        viewModel.getListSemester()
        viewModel.getListAcademicYear()
        observeStudent()
        observeDoc()
        observeUpdateStudent()
        toMenu()
        setupUi()
    }

    private fun observeUpdateStudent() {
        binding.btnsimpan.setOnClickListener { btn ->
            val nik = binding.inputNIK.text.toString().trim()
            val tempatLahir = binding.inputtempatlahir.text.toString().trim()
            val alamat = binding.inputAlamat.text.toString().trim()
            val handphone = binding.inputhandphone.text.toString().trim()
            val noTelp = binding.inputtelpon.text.toString().trim()
            val sks = binding.inputtotalsks.text.toString().trim()
            val ipk = binding.inputipk.text.toString().trim()
            val judulSkripsi = binding.inputjudulskripsi.text.toString().trim()
            Log.d("observeUpdateStudent", "prodi = $selectedProdi")
            viewModel.updateBiodataPendaftaran(UpdateBiodataPendaftaranRequestBody(
                selectedProdi,nik,alamat,tanggalLahir,nidnPembimbingIlmu,nidnDosenPenguji,
                nidnPembimbingAgama,tempatLahir,handphone,noTelp,sks.toInt(),ipk.toFloat(),
                selectedAcademicYear,selectedSemester,selectedDosenPenguji,selectedGender,
                judulSkripsi,selectedPembimbingIlmu,selectedPembimbingAgama,tanggalWisuda,tanggalLulus
            ))
            viewModel.updateBiodataPendaftaranResponse.observe(viewLifecycleOwner){resource ->
                when(resource){
                    is Resource.Success ->{
                        showLoading(false)
                        Toast.makeText(requireContext(),"Update Biodata Berhasil",Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading ->{
                        showLoading(true)
                    }
                    is Resource.Error ->{
                        showLoading(false)
                        Toast.makeText(requireContext(),"Update Biodata Gagal",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupUi() {
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
        logout()
    }

    private fun logout() {
        binding.logoutps.setOnClickListener{
            clearSession()
        }
    }
    private fun clearSession() {
        viewModel.deleteSession()
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }
    private fun pickDosenPenguji() {
        binding.inputDosenPenguji.setOnClickListener {
            pickPenguji()
        }
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
    private fun pickReligi() {
        binding.inputpembimbingagama.setOnClickListener {
            pickPembimbingAgama()
        }
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
    private fun pickAcademicYear(){
        binding.inputAcademicYear.setOnClickListener {
            pilihAcademicYear()
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
    private fun pickSemester(){
        binding.inputSemester.setOnClickListener{
            pilihSemester()
        }
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
    private fun pickIlmu() {
        binding.inputpembimbingilmu.setOnClickListener {
            pickPembimbingIlmu()
        }
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
    private fun pickJurusan() {
        binding.inputprodi.setOnClickListener {
            pickProdi()
        }
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
    private fun pickJenisKelamin() {
        binding.autocJenisKelamin.setOnClickListener {
            pickGender()
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
    private fun observeDoc() = with(binding){
        var documentUrlIjasah: String? = null
        var documentUrlAkteKelahiran: String? = null
        var documentUrlKartuKeluarga: String?=null
        var documentUrlKtp: String? = null
        var documentUrlKtm: String? = null
        var documentUrlPasFoto: String? = null
        var documentUrlSkl: String? = null
        var documentUrlToeic: String? = null
        var documentUrlSkripsi: String? = null
        var documentUrlLembarPengesahan: String? = null
        viewModel.getDocResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Success -> {
                    showLoading(false)
                    Glide.with(requireContext()).load(resources.data?.data?.photo)
                        .into(binding.shfotoprofil)
                    resources.data?.data?.let { data ->
                        documentUrlIjasah = data.graduationCertificate
                        documentUrlAkteKelahiran = data.birthCertificate
                        documentUrlKartuKeluarga = data.familyCard
                        documentUrlKtp = data.idCard
                        documentUrlKtm = data.studentCard
                        documentUrlPasFoto = data.photo
                        documentUrlSkl = data.tempGraduationCertificate
                        documentUrlToeic = data.toeicCertificate
                        documentUrlSkripsi = data.thesisFile
                        documentUrlLembarPengesahan = data.validitySheet

                        //ijasah
                        binding.ivCheckFcIjazah.isVisible =
                            !data.graduationCertificate.isNullOrEmpty()
                        binding.ivCloseFcIjazah.isVisible =
                            data.graduationCertificate.isNullOrEmpty()
                        //akte kelahiran
                        binding.ivCheckFcAkte.isVisible = !data.birthCertificate.isNullOrEmpty()
                        binding.ivCloseFcAkte.isVisible = data.birthCertificate.isNullOrEmpty()
                        //kartu keluarga
                        binding.ivCheckFcKK.isVisible = !data.familyCard.isNullOrEmpty()
                        binding.ivCloseFcKK.isVisible = data.familyCard.isNullOrEmpty()
                        //ktp
                        binding.ivCheckFcKTP.isVisible = !data.idCard.isNullOrEmpty()
                        binding.ivCloseFcKTP.isVisible = data.idCard.isNullOrEmpty()
                        //ktm
                        binding.ivCheckFcKTM.isVisible = !data.studentCard.isNullOrEmpty()
                        binding.ivCloseFcKTM.isVisible = data.studentCard.isNullOrEmpty()
                        //foto
                        binding.ivCheckFcPasFoto.isVisible = !data.photo.isNullOrEmpty()
                        binding.ivCloseFcPasFoto.isVisible = data.photo.isNullOrEmpty()
                        //skl
                        binding.ivCheckFcSKL.isVisible =
                            !data.tempGraduationCertificate.isNullOrEmpty()
                        binding.ivCloseFcSKL.isVisible =
                            data.tempGraduationCertificate.isNullOrEmpty()
                        //toeic
                        binding.ivCheckFcTOEIC.isVisible = !data.toeicCertificate.isNullOrEmpty()
                        binding.ivCloseFcTOEIC.isVisible = data.toeicCertificate.isNullOrEmpty()
                        //skripsi
                        binding.ivCheckFcSkripsi.isVisible = !data.thesisFile.isNullOrEmpty()
                        binding.ivCloseFcSkripsi.isVisible = data.thesisFile.isNullOrEmpty()
                        //lembar pengesahan
                        binding.ivCheckLembarPengesahan.isVisible =
                            !data.validitySheet.isNullOrEmpty()
                        binding.ivCloseLembarPengesahan.isVisible =
                            data.validitySheet.isNullOrEmpty()
                    }
                }
                is Resource.Loading ->{
                    showLoading(true)
                }
                is Resource.Error ->{
                }
            }
        }
        binding.aksiIjasah.setOnClickListener {
            openDocumentInBrowser(documentUrlIjasah)
        }
        binding.aksiAkte.setOnClickListener {
            openDocumentInBrowser(documentUrlAkteKelahiran)
        }
        binding.aksiKK.setOnClickListener {
            openDocumentInBrowser(documentUrlKartuKeluarga)
        }
        binding.aksiKtp.setOnClickListener {
            openDocumentInBrowser(documentUrlKtp)
        }
        binding.aksiKtm.setOnClickListener {
            openDocumentInBrowser(documentUrlKtm)
        }
        binding.aksiFoto.setOnClickListener {
            openDocumentInBrowser(documentUrlPasFoto)
        }
        binding.aksiSkl.setOnClickListener {
            openDocumentInBrowser(documentUrlSkl)
        }
        binding.aksiToeic.setOnClickListener {
            openDocumentInBrowser(documentUrlToeic)
        }
        binding.aksiSkripsi.setOnClickListener {
            openDocumentInBrowser(documentUrlSkripsi)
        }
        binding.aksiLembarPengesahan.setOnClickListener {
            openDocumentInBrowser(documentUrlLembarPengesahan)
        }
    }
    private fun openDocumentInBrowser(url: String?) {
        url?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
        } ?: run {
            Toast.makeText(context, "Document URL tidak ada", Toast.LENGTH_SHORT).show()
        }
    }
    private fun toMenu() {
        binding.menups.setOnClickListener {
            findNavController().navigate(R.id.action_berhasilSimpanPendaftaranFragment_to_menuDashboardFragment)
        }
    }

    private fun observeStudent() = with(binding){
        viewModel.getStudentByStudentIdResponse.observe(viewLifecycleOwner){ resources ->
            when(resources){
                is Resource.Success ->{
                    showLoading(false)
                    val npm = resources.data?.data?.studentId
                    val nik = resources.data?.data?.nationalId
                    val firstName = resources.data?.data?.firstName
                    val lastName = resources.data?.data?.lastName
                    val tempatLahir = resources.data?.data?.birthPlace
                    tanggalLahir = resources.data?.data?.birthDate.toString()
                    selectedGender = resources.data?.data?.gender.toString()
                    val alamat = resources.data?.data?.address
                    val email = resources.data?.data?.email
                    val noHp = resources.data?.data?.phoneNumber
                    val noTelp = resources.data?.data?.telephoneNumber
                    selectedProdi = resources.data?.data?.major.toString()
                    nidnPembimbingIlmu = resources.data?.data?.nidnAdvisorOne.toString()
                    nidnDosenPenguji = resources.data?.data?.nidnAdvisorTwo.toString()
                    nidnPembimbingAgama = resources.data?.data?.nidnReligion.toString()
                    val sks = resources.data?.data?.creditCourse
                    val ipk = resources.data?.data?.gpa
                    val skripsi = resources.data?.data?.thesisTitle
                    selectedAcademicYear = resources.data?.data?.academicYear.toString()
                    selectedSemester = resources.data?.data?.semester.toString()
                    selectedPembimbingIlmu = resources.data?.data?.advisor.toString()
                    selectedDosenPenguji = resources.data?.data?.examiner.toString()
                    selectedPembimbingAgama = resources.data?.data?.religionAdvisor.toString()
                    tanggalLulus = resources.data?.data?.commencementDate.toString()
                    tanggalWisuda = resources.data?.data?.graduateDate.toString()
                    val npmEditTable = npm?.let { Editable.Factory.getInstance().newEditable(it) }
                    val nikEditTable = nik?.let { Editable.Factory.getInstance().newEditable(it) }
                    val firstNameEditTable = firstName?.let { Editable.Factory.getInstance().newEditable(it) }
                    val lastNameEditTable = lastName?.let { Editable.Factory.getInstance().newEditable(it) }
                    val fullNameEditTable = Editable.Factory.getInstance().newEditable(
                        "${firstNameEditTable?.toString() ?:""} ${lastNameEditTable?.toString() ?:""}"
                    )
                    val tempatLahirEditTable = tempatLahir?.let { Editable.Factory.getInstance().newEditable(it) }
                    val tanggalLahirEditTable = tanggalLahir.let { Editable.Factory.getInstance().newEditable(it) }
                    val jenisKelaminEditTable = selectedGender.let { Editable.Factory.getInstance().newEditable(it) }
                    val alamatEditTable = alamat?.let { Editable.Factory.getInstance().newEditable(it) }
                    val emailEditTable = email?.let { Editable.Factory.getInstance().newEditable(it) }
                    val noHpEditTable = noHp?.let { Editable.Factory.getInstance().newEditable(it) }
                    val noTelpEditTable = noTelp?.let { Editable.Factory.getInstance().newEditable(it) }
                    val prodiEditTable = selectedProdi.let { Editable.Factory.getInstance().newEditable(it) }
                    val sksEditTable = sks?.let { Editable.Factory.getInstance().newEditable(it.toString())}
                    val ipkEditTable = ipk?.let { Editable.Factory.getInstance().newEditable(it.toString())}
                    val skripsiEditTable = skripsi?.let { Editable.Factory.getInstance().newEditable(it)}
                    val tahunAjaranEditTable = selectedAcademicYear.let { Editable.Factory.getInstance().newEditable(it)}
                    val semesterEditTable = selectedSemester.let { Editable.Factory.getInstance().newEditable(it)}
                    val pembimbingIlmuEditTable = selectedPembimbingIlmu.let { Editable.Factory.getInstance().newEditable(it)}
                    val pengujiEditTable = selectedDosenPenguji.let { Editable.Factory.getInstance().newEditable(it)}
                    val pembimbingAgamaEditTable = selectedPembimbingAgama.let { Editable.Factory.getInstance().newEditable(it)}
                    val tglLulusEditTable = tanggalLulus.let { Editable.Factory.getInstance().newEditable(it)}
                    val tglWisudaEditTable = tanggalWisuda.let { Editable.Factory.getInstance().newEditable(it)}
                    binding.inputNPM.text = npmEditTable
                    binding.inputNIK.text = nikEditTable
                    binding.inputNama.text = fullNameEditTable
                    binding.inputtempatlahir.text = tempatLahirEditTable
                    binding.inputTanggallahir.text = tanggalLahirEditTable
                    binding.autocJenisKelamin.text = jenisKelaminEditTable
                    binding.inputAlamat.text = alamatEditTable
                    binding.inputEmail.text = emailEditTable
                    binding.inputhandphone.text = noHpEditTable
                    binding.inputtelpon.text = noTelpEditTable
                    binding.inputprodi.text = prodiEditTable
                    binding.inputtotalsks.text = sksEditTable
                    binding.inputipk.text = ipkEditTable
                    binding.inputjudulskripsi.text = skripsiEditTable
                    binding.inputAcademicYear.text = tahunAjaranEditTable
                    binding.inputSemester.text = semesterEditTable
                    binding.inputpembimbingilmu.text = pembimbingIlmuEditTable
                    binding.inputDosenPenguji.text = pengujiEditTable
                    binding.inputpembimbingagama.text = pembimbingAgamaEditTable
                    binding.inputtgllulus.text = tglLulusEditTable
                    binding.inputtglwisuda.text = tglWisudaEditTable

                    if (resources.data?.data?.verification == "NOT_VERIFIED"){
                        binding.constraintWaiting.visibility = View.VISIBLE
                        binding.ivCloseWaiting.setOnClickListener {
                            binding.constraintReject.visibility = View.GONE
                        }
                    }else if(resources.data?.data?.verification == "VERIFIED"){
                        binding.constraintAccept.visibility = View.VISIBLE
                        binding.ivCloseAcc.setOnClickListener {
                            binding.constraintAccept.visibility = View.GONE
                        }
                    }else if(resources.data?.data?.verification == "REJECTED"){
                        binding.constraintReject.visibility = View.VISIBLE
                        binding.tvMessage.text = resources.data.data.message
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

    private fun showLoading(status:Boolean){
        if (status){
            binding.pbRegister.visibility = View.VISIBLE
        }else{
            binding.pbRegister.visibility = View.INVISIBLE
        }
    }
}