package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import javax.inject.Inject

/**
 * Cas d'utilisation pour récupérer tous les enregistrements de sommeil en utilisant le référentiel
 * `SleepRepository`.
 * Cette classe encapsule la logique de récupération de tous les enregistrements de sommeil,
 * acilitant l'accès aux données
 * dans une architecture Clean en séparant la logique métier de la gestion des données.
 *
 * @constructor Injecte une instance de `SleepRepository` pour gérer les opérations liées au sommeil.
 */
class GetAllSleepsUseCase @Inject constructor(private val sleepRepository: SleepRepository) {

    /**
     * Exécute la récupération de tous les enregistrements de sommeil en appelant la fonction
     * `getAllSleeps` dans le référentiel.
     *
     * @return List<Sleep> Une liste de tous les enregistrements de sommeil.
     */
    suspend fun execute(): List<Sleep> {
        return sleepRepository.getAllSleeps()
    }
}