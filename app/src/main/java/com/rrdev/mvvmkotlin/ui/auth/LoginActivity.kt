package com.rrdev.mvvmkotlin.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rrdev.mvvmkotlin.R
import com.rrdev.mvvmkotlin.databinding.ActivityLoginBinding
import com.rrdev.mvvmkotlin.util.toast

class LoginActivity : AppCompatActivity(),AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSucces() {
        toast("Login Succes")
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}
