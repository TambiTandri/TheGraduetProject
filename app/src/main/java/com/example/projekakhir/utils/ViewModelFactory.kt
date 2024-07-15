package com.example.projekakhir.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projekakhir.di.Injection
import com.example.projekakhir.ui.admin.AdminFragment.AdminViewModel
import com.example.projekakhir.ui.berhasilsimpanijasah.BerhasilSimpanIjazahViewModel
import com.example.projekakhir.ui.berhasilsimpanpendaftaran.BerhasilSimpanPendaftaranViewModel
import com.example.projekakhir.ui.biodatapendaftaran.BiodataPendaftaranViewModel
import com.example.projekakhir.ui.dashboardfragment.DashboardViewModel
import com.example.projekakhir.ui.forgotpassword.ForgotPasswordViewModel
import com.example.projekakhir.ui.login.LoginViewModel
import com.example.projekakhir.ui.pengambilan.PengambilanIjazahViewModel
import com.example.projekakhir.ui.register.RegisterViewModel
import com.example.projekakhir.ui.skl.SklViewModel
import com.example.projekakhir.ui.splash.SplashScreenViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create (modelClass: Class<T>) : T{
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->{
                RegisterViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(BiodataPendaftaranViewModel::class.java) ->{
                BiodataPendaftaranViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(BerhasilSimpanPendaftaranViewModel::class.java) ->{
                BerhasilSimpanPendaftaranViewModel(Injection.provideRepository(context), Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) ->{
                DashboardViewModel(Injection.provideDataStore(context),Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(SklViewModel::class.java) ->{
                SklViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(AdminViewModel::class.java) ->{
               AdminViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) ->{
                SplashScreenViewModel(Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(PengambilanIjazahViewModel::class.java) ->{
                PengambilanIjazahViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(BerhasilSimpanIjazahViewModel::class.java) ->{
                BerhasilSimpanIjazahViewModel(Injection.provideRepository(context),Injection.provideDataStore(context)) as T
            }
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) ->{
                ForgotPasswordViewModel(Injection.provideRepository(context)) as T
            }
            else ->throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}