package com.rrdev.roombookingmvvm.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.data.SharedPreferences.SharedPrefToken
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.databinding.FragmentProfileBinding
import com.rrdev.roombookingmvvm.ui.auth.LoginFragment
import com.rrdev.roombookingmvvm.util.BASE
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val sharedPrefToken = SharedPrefToken.getInstance(context)
        val repository = UserRepository(api, db, sharedPrefToken)
        val factory = ProfileViewModelFactory(repository)

        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false)

        viewModel = ViewModelProviders.of(
            this, factory).get(ProfileViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageProfile()
        logout()
    }

    private fun logout() {
        btnLogout.setOnClickListener {
            prefManager.clear()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun imageProfile() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            val imageProfile = it.image
            Glide.with(this).load(BASE+imageProfile).into(ivProfile)
            Glide.with(this).load(BASE+imageProfile)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(30, 3)))
                .into(imageView)
        })
    }
}
