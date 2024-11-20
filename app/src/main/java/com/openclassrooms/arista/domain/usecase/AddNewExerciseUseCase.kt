package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject

/**
 * Cas d'utilisation pour ajouter un nouvel exercice en utilisant le référentiel `ExerciseRepository`.
 * Cette classe encapsule la logique métier pour l'ajout d'un exercice, facilitant la séparation des
 * responsabilités et l'implémentation de l'architecture Clean.
 *
 * @constructor Injecte une instance d'`ExerciseRepository` pour gérer les opérations sur les exercices.
 */
class AddNewExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {

    /**
     * Exécute l'ajout d'un exercice en appelant la fonction `addExercise` dans le référentiel.
     *
     * @param exercise L'objet `Exercise` représentant l'exercice à ajouter.
     */
    suspend fun execute(exercise: Exercise) {
        exerciseRepository.addExercise(exercise)
    }
}