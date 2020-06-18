package com.rrdev.roombookingmvvm.data.repositories

import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest

class MyBookingRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

}