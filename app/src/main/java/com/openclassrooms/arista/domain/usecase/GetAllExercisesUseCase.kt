package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject

/**
 * Cas d'utilisation pour récupérer tous les exercices en utilisant le référentiel `ExerciseRepository`.
 * Cette classe encapsule la logique de récupération de tous les exercices, facilitant ainsi l'accès aux données
 * dans une architecture Clean en séparant la logique métier de la logique de données.
 *
 * @constructor Injecte une instance d'`ExerciseRepository` pour gérer les opérations sur les exercices.
 */
class GetAllExercisesUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {

    /**
     * Exécute la récupération de tous les exercices en appelant la fonction `getAllExercises` dans le référentiel.
     *
     * @return List<Exercise> Une liste de tous les exercices disponibles.
     */
    suspend fun execute(): List<Exercise> {
        return exerciseRepository.getAllExercises()
    }
}