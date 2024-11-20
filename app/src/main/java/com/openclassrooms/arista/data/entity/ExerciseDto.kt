package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Classe de données représentant un exercice dans la base de données.
 * Stockée dans la table "exercise", elle inclut les informations sur le début, la durée, la
 * catégorie et l'intensité de l'exercice.
 *
 * @param id Identifiant unique de l'exercice, généré automatiquement.
 * @param startTime Heure de début de l'exercice, en millisecondes depuis l'époque Unix.
 * @param duration Durée de l'exercice en minutes.
 * @param category Catégorie ou type de l'exercice (par exemple, "cardio", "force").
 * @param intensity Intensité de l'exercice, exprimée sur une échelle arbitraire.
 */
@Entity(tableName = "exercise")
data class ExerciseDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0, // Identifiant unique de l'exercice

    @ColumnInfo(name = "start_time")
    var startTime: Long, // Heure de début de l'exercice en millisecondes

    @ColumnInfo(name = "duration")
    var duration: Int, // Durée de l'exercice en minutes

    @ColumnInfo(name = "category")
    var category: String, // Catégorie de l'exercice (par exemple, "cardio", "force")

    @ColumnInfo(name = "intensity")
    var intensity: Int // Intensité de l'exercice (échelle arbitraire)
)
