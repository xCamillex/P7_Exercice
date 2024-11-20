package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour gérer les opérations de la base de données sur la table "user".
 * Fournit des méthodes pour insérer, récupérer et supprimer des utilisateurs.
 */
@Dao
interface UserDtoDao {
    /**
     * Insère un nouvel utilisateur dans la table "user".
     *
     * @param user L'objet UserDto représentant l'utilisateur à insérer.
     * @return L'identifiant (ID) de l'utilisateur inséré sous forme de Long.
     */
    @Insert
    suspend fun insertUser(user: UserDto): Long

    /**
     * Récupère un utilisateur unique de la table "user".
     *
     * @return Un Flow émettant un objet UserDto, représentant l'utilisateur.
     */
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserDto>

    /**
     * Supprime un utilisateur de la table "user" en fonction de son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à supprimer.
     */
    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Long)
}