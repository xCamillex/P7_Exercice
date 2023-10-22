package com.openclassrooms.arista.domain.model

import org.threeten.bp.LocalDateTime

data class Sleep(@JvmField var startTime: LocalDateTime, var duration: Int, var quality: Int)