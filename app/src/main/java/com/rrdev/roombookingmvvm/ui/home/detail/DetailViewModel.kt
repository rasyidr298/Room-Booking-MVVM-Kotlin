package com.rrdev.roombookingmvvm.ui.home.detail

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.ui.bookingRoom.BookingRoomActivity
import com.rrdev.roombookingmvvm.util.lazyDeferred

class DetailViewModel(
    private val namaRoom:String,
    private val repository: RoomRepository
) : ViewModel() {

    fun onBookingRoom(view: View){
        Intent(view.context, BookingRoomActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    //get detail room
    val detailRoom by lazyDeferred{
        repository.getDetailRoomByName(namaRoom)
    }


}
