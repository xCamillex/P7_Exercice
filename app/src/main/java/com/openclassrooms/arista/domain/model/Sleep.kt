package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto

data class Sleep(
    val id: Long?,
    var startTime: Long,
    var duration: Int,
    var quality: Int
) {
    fun toDto(): SleepDto {
        return SleepDto(
            id = id ?: throw IllegalArgumentException("Sleep Id should not be null"),
            startTime = startTime,
            duration = duration,
            quality = quality
        )
    }

    companion object {
        fun fromDto(dto: SleepDto): Sleep {
            return Sleep(
                id = dto.id,
                startTime = dto.startTime,
                duration = dto.duration,
                quality = dto.quality
            )
        }
    }
}

