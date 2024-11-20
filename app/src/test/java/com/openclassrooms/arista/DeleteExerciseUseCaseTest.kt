package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

/**
 * Classe de test pour le cas d'utilisation `DeleteExerciseUseCase`.
 * Ce test utilise JUnit4 et Mockito pour valider que le cas d'utilisation
 * supprime correctement un exercice via le référentiel (`ExerciseRepository`).
 */
@RunWith(JUnit4::class)
class DeleteExerciseUseCaseTest {
    @Mock
    private lateinit var exerciseRepository: ExerciseRepository // Mock du référentiel
    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase // Cas d'utilisation testé
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase // Utilisé pour valider les résultats

    /**
     * Configuration initiale avant chaque test.
     * Initialise les mocks avec Mockito et les cas d'utilisation avec les dépendances requises.
     */
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initialise les annotations Mockito
        deleteExerciseUseCase = DeleteExerciseUseCase(exerciseRepository)
        getAllExercisesUseCase = GetAllExercisesUseCase(exerciseRepository)
    }

    /**
     * Nettoyage après chaque test.
     * Supprime les mocks inlines pour éviter toute interférence entre les tests.
     */
    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    /**
     * Test le comportement du cas d'utilisation `DeleteExerciseUseCase`.
     * Vérifie qu'un exercice supprimé n'est plus présent dans la liste des exercices récupérés.
     */
    @Test
    fun `when repository deletes exercises, use case should delete it`() = runBlocking {
        // Arrange
        // Définition des exercices : un à supprimer et un autre restant
        val exerciseToDelete = Exercise(
            id = 1,
            startTime = LocalDateTime.now(),
            duration = 35,
            category = ExerciseCategory.Running,
            intensity = 2
        )
        val remainingExercise = Exercise(
            id = 2,
            startTime = LocalDateTime.now().plusHours(1),
            duration = 60,
            category = ExerciseCategory.Walking,
            intensity = 7
        )
        val fakeExercises = listOf(exerciseToDelete, remainingExercise)

        // Simule la liste initiale d'exercices dans le référentiel
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)
        // Act
        deleteExerciseUseCase.execute(exerciseToDelete) // Supprime l'exercice cible

        // Simule la mise à jour de la liste après suppression
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(listOf(remainingExercise))

        // Récupère la liste des exercices pour validation
        val result = getAllExercisesUseCase.execute()
        // Assert
        // Vérifie que l'exercice supprimé n'est plus dans la liste des exercices
        assertFalse(result.contains(exerciseToDelete))
    }
}