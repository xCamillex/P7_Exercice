package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject

/**
 * Cas d'utilisation pour supprimer un exercice en utilisant le référentiel `ExerciseRepository`.
 * Cette classe encapsule la logique métier pour la suppression d'un exercice, conformément à l'architecture Clean,
 * facilitant ainsi la gestion des opérations de suppression dans une couche dédiée.
 *
 * @constructor Injecte une instance d'`ExerciseRepository` pour gérer les opérations sur les exercices.
 */
class DeleteExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {

    /**
     * Exécute la suppression d'un exercice en appelant la fonction `deleteExercise` dans le référentiel.
     *
     * @param exercise L'objet `Exercise` représentant l'exercice à supprimer.
     */
    suspend fun execute(exercise: Exercise) {
        exerciseRepository.deleteExercise(exercise)
    }
}