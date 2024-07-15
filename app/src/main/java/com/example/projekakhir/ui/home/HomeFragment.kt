package com.example.projekakhir.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentHomeBinding
import com.google.android.material.imageview.ShapeableImageView
import java.lang.IllegalArgumentException


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menu.setOnClickListener {
            binding.constraintMenu.visibility = View.VISIBLE
            binding.menu.visibility = View.GONE
            binding.btnClose.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
            binding.constraintHome.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
            binding.constraintPersyaratan.setOnClickListener {
                findNavController().navigate(R.id.persyaratanFragment)
            }
            binding.constraintLogin.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }
            binding.constraintTutorVidio.setOnClickListener {
                openDocumentInBrowser("https://drive.google.com/file/d/1yqxHnKs1EnHWuCmyoc2aHmO2g_9-Abyt/view?usp=drivesdk")

            }
        }
        binding.btnDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        binding.cvFcIjazahSma.setOnClickListener { selectedIndex(0) }
        binding.cvFcAkteLahir.setOnClickListener { selectedIndex(1) }
        binding.cvFcKK.setOnClickListener { selectedIndex(2) }
        binding.cvFcKTP.setOnClickListener { selectedIndex(3) }
        binding.cvFcKTM.setOnClickListener { selectedIndex(4) }
        binding.cvPasFoto.setOnClickListener { selectedIndex(5) }
        binding.cvSkl.setOnClickListener { selectedIndex(6) }
        binding.cvSertifToeic.setOnClickListener { selectedIndex(7) }
    }
    private fun openDocumentInBrowser(url: String?) {
        url?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
        } ?: run {
            Toast.makeText(context, "Document URL tidak ada", Toast.LENGTH_SHORT).show()
        }
    }
    private fun selectedIndex(selectedIndex: Int) {
        val selectCvList = listOf(
            binding.cvFcIjazahSma,
            binding.cvFcAkteLahir,
            binding.cvFcKK,
            binding.cvFcKTP,
            binding.cvFcKTM,
            binding.cvPasFoto,
            binding.cvSkl,
            binding.cvSertifToeic
        )
        selectCvList.forEachIndexed { index, selectCv ->
            val isSelected = (index == selectedIndex)
            val iconColorRes = if (isSelected) R.color.white else R.color.black
            val linearColorRes = if (isSelected) R.color.biru else R.color.white

            selectCv.findViewById<ShapeableImageView>(getIconColor(index))
                .setColorFilter(resources.getColor(iconColorRes))
            selectCv.findViewById<LinearLayout>(getLinearColor(index))
                .setBackgroundColor(resources.getColor(linearColorRes))
        }
    }

    private fun getIconColor(index: Int): Int {
        return when (index) {
            0 -> R.id.ivCheck1
            1 -> R.id.ivCheck2
            2 -> R.id.ivCheck3
            3 -> R.id.ivCheck4
            4 -> R.id.ivCheck5
            5 -> R.id.ivCheck6
            6 -> R.id.ivCheck7
            7 -> R.id.ivCheck8
            else -> throw IllegalArgumentException("Invalid index")
        }
    }

    private fun getLinearColor(index: Int): Int {
        return when (index) {
            0 -> R.id.linearLayout
            1 -> R.id.linearLayout2
            2 -> R.id.linearLayout3
            3 -> R.id.linearLayout4
            4 -> R.id.linearLayout5
            5 -> R.id.linearLayout6
            6 -> R.id.linearLayout7
            7 -> R.id.linearLayout8
            else -> throw IllegalArgumentException("Invalid index")
        }

    }
}
