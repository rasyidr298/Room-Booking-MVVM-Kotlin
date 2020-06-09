package com.rrdev.roombookingmvvm.ui.bookingRoom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rrdev.roombookingmvvm.data.repositories.BookingRoomRepository

@Suppress("UNCHECKED_CAST")
class BookingRoomViewModelFactory(
    private val repository: BookingRoomRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookingRoomViewModel(repository) as T
    }
}