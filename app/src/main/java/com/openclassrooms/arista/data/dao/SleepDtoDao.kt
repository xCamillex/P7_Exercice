package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.SleepDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDtoDao {
    // Insère un nouveau sommeil dans la table "sleep"
    @Insert
    suspend fun insertSleep(sleep: SleepDto): Long

    // Récupère tous les sommeils de la table "sleep" sous forme de Flow
    @Query("SELECT * FROM sleep")
    suspend fun getAllSleeps(): Flow<List<SleepDto>>

    // Supprime un sommeil de la table "sleep" en fonction de son id
    @Query("DELETE FROM sleep WHERE id = :id")
    suspend fun deleteSleepById(id: Long)
}