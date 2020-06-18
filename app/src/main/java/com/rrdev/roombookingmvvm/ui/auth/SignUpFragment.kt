package com.rrdev.roombookingmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.SharedPreferences.SharedPrefToken
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.db.entities.User
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.databinding.FragmentSignUpBinding
import com.rrdev.roombookingmvvm.util.hide
import com.rrdev.roombookingmvvm.util.show
import com.rrdev.roombookingmvvm.util.snackbar
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(),AuthListener {

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val sharedPrefToken = SharedPrefToken.getInstance(requireContext())
        val repository = UserRepository(api,db,sharedPrefToken)
        val factory = AuthViewModelFactory(repository)

        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up,container,false)

        viewModel = ViewModelProviders.of(
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
