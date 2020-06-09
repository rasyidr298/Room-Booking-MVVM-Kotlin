package com.rrdev.roombookingmvvm.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.rrdev.mvvmtrial.data.db.AppDatabase
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.SharedPreferences.SharedPrefToken
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.databinding.ProfileFragmentBinding
import com.rrdev.roombookingmvvm.ui.auth.AuthViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment() {

    //override val kodein by kodein()
    private lateinit var viewModel: ProfileViewModel
    //private val factory: ProfileViewModelFactory by instance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val sharedPrefToken = SharedPrefToken.getInstance(context)
        val repository = UserRepository(api,db,sharedPrefToken)
        val factory = ProfileViewModelFactory(repository)

        val binding :
                ProfileFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.profile_fragment,container,false)

        viewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel::class.java)

        binding.viewmodel =viewModel
        binding.lifecycleOwner = this
        return  binding.root
    }

}
