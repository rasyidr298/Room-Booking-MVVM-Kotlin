package com.rrdev.roombookingmvvm.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rrdev.roombookingmvvm.data.repositories.RoomRepository
import com.rrdev.roombookingmvvm.data.repositories.UserRepository
import com.rrdev.roombookingmvvm.ui.home.detail.DetailViewModel

@Suppress("UNCHECKED_CAST")
class FavoritelViewModelFactory(
    private val repository: RoomRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}