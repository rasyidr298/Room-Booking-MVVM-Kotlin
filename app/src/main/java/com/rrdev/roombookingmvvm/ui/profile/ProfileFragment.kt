package com.rrdev.roombookingmvvm.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.databinding.FragmentProfileBinding
import com.rrdev.roombookingmvvm.ui.mybooking.MyBookingViewModelFactory
import com.rrdev.roombookingmvvm.util.BASE
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: ProfileViewModelFactory by instance()
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
