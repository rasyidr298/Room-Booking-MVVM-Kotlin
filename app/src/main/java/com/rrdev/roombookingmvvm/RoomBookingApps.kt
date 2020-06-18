package com.rrdev.roombookingmvvm

import android.app.Application
import com.rrdev.roombookingmvvm.data.SharedPreferences.PrefManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class RoomBookingApps : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@RoomBookingApps))

        //bind() from singleton { NetworkConnectionInterceptor(instance()) }
//        bind() from singleton { MyApi() }
//        bind() from singleton { AppDatabase(instance()) }
//        bind() from singleton { UserRepository(instance(), instance(), instance()) }
//        bind() from provider { AuthViewModelFactory(instance()) }
//        bind() from provider { ProfileViewModelFactory(instance()) }
    }

    companion object {
        @get:Synchronized
        lateinit var instance: RoomBookingApps
        lateinit var prefManager: PrefManager
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefManager = PrefManager(this)
    }
}