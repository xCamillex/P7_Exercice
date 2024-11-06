package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.ExerciseDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDtoDao {
    // Insère un nouvel exercice dans la table "exercice"
    @Insert
    suspend fun insertExercise(exercise: ExerciseDto): Long

    // Récupère tous les exercices de la table "exercice" sous forme de flow
    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<ExerciseDto>>

    // Supprime un exercice de la table "exercice" en fonction de son identifiant "id"
    @Query("DELETE FROM exercise WHERE id = :id")
    suspend fun deleteExerciseById(id: Long)
}