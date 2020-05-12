package com.rrdev.roombookingmvvm.ui.home.detail

import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.util.lazyDeferred

class DetailViewModel(
    private val namaRoom:String,
    private val repository: RoomRepository
) : ViewModel() {
    val detailRoom by lazyDeferred{
        repository.getDetailRoomById(namaRoom)
    }
}
