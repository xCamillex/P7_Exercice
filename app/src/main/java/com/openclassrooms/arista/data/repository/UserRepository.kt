package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first

/**
 * Repository pour gérer les opérations liées à l'utilisateur actuel.
 * Cette classe agit comme une interface entre la couche de données (DAO) et la couche de logique métier,
 * facilitant les interactions avec les données de l'utilisateur en effectuant les opérations de
 * récupération, d'insertion et de suppression.
 *
 * @param userDao Le DAO utilisé pour interagir avec la table de l'utilisateur dans la base de données.
 */
class UserRepository(private val userDao: UserDtoDao) {

    /**
     * Récupère l'utilisateur actuel depuis la base de données.
     * Utilise un Flow pour observer les changements dans la base de données et collecte uniquement
     * la première émission.
     *
     * @return L'utilisateur actuel en tant qu'objet métier `User`, ou null si aucun utilisateur n'est trouvé.
     */
    suspend fun getUser(): User? {
        return userDao.getUser()
            .first() // Collecte la première émission du Flow pour obtenir l'utilisateur actuel
            ?.let { User.fromDto(it) } // Convertit le DTO en objet métier `User`
    }

    /**
     * Insère ou met à jour l'utilisateur dans la base de données.
     * Convertit l'objet métier `User` en DTO avant de l'insérer dans la base de données via le DAO.
     *
     * @param user L'utilisateur à insérer ou mettre à jour dans la base de données.
     */
    suspend fun insertUser(user: User) {
        userDao.insertUser(user.toDto()) // Convertit l'objet `User` en DTO et l'insère dans la base de données
    }

    /**
     * Supprime un utilisateur de la base de données en utilisant son identifiant.
     *
     * @param user L'utilisateur à supprimer de la base de données.
     * @throws IllegalArgumentException Si l'identifiant de l'utilisateur est nul.
     */
    suspend fun deleteUser(user: User) {
        userDao.deleteUserById(user.id) // Supprime l'utilisateur par son identifiant
    }
}