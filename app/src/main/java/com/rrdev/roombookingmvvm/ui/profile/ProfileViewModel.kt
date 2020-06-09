package com.rrdev.roombookingmvvm.ui.profile

import androidx.lifecycle.ViewModel
import com.rrdev.roombookingmvvm.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {
    val user = repository.getUser()
}
