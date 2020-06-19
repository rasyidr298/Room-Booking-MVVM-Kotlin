package com.rrdev.roombookingmvvm.ui.detailRoom

import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.util.lazyDeferred

class DetailViewModel(
    private val namaRoom:String,
    private val repository: RoomRepository
) : ViewModel() {

    //get detail room
    val detailRoom by lazyDeferred{
        repository.getDetailRoomByName(namaRoom)
    }
}
