package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

/**
 * Classe de test pour le cas d'utilisation `GetAllExercisesUseCase`.
 * Ce test utilise JUnit4 et Mockito pour valider que le cas d'utilisation
 * récupère correctement les exercices depuis le référentiel (`ExerciseRepository`).
 */
@RunWith(JUnit4::class)
class GetAllExercisesUseCaseTest {


    @Mock
    private lateinit var exerciseRepository: ExerciseRepository // Mock du référentiel


    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase // Cas d'utilisation testé

    /**
     * Configuration initiale avant chaque test.
     * Initialise les mocks avec Mockito et le cas d'utilisation avec les dépendances nécessaires.
     */
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initialise les annotations Mockito
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
     * Test le comportement du cas d'utilisation `GetAllExercisesUseCase`.
     * Vérifie que le cas d'utilisation retourne les exercices récupérés du référentiel.
     */
    @Test
    fun `when repository returns exercises, use case should return them`() = runBlocking {
        // Arrange
        // Définition de deux exercices fictifs dans une liste
        val fakeExercises = listOf(
            Exercise(
                startTime = LocalDateTime.now(),
                duration = 30,
                category = ExerciseCategory.Running,
                intensity = 5
            ),
            Exercise(
                startTime = LocalDateTime.now().plusHours(1),
                duration = 45,
                category = ExerciseCategory.Riding,
                intensity = 7
            )
        )

        // Simule le comportement du référentiel pour retourner la liste d'exercices
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)

        // Act
        // Appel du cas d'utilisation pour récupérer les exercices
        val result = getAllExercisesUseCase.execute()

        // Assert
        // Vérifie que la liste retournée par le cas d'utilisation correspond à celle définie
        assertEquals(fakeExercises, result)
    }

    /**
     * Test le comportement du cas d'utilisation `GetAllExercisesUseCase` lorsque le référentiel retourne une liste vide.
     * Vérifie que le cas d'utilisation retourne également une liste vide.
     */
    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange
        // Simule le comportement du référentiel pour retourner une liste vide
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(emptyList())

        // Act
        // Appel du cas d'utilisation pour récupérer les exercices
        val result = getAllExercisesUseCase.execute()

        // Assert
        // Vérifie que la liste retournée est vide
        assertTrue(result.isEmpty())
    }
}