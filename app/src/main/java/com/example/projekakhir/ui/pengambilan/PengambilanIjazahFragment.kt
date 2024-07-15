package com.example.projekakhir.ui.pengambilan

import android.app.DatePickerDialog
import android.content.Intent
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.MainActivity
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentPengambilanIjazahBinding
import com.example.projekakhir.network.model.response.graduateform.GraduateFormRequestBody
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.Resource
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class PengambilanIjazahFragment : Fragment() {

    lateinit var binding: FragmentPengambilanIjazahBinding
    private lateinit var tanggalLahir :String
    private lateinit var tanggalLulus :String
    private lateinit var selectedGender :String
    private lateinit var selectedProdi :String
    private val viewModel: PengambilanIjazahViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPengambilanIjazahBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeSaveGraduateForm()
        observeNpm()
        observeName()
        viewModel.getStudent()
        observeStudent()

        //handler ijasah
        binding.inputAgama.addTextChangedListener(agamaTextWatcher)
        binding.inputjenjang.addTextChangedListener(jenjangTextWatcher)
        binding.inputnamaayah.addTextChangedListener(namaAyahTextWathcer)
        binding.inputnamaibu.addTextChangedListener(namaIbuTextWatcher)
        binding.inputteleponortu.addTextChangedListener(teleponOrtuTextWatcher)
        binding.inputalamatortu.addTextChangedListener(alamatOrtuTextWatcher)
    }

    private val agamaTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val jenjangTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val namaAyahTextWathcer = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val namaIbuTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val teleponOrtuTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }
    private val alamatOrtuTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateFields()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun validateFields(){
        val agamaError = if (binding.inputAgama.text.isNullOrBlank()) {
            binding.agama.error = "Agama tidak boleh kosong"
            true
        } else {
            binding.agama.isErrorEnabled = false
            false
        }
        val jenjangError = if (binding.inputjenjang.text.isNullOrBlank()) {
            binding.jenjang.error = "Jenjang tidak boleh kosong"
            true
        } else {
            binding.jenjang.isErrorEnabled = false
            false
        }
        val namaAyahError = if (binding.inputnamaayah.text.isNullOrBlank()) {
            binding.namaayah.error = "Nama Ayah tidak boleh kosong"
            true
        } else {
            binding.namaayah.isErrorEnabled = false
            false
        }
        val namaIbuErro = if (binding.inputnamaibu.text.isNullOrBlank()) {
            binding.namaibu.error = "Nama Ibu tidak boleh kosong"
            true
        } else {
            binding.namaibu.isErrorEnabled = false
            false
        }
        val teleponOrtuError = if (binding.inputteleponortu.text.isNullOrBlank()) {
            binding.teleponortu.error = "Telepon Orang Tua tidak boleh kosong"
            true
        } else if (!binding.inputteleponortu.text.toString().all { it.isDigit() }) {
            binding.teleponortu.error = "Nomor Telepon harus berupa angka"
            true
        } else if (binding.inputteleponortu.text?.length ?: 0 < 10 || binding.inputteleponortu.text?.length ?: 0 > 13) {
            binding.teleponortu.error = "Masukan No Telepon dengan benar"
            true
        } else {
            binding.teleponortu.isErrorEnabled = false
            false
        }
        val alamatOrtuError = if (binding.inputalamatortu.text.isNullOrBlank()) {
            binding.alamatortu.error = "Alamat Orang Tua tidak boleh kosong"
            true
        } else {
            binding.alamatortu.isErrorEnabled = false
            false
        }
        binding.btnsimpan.isEnabled = !agamaError && !jenjangError &&!namaAyahError && !namaIbuErro && !teleponOrtuError && !alamatOrtuError
    }

    private fun observeName() {
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
        }
    }

    private fun observeSaveGraduateForm() {
        binding.btnsimpan.setOnClickListener { btn ->
            val studentId = binding.inputNPM.text.toString().trim()
            val namaLengkap = binding.inputNama.text.toString().trim()
            val agama = binding.inputAgama.text.toString().trim()
            val tempatLahir = binding.inputtempatlahir.text.toString().trim()
            //tanggal lahir
            val tanggalLahir = binding.inputTanggallahir.text.toString().trim()
            //jenis kelamin
            val jenisKelamin = binding.inputjeniskelamin.text.toString().trim()
            val alamat = binding.inputAlamat.text.toString().trim()
            val nomorHp = binding.inputnohp.text.toString().trim()
            //prodi
            val prodi = binding.inputprodi.text.toString().trim()
            val jenjang = binding.inputjenjang.text.toString().trim()
            val ipk = binding.inputipk.text.toString().trim()
            //tanggal lulus
            val tanggalLulus = binding.inputtanggallulus.text.toString().trim()
            val namaAyah = binding.inputnamaayah.text.toString().trim()
            val namaIbu = binding.inputnamaibu.text.toString().trim()
            val telpOrangTua = binding.inputteleponortu.text.toString().trim()
            val alamatOrangTua = binding.inputalamatortu.text.toString().trim()
            viewModel.saveGraduateForm(GraduateFormRequestBody(
                studentId,namaLengkap,tanggalLahir,tempatLahir,jenisKelamin,alamat,nomorHp,prodi,
                ipk.toFloat(),agama,jenjang,namaAyah,namaIbu,telpOrangTua,alamatOrangTua,tanggalLulus
            ))
            viewModel.graduateForm.observe(viewLifecycleOwner){
                if (it!=null){
                    when(it){
                        is Resource.Loading ->{
                            showLoading(true)
                        }
                        is Resource.Success ->{
                            showLoading(false)
                            val data = it.data
                            if (data != null){
                                if (data.status == "success save form graduate"){
                                    Toast.makeText(requireContext(),"Berhasil Simpan Pendaftaran pengambilan ijasah",Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_pengambilanIjazahFragment_to_berhasilSimpanIjazahFragment)
                                }
                            }
                        }
                        is Resource.Error ->{
                            showLoading(false)
                            Toast.makeText(context, it.message,Toast.LENGTH_LONG).show()
                            Log.e("Erro ",it?.message!!)
                        }
                    }
                }
            }
        }
    }

    private fun observeStudent() = with(binding){
        viewModel.getStudentByStudentIdResponse.observe(viewLifecycleOwner){ resources ->
            when(resources){
                is Resource.Success ->{
                    showLoading(false)
                    val npm = resources.data?.data?.studentId
                    val firstName = resources.data?.data?.firstName
                    val lastName = resources.data?.data?.lastName
                    val tempatLahir = resources.data?.data?.birthPlace
                    tanggalLahir = resources.data?.data?.birthDate.toString()
                    val jenisKelamin = resources.data?.data?.gender
                    val alamat = resources.data?.data?.address
                    val noHp = resources.data?.data?.phoneNumber
                    val prodi = resources.data?.data?.major
                    val ipk = resources.data?.data?.gpa
                    tanggalLulus = resources.data?.data?.commencementDate.toString()
                    val npmEditTable = npm?.let { Editable.Factory.getInstance().newEditable(it) }
                    val firstNameEditTable = firstName?.let { Editable.Factory.getInstance().newEditable(it) }
                    val lastNameEditTable = lastName?.let { Editable.Factory.getInstance().newEditable(it) }
                    val fullNameEditTable = Editable.Factory.getInstance().newEditable(
                        "${firstNameEditTable?.toString() ?:""} ${lastNameEditTable?.toString() ?:""}"
                    )
                    val tempatLahirEditTable = tempatLahir?.let { Editable.Factory.getInstance().newEditable(it) }
                    val tanggalLahirEditTable = tanggalLahir?.let { Editable.Factory.getInstance().newEditable(it) }
                    val jenisKelaminEditTable = jenisKelamin?.let { Editable.Factory.getInstance().newEditable(it) }
                    val alamatEditTable = alamat?.let { Editable.Factory.getInstance().newEditable(it) }
                    val noHpEditTable = noHp?.let { Editable.Factory.getInstance().newEditable(it) }
                    val prodiEditTable = prodi?.let { Editable.Factory.getInstance().newEditable(it) }
                    val ipkEditTable = ipk?.let { Editable.Factory.getInstance().newEditable(it.toString())}
                    val tglLulusEditTable = tanggalLulus?.let { Editable.Factory.getInstance().newEditable(it)}
                    binding.inputNPM.text = npmEditTable
                    binding.inputNama.text = fullNameEditTable
                    binding.inputtempatlahir.text = tempatLahirEditTable
                    binding.inputTanggallahir.text = tanggalLahirEditTable
                    binding.inputjeniskelamin.text = jenisKelaminEditTable
                    binding.inputAlamat.text = alamatEditTable
                    binding.inputnohp.text = noHpEditTable
                    binding.inputprodi.text = prodiEditTable
                    binding.inputipk.text = ipkEditTable
                    binding.inputtanggallulus.text = tglLulusEditTable
                }
                is Resource.Error ->{
                    showLoading(false)
                    Toast.makeText(requireContext(),"Error: ${resources.message}",Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading ->{ showLoading(true)}
            }
        }
    }

    private fun setupUI() {
        pickTanggalLahir()
        pickTanggalLulus()
        pickGender()
        pickProdi()
        binding.logoutpi.setOnClickListener {
            viewModel.deleteSession()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
        binding.menupi.setOnClickListener {
            findNavController().navigate(R.id.action_pengambilanIjazahFragment_to_menuDashboardFragment)
        }
        binding.btnClose.setOnClickListener{
            binding.consLay.visibility = View.GONE
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
        binding.inputtanggallulus.setOnClickListener {

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
                    binding.inputtanggallulus.setText(tanggalLulus)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()

        }
    }
    private fun pickGender() {
        val items = arrayOf("Laki - Laki", "Perempuan")
        val autoComplete = binding.inputjeniskelamin
        val adapter = ArrayAdapter(requireActivity(), R.layout.list_drop_down,items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            selectedGender = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(requireActivity(),"Jenis Kelamin: $selectedGender",Toast.LENGTH_SHORT).show()
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

    private fun showLoading(status:Boolean){
        if (status){
            binding.pbRegister.visibility = View.VISIBLE
        }else{
            binding.pbRegister.visibility = View.INVISIBLE
        }
    }
}