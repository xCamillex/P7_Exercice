package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.TimeZone


data class Sleep(
    val id: Long?,
    var startTime: LocalDateTime,
    var duration: Int,
    var quality: Int
) {
    companion object {
        fun fromDto(dto: SleepDto): Sleep {
            return Sleep(
                id = dto.id,
                startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dto.startTime),
                    TimeZone.getDefault().toZoneId()),
                duration = dto.duration,
                quality = dto.quality
            )
        }
    }
    fun toDto(): SleepDto {
        return SleepDto(
            id = id ?: throw IllegalArgumentException("Sleep Id should not be null"),
            startTime = startTime.toInstant(ZoneOffset.UTC).toEpochMilli(),
            duration = duration,
            quality = quality
        )
    }
}

