package com.rrdev.roombookingmvvm.ui.booking

import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.BookingRepository
import com.rrdev.roombookingmvvm.util.lazyDeferred

class BookingViewModel(
    repository: BookingRepository
) : ViewModel() {

    val booking by lazyDeferred{
        repository.getBooking()
    }
}
