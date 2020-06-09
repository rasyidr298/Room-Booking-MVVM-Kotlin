package com.rrdev.roombookingmvvm.ui.bookingRoom

interface BookingRoomListener {
    fun onStarted()
    fun onSucces(message: String)
    fun onFailure(message: String)
}