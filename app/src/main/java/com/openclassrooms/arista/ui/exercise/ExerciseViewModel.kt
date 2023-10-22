package com.openclassrooms.arista.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {
    val exercisesLiveData = MutableLiveData<List<Exercise>>()

    init {
        loadAllExercises()
    }

    fun deleteExercise(exercise: Exercise) {
        deleteExerciseUseCase.execute(exercise)
        loadAllExercises()
    }

    private fun loadAllExercises() {
        val exercises = getAllExercisesUseCase.execute()
        exercisesLiveData.value = exercises
    }

    fun addNewExercise(exercise: Exercise) {
        addNewExerciseUseCase.execute(exercise)
        loadAllExercises()
    }
}