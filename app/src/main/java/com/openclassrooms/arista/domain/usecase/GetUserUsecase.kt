package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import javax.inject.Inject

/**
 * Cas d'utilisation pour récupérer les informations de l'utilisateur actuel en utilisant le
 * référentiel `UserRepository`.
 * Cette classe encapsule la logique de récupération de l'utilisateur, permettant une gestion
 * centralisée des données utilisateur et facilitant l'accès à ces données dans une architecture Clean.
 *
 * @constructor Injecte une instance de `UserRepository` pour gérer les opérations liées aux utilisateurs.
 */
class GetUserUsecase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Exécute la récupération de l'utilisateur actuel en appelant la fonction `getUser` dans le référentiel.
     *
     * @return User? L'utilisateur actuel si trouvé, sinon `null`.
     */
    suspend fun execute(): User? {
        return userRepository.getUser()
    }
}