package com.openclassrooms.arista.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour gérer les données utilisateur dans l'application.
 * Utilise Hilt pour l'injection de dépendances et encapsule la logique de récupération
 * des données utilisateur en respectant les principes de l'architecture MVVM.
 *
 * Fonctionnalités principales :
 * - Fournit un `StateFlow` pour observer les données utilisateur.
 * - Gère le chargement des données utilisateur en arrière-plan avec des coroutines.
 * - Initialise automatiquement les données utilisateur au démarrage du ViewModel.
 */
@HiltViewModel
class UserDataViewModel @Inject constructor(private val getUserUsecase: GetUserUsecase) :
    ViewModel() {

    // Flux encapsulé contenant les données utilisateur observables.
    private val _userFlow = MutableStateFlow<User?>(null)
    val userFlow: StateFlow<User?> = _userFlow.asStateFlow()

    /**
     * Initialisation du ViewModel.
     * Charge automatiquement les données utilisateur dès que le ViewModel est créé.
     */
    init {
        loadUserData()
    }

    /**
     * Charge les données utilisateur en utilisant le cas d'utilisation `GetUserUsecase`.
     * Exécute l'opération dans un contexte IO pour éviter de bloquer le thread principal.
     * Met à jour le `StateFlow` avec les données récupérées.
     */
    private fun loadUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            // Exécute le cas d'utilisation pour récupérer l'utilisateur.
            val user = getUserUsecase.execute()

            // Met à jour la valeur du flux avec les données utilisateur récupérées.
            _userFlow.value = user
        }
    }
}