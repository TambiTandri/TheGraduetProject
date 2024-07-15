package com.example.projekakhir.ui.splash

import android.media.session.MediaSession.Token
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.projekakhir.R
import com.example.projekakhir.databinding.FragmentSplashScreenBinding
import com.example.projekakhir.network.model.response.getAllStudent.Data
import com.example.projekakhir.utils.TokenPreferences
import com.example.projekakhir.utils.ViewModelFactory
import com.example.projekakhir.utils.dataStore
import com.example.projekakhir.utils.ext.decodeJwtPayload
import com.example.projekakhir.utils.ext.safeNavigate
import kotlinx.coroutines.launch
import org.json.JSONObject

class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    lateinit var tokenPreferences: TokenPreferences

    private val viewModel: SplashScreenViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashDelay()
    }

    private fun splashDelay() {
        val time = 2000L
        Handler().postDelayed({
            setNavigationToken()
        },time)
    }

    private fun setNavigationToken() {
        val dataStore: DataStore<Preferences> = requireContext().dataStore
        val tokenPreferences = TokenPreferences.getInstance(dataStore)
        lifecycleScope.launch {
            val token = tokenPreferences.getToken()
            val role = tokenPreferences.getRole()
            if (token != null && role != null){
                when{
                    role.contains("admin") -> findNavController().safeNavigate(R.id.action_splashScreenFragment_to_adminFragment)
                    role.contains("student") -> findNavController().safeNavigate(R.id.action_splashScreenFragment_to_dashboardFragment)
                    else -> findNavController().safeNavigate(R.id.action_splashScreenFragment_to_homeFragment)
                }
            }else{
                findNavController().safeNavigate(R.id.action_splashScreenFragment_to_homeFragment)
            }
        }
    }

//    private fun setNavigationToken() {
//        val dataStore: DataStore<Preferences> = requireContext().dataStore
//        tokenPreferences = TokenPreferences.getInstance(dataStore)
//        val token = TokenPreferences.getTokenSynchronously(dataStore)
//        if (token != null){
//            findNavController().navigate(R.id.action_splashScreenFragment_to_dashboardFragment)
//        }else{
//            findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
//        }
//    }
}