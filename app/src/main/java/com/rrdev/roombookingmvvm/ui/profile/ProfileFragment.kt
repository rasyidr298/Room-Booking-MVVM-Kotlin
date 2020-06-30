package com.rrdev.roombookingmvvm.ui.profile

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.RoomBookingApps.Companion.prefManager
import com.rrdev.roombookingmvvm.databinding.FragmentProfileBinding
import com.rrdev.roombookingmvvm.util.*
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware, ProfileListener {

    override val kodein by kodein()
    private val factory: ProfileViewModelFactory by instance<ProfileViewModelFactory>()
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false)

        viewModel = ViewModelProvider(
            this, factory).get(ProfileViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.profileListener = this
        pbProfile.hide()
        getProfile()
        logout()
    }

    override fun onStarted() {
        pbProfile.show()
    }

    override fun onSucces(message: String) {
        pbProfile.hide()
        root_layout_profile.snackbar(message)
    }

    override fun onFailure(message: String) {
        pbProfile.hide()
        root_layout_profile.snackbar(message)
    }

    
    private fun logout() {
        tvLogout.setOnClickListener {
            prefManager.clear()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun getProfile(){
        pbProfile.show()
        Coroutines.main {
            val profile = viewModel.profile.await()
            profile.observe(viewLifecycleOwner, Observer {
                tvNamaHeader.text = it.namaUser
                tvNimHeader.text = it.nim
                tvNIMProfile.text = it.nim
                etNamaProfile.text = Editable.Factory.getInstance().newEditable(it.namaUser)
                etNoTelpProfile.text = Editable.Factory.getInstance().newEditable(it.nohp)
                etPasswordProfile.text = Editable.Factory.getInstance().newEditable(it.password)
                val imageProfile = it.image
                Glide.with(this).load(BASE+imageProfile).into(ivProfile)
                Glide.with(this).load(BASE+imageProfile)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(30, 3)))
                    .into(imageView)
                pbProfile.hide()
            })
        }
    }
}
