package com.rrdev.roombookingmvvm.data.network.responses


import com.google.gson.annotations.SerializedName
import com.rrdev.roombookingmvvm.data.db.entities.Booking

data class BookingResponse(
    @SerializedName("data")
    val booking: List<Booking>
)