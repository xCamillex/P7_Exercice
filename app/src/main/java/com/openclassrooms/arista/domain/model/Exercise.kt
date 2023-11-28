package com.openclassrooms.arista.domain.model

import java.time.LocalDateTime

data class Exercise(
    val id: Long? = null,
    var startTime: LocalDateTime,
    var duration: Int,
    var category: ExerciseCategory,
    var intensity: Int
)