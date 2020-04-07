package com.rrdev.mvvmkotlin.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rrdev.mvvmkotlin.R
import com.rrdev.mvvmkotlin.databinding.ActivityLoginBinding
import com.rrdev.mvvmkotlin.util.hide
import com.rrdev.mvvmkotlin.util.show
import com.rrdev.mvvmkotlin.util.toast
import com.rrdev.mvvmtrial.data.db.entities.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        progres_bar.show()
        toast("Login Started")
    }

    override fun onSucces(user: User) {
        progres_bar.hide()
        toast("${user.name} is logged in")
    }

    override fun onFailure(message: String) {
        progres_bar.hide()
        toast(message)
    }
}
