package com.rrdev.roombookingmvvm

import android.app.Application
import android.content.ContentValues
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.rrdev.roombookingmvvm.data.SharedPreferences.PrefManager
import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.*
import com.rrdev.roombookingmvvm.ui.auth.AuthViewModelFactory
import com.rrdev.roombookingmvvm.ui.bookingRoom.BookingRoomViewModelFactory
import com.rrdev.roombookingmvvm.ui.detailRoom.DetailViewModelFactory
import com.rrdev.roombookingmvvm.ui.home.HomeViewModelFactory
import com.rrdev.roombookingmvvm.ui.mybooking.MyBookingViewModelFactory
import com.rrdev.roombookingmvvm.ui.profile.ProfileViewModelFactory
import com.rrdev.roombookingmvvm.util.MyNotification
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
        lateinit var myNotification: MyNotification
    }

    override fun onCreate() {
        getToken()
        instance = this
        prefManager = PrefManager(this)
        myNotification = MyNotification(this)
        super.onCreate()
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