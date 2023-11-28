package com.openclassrooms.arista.domain.model

import java.time.LocalDateTime

data class Sleep(@JvmField var startTime: LocalDateTime, var duration: Int, var quality: Int)
