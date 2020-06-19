package com.rrdev.roombookingmvvm.ui.detailRoom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val namaRoom: String,
    private val repository: RoomRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(
            namaRoom,
            repository
        ) as T
    }
}