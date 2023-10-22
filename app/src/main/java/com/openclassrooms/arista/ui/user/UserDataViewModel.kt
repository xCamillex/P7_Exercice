package com.openclassrooms.arista.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(private val getUserUsecase: GetUserUsecase) :
    ViewModel() {
    val userLiveData = MutableLiveData<User?>()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = getUserUsecase.execute()
        userLiveData.value = user
    }
}