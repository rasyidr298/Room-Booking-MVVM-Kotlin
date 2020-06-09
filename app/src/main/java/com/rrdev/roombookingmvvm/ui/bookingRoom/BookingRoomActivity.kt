package com.rrdev.roombookingmvvm.ui.bookingRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rrdev.mvvmtrial.data.db.AppDatabase
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.SharedPreferences.SharedPrefToken
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.BookingRoomRepository
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.databinding.ActivityBookingRoomBinding
import com.rrdev.roombookingmvvm.databinding.ActivitySignUpBinding
import com.rrdev.roombookingmvvm.ui.auth.AuthViewModel
import com.rrdev.roombookingmvvm.ui.auth.AuthViewModelFactory
import com.rrdev.roombookingmvvm.util.hide
import com.rrdev.roombookingmvvm.util.show
import com.rrdev.roombookingmvvm.util.snackbar
import kotlinx.android.synthetic.main.activity_booking_room.*
import kotlinx.android.synthetic.main.activity_login.*

class BookingRoomActivity : AppCompatActivity(),BookingRoomListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val repository = BookingRoomRepository(api)
        val factory = BookingRoomViewModelFactory(repository)

        super.onCreate(savedInstanceState)
        val binding: ActivityBookingRoomBinding = DataBindingUtil.setContentView(this, R.layout.activity_booking_room)
        val viewModel = ViewModelProviders.of(this, factory).get(BookingRoomViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.bookingRoomListener = this
    }

    override fun onStarted() {
        pbBookingRoom.show()
    }

    override fun onSucces(message: String) {
        pbBookingRoom.hide()
        root_layoutBookingRoom.snackbar(message)
    }

    override fun onFailure(message: String) {
        pbBookingRoom.hide()
        root_layoutBookingRoom.snackbar(message)
    }
}
