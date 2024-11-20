package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Classe représentant une entité de la base de données pour stocker les données de sommeil.
 * Cette classe est associée à la table "sleep" dans la base de données Room.
 *
 * @property id Identifiant unique de chaque enregistrement de sommeil, généré automatiquement.
 * @property startTime Heure de début du sommeil, exprimée en timestamp (millisecondes).
 * @property duration Durée totale du sommeil en minutes.
 * @property quality Indicateur de la qualité du sommeil, sur une échelle (par exemple de 1 à 5).
 */
@Entity(tableName = "sleep")
data class SleepDto(
    /** Clé primaire auto-générée pour chaque enregistrement de sommeil. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    /** Heure de début du sommeil, stockée en millisecondes (timestamp). */
    @ColumnInfo(name = "start_time")
    var startTime: Long,

    /** Durée du sommeil en minutes. */
    @ColumnInfo(name = "duration")
    var duration: Int,

    /** Qualité du sommeil, représentée par une valeur entière. */
    @ColumnInfo(name = "quality")
    var quality: Int
)
