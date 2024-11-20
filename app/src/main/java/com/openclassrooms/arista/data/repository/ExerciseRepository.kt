package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.first

/**
 * Repository pour gérer les opérations liées aux exercices.
 * Cette classe agit comme un intermédiaire entre la couche de données (DAO) et la couche de logique métier.
 * Elle permet d'exécuter des opérations de récupération, d'ajout et de suppression des exercices dans la base de données.
 *
 * @param exerciseDao Le DAO utilisé pour interagir avec la table des exercices dans la base de données.
 */
class ExerciseRepository(private val exerciseDao: ExerciseDtoDao) {


    /**
     * Récupère tous les exercices de la base de données.
     * Utilise un Flow pour observer les changements dans la base de données, mais collecte seulement la première émission.
     *
     * @return Liste des exercices convertis depuis le DTO vers le modèle métier `Exercise`.
     */
    suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAllExercises()
            .first()// Collecte la première émission du Flow, ce qui permet de récupérer la liste des exercices à un instant donné
            .map { Exercise.fromDto(it) } // Convertit chaque DTO en un objet de type `Exercise` pour être utilisé dans la logique métier
    }


    /**
     * Ajoute un nouvel exercice dans la base de données.
     * Convertit l'objet `Exercise` en un DTO avant de l'insérer dans la base de données via le DAO.
     *
     * @param exercise L'exercice à ajouter à la base de données.
     */
    suspend fun addExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise.toDto()) // Convertit l'objet `Exercise` en DTO et l'insère dans la base de données
    }


    /**
     * Supprime un exercice de la base de données en utilisant son identifiant.
     * L'exercice doit avoir un identifiant valide pour être supprimé.
     *
     * @param exercise L'exercice à supprimer de la base de données.
     * @throws IllegalArgumentException Si l'exercice n'a pas d'ID valide.
     */
    suspend fun deleteExercise(exercise: Exercise) {
        // Si l'exercice a un ID, on le passe au DAO pour suppression
        exercise.id?.let {
            exerciseDao.deleteExerciseById(
                id = exercise.id, // Supprime l'exercice basé sur son ID
            )
        } ?: throw IllegalArgumentException("Exercise must have a valid ID to be deleted.")
    }
}