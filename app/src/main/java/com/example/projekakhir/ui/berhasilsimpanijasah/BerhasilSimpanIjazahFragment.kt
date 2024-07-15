package com.example.projekakhir.ui.berhasilsimpanijasah

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.MainActivity
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentBerhasilSimpanIjazahBinding
import com.example.projekakhir.network.model.response.graduateform.upadategraduateform.UpdateGraduateForm
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.Resource
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.property.TextAlignment

class BerhasilSimpanIjazahFragment : Fragment() {

    private lateinit var binding: FragmentBerhasilSimpanIjazahBinding
    private lateinit var tanggalLahir :String
    private lateinit var tanggalLulus :String
    private lateinit var selectedGender :String
    private lateinit var selectedProdi :String
    private val viewModel: BerhasilSimpanIjazahViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private val STORAGE_PERMISSION_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBerhasilSimpanIjazahBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menupi.setOnClickListener {
            findNavController().navigate(R.id.action_berhasilSimpanIjazahFragment_to_menuDashboardFragment)
        }
        setupUI()
        observeSaveGraduateForm()
        observeNpm()
        observeName()
        viewModel.getStudent()
        observeStudent()
        cetakIjasah()
    }
    private fun cetakIjasah() {
        binding.btncetak.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
            } else {
                generateAndOpenPDF()
            }
        }
    }
    private fun generateAndOpenPDF() {
        val nama = binding.inputNama.text.toString()
        val npm = binding.inputNPM.text.toString()
        val agama = binding.inputAgama.text.toString()
        val tempatLahir = binding.inputtempatlahir.text.toString()
        val tanggalLahir = binding.inputTanggallahir.text.toString()
        val jenisKelamin = binding.inputjeniskelamin.text.toString()
        val alamat = binding.inputAlamat.text.toString()
        val handphone = binding.inputnohp.text.toString()
        val programStudi = binding.inputprodi.text.toString()
        val jenjang = binding.inputjenjang.text.toString()
        val tanggalLulus = binding.inputtanggallulus.text.toString()
        val ipk = binding.inputipk.text.toString()
        val namaAyah = binding.inputnamaayah.text.toString()
        val namaIbu = binding.inputnamaibu.text.toString()
        val alamatOrtu = binding.inputalamatortu.text.toString()
        val telponOrtu = binding.inputteleponortu.text.toString()

        try {
            val pdfFile = generatePDF(
                nama,
                npm,
                agama,
                tempatLahir,
                tanggalLahir,
                jenisKelamin,
                alamat,
                handphone,
                programStudi,
                jenjang,
                tanggalLulus,
                ipk,
                namaAyah,
                namaIbu,
                alamatOrtu,
                telponOrtu
            )
            openPDF(pdfFile)
            Toast.makeText(requireContext(), "PDF Generated Successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to Generate PDF", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    private fun generatePDF(
        nama: String,
        npm: String,
        agama: String,
        tempatLahir: String,
        tanggalLahir: String,
        jenisKelamin: String,
        alamat: String,
        handphone: String,
        programStudi: String,
        jenjang: String,
        tanggalLulus: String,
        ipk: String,
        namaAyah: String,
        namaIbu: String,
        alamatOrtu: String,
        telponOrtu: String
    ): File {
        val context = requireContext()
        val fileName = "${context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}/generated.pdf"
        val file = File(fileName)
        val pdfWriter = PdfWriter(FileOutputStream(file))
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        val font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN)
        val blackColor = DeviceRgb(0, 0, 0)

        document.add(
            Paragraph("SURAT PERNYATAAN PENGAMBILAN IJAZAH")
                .setFont(font)
                .setFontSize(16f)
                .setBold()
                .setUnderline()
                .setMarginBottom(10f)
                .setFontColor(blackColor)
                .setTextAlignment(TextAlignment.CENTER)
        )

        val table = Table(floatArrayOf(3f, 1f, 6f))
        table.setWidth(200f)

        addRowToTable(table, "Nama Lengkap", nama, font, blackColor)
        addRowToTable(table, "NPM", npm, font, blackColor)
        addRowToTable(table, "Agama", agama, font, blackColor)
        addRowToTable(table, "Tempat/Tanggal Lahir", "${tempatLahir} / ${tanggalLahir}", font, blackColor)
        addRowToTable(table, "Jenis Kelamin", jenisKelamin, font, blackColor)
        addRowToTable(table, "Alamat", alamat, font, blackColor)
        addRowToTable(table, "Handphone", handphone, font, blackColor)
        addRowToTable(table, "Program Studi", programStudi, font, blackColor)
        addRowToTable(table, "Jenjang", jenjang, font, blackColor)
        addRowToTable(table, "Tanggal Lulus", tanggalLulus, font, blackColor)
        addRowToTable(table, "IPK", ipk, font, blackColor)
        addRowToTable(table, "Nama Ayah", namaAyah, font, blackColor)
        addRowToTable(table, "Nama Ibu", namaIbu, font, blackColor)
        addRowToTable(table, "Alamat Orang Tua", alamatOrtu, font, blackColor)
        addRowToTable(table, "Telpon Orang Tua", telponOrtu, font, blackColor)

        document.add(table)

        document.add(Paragraph("\nDengan ini menyatakan bahwa saya bersedia untuk mengambil ijazah atas nama saya paling lambat 6 (enam) bulan sejak tanggal kelulusan/wisuda saya dengan melengkapi persyaratan yang telah ditentukan. Apabila ijazah tersebut tidak diambil dalam jangka waktu yang telah ditentukan, maka saya bersedia menerima segala resiko (kehilangan atau kerusakan).\n").setFont(font).setFontSize(12f).setFontColor(blackColor))
        document.add(Paragraph("Demikian pernyataan ini saya buat dengan sadar dan tanpa tekanan dari pihak manapun.\n").setFont(font).setFontSize(12f).setFontColor(blackColor))

        document.add(Paragraph("Jakarta,               ").setFont(font).setFontSize(12f).setFontColor(blackColor).setTextAlignment(TextAlignment.RIGHT))
        document.add(Paragraph("Yang membuat pernyataan\n\n\n\n${nama}").setFont(font).setFontSize(12f).setFontColor(blackColor).setTextAlignment(TextAlignment.RIGHT))

        document.close()

        return file
    }
    private fun openPDF(file: File) {
        val context = requireContext()
        val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NO_HISTORY
        }

        val chooser = Intent.createChooser(intent, "Open PDF with")
        if (intent.resolveActivity(context.packageManager) != null) {
            startActivity(chooser)
        }
    }
    private fun addRowToTable(table: Table, key: String, value: String, font: PdfFont, fontColor: DeviceRgb) {
        table.addCell(Cell().add(Paragraph(key).setFont(font).setFontSize(12f).setFontColor(fontColor)).setBorder(Border.NO_BORDER))
        table.addCell(Cell().add(Paragraph(":").setFont(font).setFontSize(12f).setFontColor(fontColor)).setBorder(Border.NO_BORDER))
        table.addCell(Cell().add(Paragraph(value).setFont(font).setFontSize(12f).setFontColor(fontColor)).setBorder(Border.NO_BORDER))
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                generateAndOpenPDF()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
//    private fun test() {
//        binding.btncetak.setOnClickListener {
//            val nama = binding.inputNama.text.toString()
//            val npm = binding.inputNPM.text.toString()
//            val agama = binding.inputAgama.text.toString()
//            val tempatLahir = binding.inputtempatlahir.text.toString()
//            val tanggalLahir = binding.inputTanggallahir.text.toString()
//            val jenisKelamin = binding.inputjeniskelamin.text.toString()
//            val alamat = binding.inputAlamat.text.toString()
//            val handphone = binding.inputnohp.text.toString()
//            val programStudi = binding.inputprodi.text.toString()
//            val jenjang = binding.inputjenjang.text.toString()
//            val tanggalLulus = binding.inputtanggallulus.text.toString()
//            val ipk = binding.inputipk.text.toString()
//            val namaAyah = binding.inputnamaayah.text.toString()
//            val namaIbu = binding.inputnamaibu.text.toString()
//            val alamatOrtu = binding.inputalamatortu.text.toString()
//            val telponOrtu = binding.inputteleponortu.text.toString()
//
//            try {
//                generatePDF(
//                    nama,
//                    npm,
//                    agama,
//                    tempatLahir,
//                    tanggalLahir,
//                    jenisKelamin,
//                    alamat,
//                    handphone,
//                    programStudi,
//                    jenjang,
//                    tanggalLulus,
//                    ipk,
//                    namaAyah,
//                    namaIbu,
//                    alamatOrtu,
//                    telponOrtu
//                )
//                Toast.makeText(requireContext(), "PDF Generated Successfully", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "Failed to Generate PDF", Toast.LENGTH_SHORT).show()
//                e.printStackTrace()
//            }
//        }
//    }
//    private fun generatePDF(
//        nama: String,
//        npm: String,
//        agama: String,
//        tempatLahir: String,
//        tanggalLahir: String,
//        jenisKelamin: String,
//        alamat: String,
//        handphone: String,
//        programStudi: String,
//        jenjang: String,
//        tanggalLulus: String,
//        ipk: String,
//        namaAyah: String,
//        namaIbu: String,
//        alamatOrtu: String,
//        telponOrtu: String
//    ) {
//        val context = requireContext()
//        val fileName = "${context.filesDir.absolutePath}/generated.pdf"
//        val pdfWriter = PdfWriter(FileOutputStream(File(fileName)))
//        val pdfDocument = PdfDocument(pdfWriter)
//        val document = Document(pdfDocument)
//
//        val font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN)
//        val blackColor = DeviceRgb(0, 0, 0)
//
//        document.add(
//            Paragraph("SURAT PERNYATAAN PENGAMBILAN IJAZAH")
//                .setFont(font)
//                .setFontSize(16f)
//                .setBold()
//                .setUnderline()
//                .setMarginBottom(10f)
//                .setFontColor(blackColor)
//                .setTextAlignment(TextAlignment.CENTER)
//        )
//
//        val table = Table(floatArrayOf(3f, 1f, 6f))
//        table.setWidth(200f)
//
//        addRowToTable(table, "Nama Lengkap", nama, font, blackColor)
//        addRowToTable(table, "NPM", npm, font, blackColor)
//        addRowToTable(table, "Agama", agama, font, blackColor)
//        addRowToTable(table, "Tempat/Tanggal Lahir", "${tempatLahir} / ${tanggalLahir}", font, blackColor)
//        addRowToTable(table, "Jenis Kelamin", jenisKelamin, font, blackColor)
//        addRowToTable(table, "Alamat", alamat, font, blackColor)
//        addRowToTable(table, "Handphone", handphone, font, blackColor)
//        addRowToTable(table, "Program Studi", programStudi, font, blackColor)
//        addRowToTable(table, "Jenjang", jenjang, font, blackColor)
//        addRowToTable(table, "Tanggal Lulus", tanggalLulus, font, blackColor)
//        addRowToTable(table, "IPK", ipk, font, blackColor)
//        addRowToTable(table, "Nama Ayah", namaAyah, font, blackColor)
//        addRowToTable(table, "Nama Ibu", namaIbu, font, blackColor)
//        addRowToTable(table, "Alamat Orang Tua", alamatOrtu, font, blackColor)
//        addRowToTable(table, "Telpon Orang Tua", telponOrtu, font, blackColor)
//
//        document.add(table)
//
//        document.add(Paragraph("\nDengan ini menyatakan bahwa saya bersedia untuk mengambil ijazah atas nama saya paling lambat 6 (enam) bulan sejak tanggal kelulusan/wisuda saya dengan melengkapi persyaratan yang telah ditentukan. Apabila ijazah tersebut tidak diambil dalam jangka waktu yang telah ditentukan, maka saya bersedia menerima segala resiko (kehilangan atau kerusakan).\n").setFont(font).setFontSize(12f).setFontColor(blackColor))
//        document.add(Paragraph("Demikian pernyataan ini saya buat dengan sadar dan tanpa tekanan dari pihak manapun.\n").setFont(font).setFontSize(12f).setFontColor(blackColor))
//
//        document.add(Paragraph("Jakarta, 05 Jun 2024").setFont(font).setFontSize(12f).setFontColor(blackColor).setTextAlignment(TextAlignment.RIGHT))
//        document.add(Paragraph("Yang membuat pernyataan\n\n\n\n${nama}").setFont(font).setFontSize(12f).setFontColor(blackColor).setTextAlignment(TextAlignment.RIGHT))
//
//        document.close()
//    }
//    private fun addRowToTable(table: Table, label: String, value: String, font: com.itextpdf.kernel.font.PdfFont, color: DeviceRgb) {
////        table.addCell(Cell().add(Paragraph(label).setFont(font).setFontSize(12f).setFontColor(color)).setBorder(com.itextpdf.layout.border.Border.NO_BORDER))
////        table.addCell(Cell().add(Paragraph(":").setFont(font).setFontSize(12f).setFontColor(color)).setBorder(com.itextpdf.layout.border.Border.NO_BORDER))
////        table.addCell(Cell().add(Paragraph(value).setFont(font).setFontSize(12f).setFontColor(color)).setBorder(com.itextpdf.layout.border.Border.NO_BORDER))
//        table.addCell(
//            Cell().add(Paragraph(label).setFont(font).setFontSize(12f).setFontColor(color)).setBorder(
//                Border.NO_BORDER))
//        table.addCell(
//            Cell().add(Paragraph(":").setFont(font).setFontSize(12f).setFontColor(color)).setBorder(
//                Border.NO_BORDER))
//        table.addCell(
//            Cell().add(Paragraph(value).setFont(font).setFontSize(12f).setFontColor(color)).setBorder(
//                Border.NO_BORDER))
//    }


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
            viewModel.updateGraduateForm(
                UpdateGraduateForm(
                studentId,namaLengkap,tanggalLahir,tempatLahir,jenisKelamin,alamat,nomorHp,prodi,
                ipk.toFloat(),agama,jenjang,namaAyah,namaIbu,telpOrangTua,alamatOrangTua,tanggalLulus
            )
            )
            viewModel.updateGraduateFormResponse.observe(viewLifecycleOwner){
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
                                    Toast.makeText(requireContext(),"Berhasil Update Pendaftaran pengambilan ijasah",Toast.LENGTH_SHORT).show()
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
        viewModel.getGraduateFormByNpm.observe(viewLifecycleOwner){ resources ->
            when(resources){
                is Resource.Success ->{
                    showLoading(false)
                    val npm = resources.data?.data?.studentId
                    val fullName = resources.data?.data?.fullName
                    val tempatLahir = resources.data?.data?.birthPlace
                    tanggalLahir = resources.data?.data?.birthDate.toString()
                    val jenisKelamin = resources.data?.data?.gender
                    val alamat = resources.data?.data?.address
                    val noHp = resources.data?.data?.phoneNumber
                    val prodi = resources.data?.data?.major
                    val ipk = resources.data?.data?.gpa
                    tanggalLulus = resources.data?.data?.commencementDate.toString()
                    val jenjang = resources.data?.data?.level
                    val ayah = resources.data?.data?.dad
                    val ibu = resources.data?.data?.mother
                    val telpOrangTua = resources.data?.data?.parentTelp
                    val alamatOrangTua = resources.data?.data?.parentAddress
                    val agama = resources.data?.data?.religion
                    val npmEditTable = npm?.let { Editable.Factory.getInstance().newEditable(it) }
                    val fullNameEditTable = fullName?.let { Editable.Factory.getInstance().newEditable(it) }
                    val tempatLahirEditTable = tempatLahir?.let { Editable.Factory.getInstance().newEditable(it) }
                    val tanggalLahirEditTable = tanggalLahir?.let { Editable.Factory.getInstance().newEditable(it) }
                    val jenisKelaminEditTable = jenisKelamin?.let { Editable.Factory.getInstance().newEditable(it) }
                    val alamatEditTable = alamat?.let { Editable.Factory.getInstance().newEditable(it) }
                    val noHpEditTable = noHp?.let { Editable.Factory.getInstance().newEditable(it) }
                    val prodiEditTable = prodi?.let { Editable.Factory.getInstance().newEditable(it) }
                    val ipkEditTable = ipk?.let { Editable.Factory.getInstance().newEditable(it.toString())}
                    val tglLulusEditTable = tanggalLulus?.let { Editable.Factory.getInstance().newEditable(it)}
                    val jenjangEditTable = jenjang?.let { Editable.Factory.getInstance().newEditable(it)}
                    val ayahEditTable = ayah?.let { Editable.Factory.getInstance().newEditable(it)}
                    val ibuEditTable = ibu?.let { Editable.Factory.getInstance().newEditable(it)}
                    val telpOrangTuaEditTable = telpOrangTua?.let { Editable.Factory.getInstance().newEditable(it)}
                    val alamatOrangTuaEditTable = alamatOrangTua?.let { Editable.Factory.getInstance().newEditable(it)}
                    val agamaEditTable = agama?.let { Editable.Factory.getInstance().newEditable(it)}
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
                    binding.inputjenjang.text = jenjangEditTable
                    binding.inputnamaayah.text = ayahEditTable
                    binding.inputnamaibu.text = ibuEditTable
                    binding.inputteleponortu.text = telpOrangTuaEditTable
                    binding.inputalamatortu.text = alamatOrangTuaEditTable
                    binding.inputAgama.text = agamaEditTable
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
            findNavController().navigate(R.id.action_berhasilSimpanIjazahFragment_to_menuDashboardFragment)
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
            Toast.makeText(requireActivity(),"Jenis Kelamin: $selectedGender", Toast.LENGTH_SHORT).show()
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