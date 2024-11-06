package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first

class UserRepository(private val userDao: UserDtoDao) {

    // Get current user
    suspend fun getUser(): User? {
        return userDao.getUser()
            .first() // Collect the first emission of the Flow
            ?.let { User.fromDto(it) } // Convert DTO in User
    }
    // Insert user
    suspend fun insertUser(user: User) {
        userDao.insertUser(user.toDto()) // Convert User to DTO and insert or update
    }
    // Delete user
    suspend fun deleteUser(user: User) {
        userDao.deleteUserById(user.id)
    }
}