package com.rrdev.roombookingmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.entities.User
import com.rrdev.roombookingmvvm.databinding.FragmentSignUpBinding
import com.rrdev.roombookingmvvm.util.hide
import com.rrdev.roombookingmvvm.util.show
import com.rrdev.roombookingmvvm.util.snackbar
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SignUpFragment : Fragment(),AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance<AuthViewModelFactory>()
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up,container,false)

        viewModel = ViewModelProvider(
            this,factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authListener = this
        progres_bar_signup.hide()
        onLogin()
    }

    override fun onStarted() {
        progres_bar_signup.show()
    }

    override fun onSucces(user: User) {
        progres_bar_signup.hide()
    }

    override fun onFailure(message: String) {
        progres_bar_signup.hide()
        root_layout_signup.snackbar(message)
    }

    private fun onLogin() {
        onLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }
}
