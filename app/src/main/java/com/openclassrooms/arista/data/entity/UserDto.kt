package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Classe représentant une entité de la base de données pour stocker les informations des utilisateurs.
 * Cette classe est associée à la table "user" dans la base de données Room.
 *
 * @property id Identifiant unique de chaque utilisateur, généré automatiquement.
 * @property name Nom de l'utilisateur.
 * @property email Adresse e-mail de l'utilisateur, utilisée comme identifiant de connexion.
 * @property password Mot de passe de l'utilisateur. Il est recommandé de stocker ce champ sous forme
 * cryptée pour des raisons de sécurité.
 */
@Entity(tableName = "user")
data class UserDto(
    /** Clé primaire auto-générée pour chaque utilisateur. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    /** Nom de l'utilisateur. */
    @ColumnInfo(name = "name")
    var name: String,

    /** Adresse e-mail de l'utilisateur. */
    @ColumnInfo(name = "email")
    var email: String,

    /** Mot de passe de l'utilisateur. Doit être stocké de façon sécurisée (chiffré). */
    @ColumnInfo(name = "password")
    var password: String,
)
