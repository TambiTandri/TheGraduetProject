package com.example.projekakhir.ui.menudashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentMenuDashboardBinding


class MenuDashboardFragment : Fragment() {

    lateinit var binding : FragmentMenuDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.constraintDashboard.setOnClickListener {
            findNavController().navigate(R.id.action_menuDashboardFragment_to_dashboardFragment)
        }
        binding.constraintSkl.setOnClickListener {
            findNavController().navigate(R.id.action_menuDashboardFragment_to_sklFragment)
        }
        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.action_menuDashboardFragment_to_dashboardFragment)
        }
        binding.constraintPengambilanIjasah.setOnClickListener{
            findNavController().navigate(R.id.action_menuDashboardFragment_to_pengambilanIjazahFragment)
        }
        binding.constraintPendaftaran.setOnClickListener {
            findNavController().navigate(R.id.action_menuDashboardFragment_to_biodataPendaftaranFragment)
        }
        binding.constraintBerhasilPendaftaran.setOnClickListener {
            findNavController().navigate(R.id.action_menuDashboardFragment_to_berhasilSimpanPendaftaranFragment)
        }
        binding.constraintBerhasilSimpanPengambilanIjazah.setOnClickListener {
            findNavController().navigate(R.id.action_menuDashboardFragment_to_berhasilSimpanIjazahFragment)
        }
    }

}