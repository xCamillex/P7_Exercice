package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.ExerciseDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.TimeZone

/**
 * Classe représentant un exercice, avec des informations sur le moment de l'exercice, la durée, la
 * catégorie et l'intensité.
 * Elle contient des méthodes pour convertir l'instance en DTO (`toDto`) pour une utilisation avec Room,
 * et une fonction compagnon (`fromDto`) pour transformer un DTO en objet `Exercise`.
 *
 * @property id L'identifiant unique de l'exercice (nullable pour les nouveaux enregistrements non encore
 * insérés dans la base de données).
 * @property startTime La date et l'heure de début de l'exercice, représentée sous forme de `LocalDateTime`.
 * @property duration La durée de l'exercice en minutes.
 * @property category La catégorie de l'exercice, représentée sous forme d'énumération `ExerciseCategory`.
 * @property intensity Le niveau d'intensité de l'exercice, sous forme d'entier.
 */
data class Exercise(
    val id: Long? = null,
    var startTime: LocalDateTime,
    var duration: Int,
    var category: ExerciseCategory,
    var intensity: Int
) {

    /**
     * Convertit cette instance `Exercise` en un `ExerciseDto` pour stockage dans la base de données.
     * La conversion du champ `startTime` vers un timestamp en millisecondes UTC est nécessaire pour le stockage.
     *
     * @return ExerciseDto L'instance convertie de `ExerciseDto` pour le stockage dans la base de données.
     * @throws IllegalArgumentException Si l'id est nul lors de la conversion, une exception est levée
     * pour garantir un id valide.
     */
    fun toDto() = ExerciseDto(
        id = id ?: throw IllegalArgumentException("Id should not be null"), // Vérifie que l'ID n'est pas nul
        startTime = startTime.toInstant(ZoneOffset.UTC).toEpochMilli(), // Convertit `startTime` en millisecondes depuis l'époque UTC
        duration = duration,
        category = category.toString(), // Convertit `category` en `String` pour le stockage
        intensity = intensity
    )

    companion object {
        /**
         * Fonction compagnon pour convertir un `ExerciseDto` en objet `Exercise`.
         * Reconvertit le timestamp `startTime` en `LocalDateTime` et transforme le champ `category`
         * en `ExerciseCategory`.
         *
         * @param dto L'instance `ExerciseDto` à convertir.
         * @return Exercise L'instance convertie de `Exercise`.
         */
        fun fromDto(dto: ExerciseDto): Exercise {
            return Exercise(
                id = dto.id,
                startTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(dto.startTime),
                    TimeZone.getDefault().toZoneId() // Convertit le timestamp en `LocalDateTime` dans le fuseau horaire local
                ),
                duration = dto.duration,
                category = ExerciseCategory.valueOf(dto.category), // Convertit la chaîne `category` en `ExerciseCategory`
                intensity = dto.intensity
            )
        }
    }
}