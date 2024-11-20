package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.ExerciseDto
import kotlinx.coroutines.flow.Flow

/**
 * Interface DAO pour gérer les opérations de la base de données sur la table "exercise".
 * Fournit des méthodes pour insérer, récupérer et supprimer des exercices.
 */
@Dao
interface ExerciseDtoDao {
    /**
     * Insère un nouvel exercice dans la table "exercise".
     *
     * @param exercise L'objet ExerciseDto à insérer dans la base de données.
     * @return L'identifiant (ID) de l'exercice inséré sous forme de Long.
     */
    @Insert
    suspend fun insertExercise(exercise: ExerciseDto): Long

    /**
     * Récupère tous les exercices de la table "exercise".
     *
     * @return Un Flow qui émet une liste d'ExerciseDto, représentant tous les exercices.
     */
    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<ExerciseDto>>

    /**
     * Supprime un exercice de la table "exercise" en fonction de son identifiant.
     *
     * @param id L'identifiant de l'exercice à supprimer.
     */
    @Query("DELETE FROM exercise WHERE id = :id")
    suspend fun deleteExerciseById(id: Long)
}