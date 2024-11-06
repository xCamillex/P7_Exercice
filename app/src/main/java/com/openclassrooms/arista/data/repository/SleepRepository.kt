package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.first

class SleepRepository(private val sleepDao: SleepDtoDao) {

    // Get all sleep records
    suspend fun getAllSleeps(): List<Sleep> {
        return sleepDao.getAllSleeps()
            .first() // Collect the first emission of the Flow
            .map { Sleep.fromDto(it) } // Convert every DTO in Sleep
    }


    // Add a new sleep records
    suspend fun addSleep(sleep: Sleep) {
        sleepDao.insertSleep(sleep.toDto()) // Convert every Sleep in DTO
    }


    // Delete a sleep records
    suspend fun deleteSleep(sleep: Sleep) {
        // If there is no id, you can raise an exception and catch it in the use case and viewmodel
        sleep.id?.let {
            sleepDao.deleteSleepById(
                id = sleep.id,
            )
        }
    }
}