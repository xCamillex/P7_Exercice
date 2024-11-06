package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDtoDao {
    // Insère un nouvel utilisateur dans la table "user"
    @Insert
    suspend fun insertUser(user: UserDto): Long

    // Récupère un utilisateur de la table "user"
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserDto>

    // Supprime un utilisateur de la table "user" en fonction de son id
    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Long)
}