package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.TimeZone

/**
 * Classe représentant un enregistrement de sommeil, contenant des informations telles que l'heure de début,
 * la durée et la qualité du sommeil. Fournit des méthodes pour convertir entre l'objet `Sleep` et le DTO `SleepDto`
 * afin de faciliter le stockage en base de données et la manipulation de données dans l'application.
 *
 * @property id L'identifiant unique de l'enregistrement de sommeil (nullable pour les nouveaux enregistrements).
 * @property startTime La date et l'heure de début du sommeil, représentée sous forme de `LocalDateTime`.
 * @property duration La durée du sommeil en minutes.
 * @property quality La qualité du sommeil, représentée par un entier (par exemple, une échelle de 1 à 5).
 */
data class Sleep(
    val id: Long?,
    var startTime: LocalDateTime,
    var duration: Int,
    var quality: Int
) {

    /**
     * Fonction compagnon pour convertir un `SleepDto` en objet métier `Sleep`.
     * Convertit le timestamp `startTime` du DTO en `LocalDateTime` dans le fuseau horaire local.
     *
     * @param dto L'instance `SleepDto` à convertir.
     * @return Sleep L'instance convertie de `Sleep`.
     */
    companion object {
        fun fromDto(dto: SleepDto): Sleep {
            return Sleep(
                id = dto.id,
                startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dto.startTime),
                    TimeZone.getDefault().toZoneId()), // Convertit le timestamp en `LocalDateTime` local
                duration = dto.duration,
                quality = dto.quality
            )
        }
    }

    /**
     * Convertit cette instance `Sleep` en un `SleepDto` pour le stockage dans la base de données.
     * La conversion du champ `startTime` vers un timestamp en millisecondes UTC est nécessaire pour
     * le stockage.
     *
     * @return SleepDto L'instance convertie de `SleepDto`.
     * @throws IllegalArgumentException Si l'id est nul lors de la conversion, une exception est
     * levée pour garantir un id valide.
     */
    fun toDto(): SleepDto {
        return SleepDto(
            id = id ?: throw IllegalArgumentException("Sleep Id should not be null"), // Vérifie que l'ID n'est pas nul
            startTime = startTime.toInstant(ZoneOffset.UTC).toEpochMilli(), // Convertit `startTime` en millisecondes UTC
            duration = duration,
            quality = quality
        )
    }
}

