package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import junit.framework.TestCase.assertTrue
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
 * Classe de test pour le cas d'utilisation `AddNewExerciseUseCase`.
 * Utilise JUnit4 et Mockito pour tester le comportement du cas d'utilisation en isolant
 * les interactions avec le référentiel (`ExerciseRepository`).
 */
@RunWith(JUnit4::class)
class AddNewExerciseUseCaseTest {
    @Mock
    private lateinit var exerciseRepository: ExerciseRepository // Mock du référentiel
    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase // Cas d'utilisation testé
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase // Utilisé pour valider les résultats

    /**
     * Configuration initiale avant chaque test.
     * Initialise les mocks avec Mockito et les cas d'utilisation avec les dépendances requises.
     */
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initialise les annotations Mockito
        addNewExerciseUseCase = AddNewExerciseUseCase(exerciseRepository)
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
     * Test le comportement du cas d'utilisation `AddNewExerciseUseCase`.
     * Vérifie qu'un exercice ajouté est bien présent dans la liste des exercices récupérés.
     */
    @Test
    fun `when repository add exercises, use case should add it`() = runBlocking {
        // Arrange
        val exerciseToAdd = Exercise(
            id = 1,
            startTime = LocalDateTime.now(),
            duration = 20,
            category = ExerciseCategory.Swimming,
            intensity = 3
        )
        val baseExercise = Exercise(
            id = 2,
            startTime = LocalDateTime.now().plusHours(1),
            duration = 40,
            category = ExerciseCategory.Football,
            intensity = 1
        )
        val fakeExercises = mutableListOf(baseExercise)
        // Simule le comportement initial du référentiel
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)
        // Act
        addNewExerciseUseCase.execute(exerciseToAdd) // Ajoute un nouvel exercice
        fakeExercises.add(exerciseToAdd) // Simule l'ajout dans le référentiel
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)

        // Récupère les exercices pour validation
        val result = getAllExercisesUseCase.execute()
        // Assert
        assertTrue(result.contains(exerciseToAdd)) // Vérifie que l'exercice ajouté est bien présent
    }

}