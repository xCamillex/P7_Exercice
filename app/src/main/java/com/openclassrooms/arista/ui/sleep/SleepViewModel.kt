package com.openclassrooms.arista.ui.sleep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour gérer les données de sommeil dans l'application.
 * Utilise un `StateFlow` pour fournir des données réactives à la vue tout en respectant le cycle de vie.
 * Les données sont récupérées à partir d'un cas d'utilisation spécifique via un repository.
 *
 * @property getAllSleepsUseCase Cas d'utilisation injecté pour récupérer toutes les sessions de sommeil.
 */
@HiltViewModel
class SleepViewModel @Inject constructor(private val getAllSleepsUseCase: GetAllSleepsUseCase) :
    ViewModel() {

    // StateFlow exposé pour la vue, contenant une liste des sessions de sommeil.
    private val _sleeps = MutableStateFlow<List<Sleep>>(emptyList())
    val sleeps: StateFlow<List<Sleep>> = _sleeps.asStateFlow()

    // Bloc d'initialisation appelé à la création de l'instance, déclenchant le chargement des données.
    init {
        fetchSleeps()
    }

    /**
     * Récupère les données de sommeil en appelant le cas d'utilisation approprié.
     * La récupération est effectuée dans un contexte IO pour éviter de bloquer le thread principal.
     * Les données récupérées sont ensuite mises à jour dans le StateFlow.
     */
    fun fetchSleeps() {
        viewModelScope.launch(Dispatchers.IO) {
            val sleepList = getAllSleepsUseCase.execute()
            _sleeps.value = sleepList
        }
    }
}