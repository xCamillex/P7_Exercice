package com.openclassrooms.arista.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsable de la gestion des données liées aux exercices dans l'application.
 * Ce ViewModel utilise des cas d'utilisation pour récupérer, ajouter et supprimer des exercices,
 * et expose les données via un `StateFlow` pour une gestion réactive de l'UI.
 *
 * Le ViewModel est responsable de l'appel des cas d'utilisation pour interagir avec le dépôt de données,
 * et met à jour le flux d'exercices chaque fois que les données sont modifiées.
 */
@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {

    // MutableStateFlow qui contient la liste des exercices. Utilisé pour transmettre les données à la vue.
    private val _exercisesFlow = MutableStateFlow<List<Exercise>>(emptyList())

    // StateFlow en lecture seule exposé pour l'observation dans la vue.
    val exercisesFlow: StateFlow<List<Exercise>> = _exercisesFlow.asStateFlow()

    /**
     * Initialisation du ViewModel : Charge les exercices dès que le ViewModel est créé.
     */
    init {
        loadAllExercises()
    }

    /**
     * Supprime un exercice et recharge la liste des exercices après la suppression.
     * Cette méthode est appelée depuis la vue lorsque l'utilisateur clique sur l'icône de suppression d'un exercice.
     */
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            // Appel du cas d'utilisation pour supprimer l'exercice
            deleteExerciseUseCase.execute(exercise)
            // Recharge la liste des exercices après la suppression
            loadAllExercises()
        }
    }

    /**
     * Charge tous les exercices à partir du cas d'utilisation et met à jour le flux d'exercices.
     * Cette méthode est utilisée pour obtenir et afficher la liste des exercices.
     */
    private fun loadAllExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            // Récupère la liste des exercices à partir du dépôt
            val exercises = getAllExercisesUseCase.execute()
            // Met à jour le flux d'exercices pour notifier la vue
            _exercisesFlow.value = exercises
        }
    }

    /**
     * Ajoute un nouvel exercice et recharge la liste des exercices après l'ajout.
     * Cette méthode est appelée depuis la vue lorsque l'utilisateur soumet un nouvel exercice.
     */
    fun addNewExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            // Appel du cas d'utilisation pour ajouter un nouvel exercice
            addNewExerciseUseCase.execute(exercise)
            // Recharge la liste des exercices après l'ajout
            loadAllExercises()
        }
    }
}
