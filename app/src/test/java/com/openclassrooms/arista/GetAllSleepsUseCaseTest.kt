package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

/**
 * Classe de test pour le cas d'utilisation `GetAllSleepsUseCase`.
 * Ce test utilise JUnit4 et Mockito pour valider que le cas d'utilisation
 * récupère correctement les données de sommeil depuis le référentiel (`SleepRepository`).
 */
@RunWith(JUnit4::class)
class GetAllSleepsUseCaseTest {
    @Mock
    private lateinit var sleepRepository: SleepRepository // Mock du référentiel de sommeil
    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase // Cas d'utilisation testé

    /**
     * Configuration initiale avant chaque test.
     * Initialise les mocks avec Mockito et le cas d'utilisation avec les dépendances nécessaires.
     */
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this) // Initialise les annotations Mockito
        getAllSleepsUseCase = GetAllSleepsUseCase(sleepRepository)
    }

    /**
     * Nettoyage après chaque test.
     * Supprime les mocks inlines pour éviter toute interférence entre les tests.
     */
    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    /**
     * Test le comportement du cas d'utilisation `GetAllSleepsUseCase`.
     * Vérifie que le cas d'utilisation retourne les données de sommeil récupérées du référentiel.
     */
    @Test
    fun `when repository returns sleeps, use case should return them`() = runBlocking {
        // Arrange
        // Définition de deux entrées de sommeil fictives dans une liste
        val fakeSleeps = listOf(
            Sleep(
                id = 1,
                startTime = LocalDateTime.now().minusDays(1),
                duration = 540, // Durée en minutes (540 min = 9 heures)
                quality = 6 // Qualité de sommeil sur une échelle de 1 à 10
            ),
            Sleep(
                id = 2,
                startTime = LocalDateTime.now().minusDays(2),
                duration = 600, // Durée en minutes (600 min = 10 heures)
                quality = 3 // Qualité de sommeil sur une échelle de 1 à 10
            )
        )
        // Simule le comportement du référentiel pour retourner la liste d'entrées de sommeil
        Mockito.`when`(sleepRepository.getAllSleeps()).thenReturn(fakeSleeps)
        // Act
        // Appel du cas d'utilisation pour récupérer les données de sommeil
        val result = getAllSleepsUseCase.execute()
        // Assert
        // Vérifie que la liste retournée par le cas d'utilisation correspond à celle définie
        assertEquals(fakeSleeps, result)
    }

    /**
     * Test le comportement du cas d'utilisation `GetAllSleepsUseCase` lorsque le référentiel retourne une liste vide.
     * Vérifie que le cas d'utilisation retourne également une liste vide.
     */
    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange
        // Simule le comportement du référentiel pour retourner une liste vide
        Mockito.`when`(sleepRepository.getAllSleeps()).thenReturn(emptyList())
        // Act
        // Appel du cas d'utilisation pour récupérer les données de sommeil
        val result = getAllSleepsUseCase.execute()
        // Assert
        // Vérifie que la liste retournée est vide
        assertTrue(result.isEmpty())
    }
}