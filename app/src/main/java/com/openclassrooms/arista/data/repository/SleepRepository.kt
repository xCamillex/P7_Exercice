package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.first

/**
 * Repository pour gérer les opérations liées aux enregistrements de sommeil.
 * Cette classe agit comme une interface entre la couche de données (DAO) et la couche de logique métier.
 * Elle permet d'exécuter des opérations de récupération, d'ajout et de suppression des enregistrements
 * de sommeil dans la base de données.
 *
 * @param sleepDao Le DAO utilisé pour interagir avec la table des enregistrements de sommeil dans la base de données.
 */
class SleepRepository(private val sleepDao: SleepDtoDao) {

    /**
     * Récupère tous les enregistrements de sommeil depuis la base de données.
     * Utilise un Flow pour observer les changements dans la base de données et collecte seulement la première émission.
     *
     * @return Liste des enregistrements de sommeil, chaque DTO étant converti en objet métier `Sleep`.
     */
    suspend fun getAllSleeps(): List<Sleep> {
        return sleepDao.getAllSleeps()
            .first() // Collecte la première émission du Flow, pour récupérer une liste de sommeil actuelle
            .map { Sleep.fromDto(it) } // Convertit chaque DTO en objet `Sleep` pour une utilisation dans la logique métier
    }


    /**
     * Ajoute un nouvel enregistrement de sommeil dans la base de données.
     * Convertit l'objet `Sleep` en DTO avant de l'insérer dans la base de données via le DAO.
     *
     * @param sleep L'enregistrement de sommeil à ajouter à la base de données.
     */
    suspend fun addSleep(sleep: Sleep) {
        sleepDao.insertSleep(sleep.toDto()) // Convertit l'objet `Sleep` en DTO et l'insère dans la base de données
    }


    /**
     * Supprime un enregistrement de sommeil de la base de données en utilisant son identifiant.
     * L'enregistrement doit avoir un identifiant valide pour pouvoir être supprimé.
     *
     * @param sleep L'enregistrement de sommeil à supprimer de la base de données.
     * @throws IllegalArgumentException Si l'enregistrement de sommeil n'a pas d'ID valide.
     */
    suspend fun deleteSleep(sleep: Sleep) {
        // Vérifie que l'objet de sommeil possède un ID valide avant de le supprimer
        sleep.id?.let {
            sleepDao.deleteSleepById(
                id = sleep.id, // Supprime l'enregistrement basé sur son ID
            )
        } ?: throw IllegalArgumentException("Sleep record must have a valid ID to be deleted.")
    }
}