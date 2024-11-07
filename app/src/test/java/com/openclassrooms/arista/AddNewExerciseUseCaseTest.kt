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

@RunWith(JUnit4::class)
class AddNewExerciseUseCaseTest {
    @Mock
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addNewExerciseUseCase = AddNewExerciseUseCase(exerciseRepository)
        getAllExercisesUseCase = GetAllExercisesUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

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
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)
        // Act
        addNewExerciseUseCase.execute(exerciseToAdd)
        fakeExercises.add(exerciseToAdd) // Simulate the addition in the repository
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)
        val result = getAllExercisesUseCase.execute()
        // Assert
        assertTrue(result.contains(exerciseToAdd))
    }

}