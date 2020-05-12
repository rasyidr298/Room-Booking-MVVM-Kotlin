package com.rrdev.roombookingmvvm

import android.app.Application
import com.rrdev.mvvmtrial.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.ui.auth.AuthViewModelFactory
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
        //lateinit var prefManager: PrefManager
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //prefManager = PrefManager(this)
    }
}