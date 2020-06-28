package com.rrdev.roombookingmvvm

import android.app.Application
import android.content.ContentValues
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.rrdev.roombookingmvvm.data.SharedPreferences.PrefManager
import com.rrdev.roombookingmvvm.data.SharedPreferences.SharedPrefToken
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.*
import com.rrdev.roombookingmvvm.ui.auth.AuthViewModel
import com.rrdev.roombookingmvvm.ui.auth.AuthViewModelFactory
import com.rrdev.roombookingmvvm.ui.bookingRoom.BookingRoomViewModel
import com.rrdev.roombookingmvvm.ui.bookingRoom.BookingRoomViewModelFactory
import com.rrdev.roombookingmvvm.ui.detailRoom.DetailViewModel
import com.rrdev.roombookingmvvm.ui.detailRoom.DetailViewModelFactory
import com.rrdev.roombookingmvvm.ui.home.HomeViewModel
import com.rrdev.roombookingmvvm.ui.home.HomeViewModelFactory
import com.rrdev.roombookingmvvm.ui.mybooking.MyBookingViewModel
import com.rrdev.roombookingmvvm.ui.mybooking.MyBookingViewModelFactory
import com.rrdev.roombookingmvvm.ui.profile.ProfileViewModel
import com.rrdev.roombookingmvvm.ui.profile.ProfileViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class RoomBookingApps : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@RoomBookingApps))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }

        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { BookingRoomRepository(instance(), instance()) }
        bind() from singleton { MyBookingRepository(instance(), instance()) }
        bind() from singleton { RoomRepository(instance(), instance())  }
        bind() from singleton { ProfileRepository(instance(), instance())  }

        bind() from singleton { AuthViewModel(instance()) }
        bind() from singleton { BookingRoomViewModel(instance()) }
        bind() from singleton { DetailViewModel(instance()) }
        bind() from singleton { HomeViewModel(instance())  }
        bind() from singleton { MyBookingViewModel(instance()) }
        bind() from singleton { ProfileViewModel(instance()) }

        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { BookingRoomViewModelFactory(instance()) }
        bind() from singleton { DetailViewModelFactory(instance()) }
        bind() from singleton { HomeViewModelFactory(instance())  }
        bind() from singleton { MyBookingViewModelFactory(instance()) }
        bind() from singleton { ProfileViewModelFactory(instance()) }
    }

    companion object {
        @get:Synchronized
        lateinit var instance: RoomBookingApps
        lateinit var prefManager: PrefManager
        lateinit var prefManagerToken: SharedPrefToken
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefManager = PrefManager(this)
        prefManagerToken = SharedPrefToken.getInstance(this)
        getToken()
    }

    private fun getToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(ContentValues.TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                prefManager.spToken = token
            })
    }
}