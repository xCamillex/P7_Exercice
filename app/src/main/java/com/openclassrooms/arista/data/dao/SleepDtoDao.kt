package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.SleepDto
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour gérer les opérations de la base de données sur la table "sleep".
 * Fournit des méthodes pour insérer, récupérer et supprimer des enregistrements de sommeil.
 */
@Dao
interface SleepDtoDao {
    /**
     * Insère un nouvel enregistrement de sommeil dans la table "sleep".
     *
     * @param sleep L'objet SleepDto à insérer dans la base de données.
     * @return L'identifiant (ID) du sommeil inséré sous forme de Long.
     */
    @Insert
    suspend fun insertSleep(sleep: SleepDto): Long

    /**
     * Récupère tous les enregistrements de sommeil de la table "sleep".
     *
     * @return Un Flow qui émet une liste de SleepDto, représentant tous les enregistrements de sommeil.
     */
    @Query("SELECT * FROM sleep")
    fun getAllSleeps(): Flow<List<SleepDto>>

    /**
     * Supprime un enregistrement de sommeil de la table "sleep" en fonction de son identifiant.
     *
     * @param id L'identifiant de l'enregistrement de sommeil à supprimer.
     */
    @Query("DELETE FROM sleep WHERE id = :id")
    suspend fun deleteSleepById(id: Long)
}