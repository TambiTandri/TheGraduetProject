package com.example.projekakhir.ui.persyaratan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentPersyaratanBinding
import com.google.android.material.imageview.ShapeableImageView
import java.lang.IllegalArgumentException


class PersyaratanFragment : Fragment() {

    private lateinit var binding: FragmentPersyaratanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPersyaratanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHome.setOnClickListener {
            findNavController().navigate(R.id.action_persyaratanFragment_to_homeFragment)
        }
        binding.menu.setOnClickListener {
            binding.constraintMenu.visibility = View.VISIBLE
            binding.menu.visibility = View.GONE
            binding.btnClose.setOnClickListener {
                findNavController().navigate(R.id.persyaratanFragment)
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