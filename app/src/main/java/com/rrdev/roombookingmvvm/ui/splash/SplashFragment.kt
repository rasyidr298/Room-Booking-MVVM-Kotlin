package com.rrdev.roombookingmvvm.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rrdev.roombookingmvvm.R
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    private val SPLASH_SCREEN_TIME_OUT: Long = 3000
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash
            , container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(this)
            .load(R.drawable.ic_logo)
            .into(iv_splash_screen)

        la_splash.playAnimation()

        Handler().postDelayed({
            la_splash.visibility = View.GONE
            iv_splash_screen.visibility = View.VISIBLE

            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

        }, SPLASH_SCREEN_TIME_OUT)

    }
}
