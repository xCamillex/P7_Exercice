package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.UserDto

/**
 * Classe représentant un utilisateur avec des informations personnelles de base telles que l'identifiant,
 * le nom, l'email et le mot de passe. Fournit des méthodes pour convertir entre l'objet `User` et le DTO `UserDto`
 * afin de faciliter le stockage et la manipulation des données utilisateur.
 *
 * @property id L'identifiant unique de l'utilisateur.
 * @property name Le nom de l'utilisateur.
 * @property email L'adresse email de l'utilisateur.
 * @property password Le mot de passe de l'utilisateur.
 */
data class User(
    var id: Long,
    var name: String,
    var email: String,
    var password: String
) {

    /**
     * Convertit cette instance `User` en un `UserDto` pour le stockage en base de données.
     *
     * @return UserDto L'instance convertie de `UserDto` avec les mêmes valeurs de propriétés.
     */
    fun toDto(): UserDto {
        return UserDto(
            id = id,
            name = name,
            email = email,
            password = password
        )
    }

    companion object {
        /**
         * Fonction compagnon pour convertir un `UserDto` en objet métier `User`.
         *
         * @param dto L'instance `UserDto` à convertir.
         * @return User L'instance convertie de `User` avec les mêmes valeurs de propriétés.
         */
        fun fromDto(dto: UserDto): User {
            return User(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                password = dto.password
            )
        }
    }
}