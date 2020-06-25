package com.rrdev.roombookingmvvm.data.repositories

import com.rrdev.roombookingmvvm.data.db.AppDatabase
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.SafeApiRequest
import com.rrdev.roombookingmvvm.data.network.responses.AuthResponse
import com.rrdev.roombookingmvvm.data.network.responses.CancelBookingResponse

class MyBookingRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    suspend fun cancelBooking(idBooking: String ): CancelBookingResponse {
        return apiRequest { api.cancelBooking(idBooking) }
    }
}