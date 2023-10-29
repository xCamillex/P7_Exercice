package com.openclassrooms.arista.domain.model

import org.threeten.bp.LocalDateTime
import java.util.Objects

data class Exercise(
    val id: Long? = null,
    var startTime: LocalDateTime,
    var duration: Int,
    var category: ExerciseCategory,
    var intensity: Int
)