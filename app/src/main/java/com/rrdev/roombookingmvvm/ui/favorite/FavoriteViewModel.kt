package com.rrdev.roombookingmvvm.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.util.lazyDeferred
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: RoomRepository
) : ViewModel() {

//    fun getFavorite() = repository.getFavorite()
//
//    fun deleteFavorite(roomsFavorite: Rooms) = viewModelScope.launch {
//        repository.deleteFavorite(roomsFavorite)
//    }
}
