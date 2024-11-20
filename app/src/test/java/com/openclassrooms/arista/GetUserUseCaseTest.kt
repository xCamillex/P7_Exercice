package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Classe de test pour le cas d'utilisation `GetUserUsecase`.
 * Ce test utilise JUnit4 et Mockito pour valider que le cas d'utilisation
 * récupère correctement les données utilisateur depuis le référentiel (`UserRepository`).
 */
@RunWith(JUnit4::class)
class GetUserUseCaseTest {
    @Mock
    private lateinit var userRepository: UserRepository // Mock du référentiel utilisateur
    private lateinit var getUserUseCase: GetUserUsecase // Cas d'utilisation testé

    /**
     * Configuration initiale avant chaque test.
     * Initialise les mocks avec Mockito et le cas d'utilisation avec les dépendances nécessaires.
     */
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initialise les annotations Mockito
        getUserUseCase = GetUserUsecase(userRepository)
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
     * Test le comportement du cas d'utilisation `GetUserUsecase`.
     * Vérifie que le cas d'utilisation retourne l'utilisateur récupéré du référentiel.
     */
    @Test
    fun `when repository returns user, use case should return them`() = runBlocking {
        // Arrange
        // Définition d'un utilisateur fictif à retourner par le référentiel
        val fakeUser = User(
            id = 1,
            name = "John Doe",
            email = "johndoe@test.fr",
            password = "1234" // Exemple d'utilisateur avec des informations basiques
        )
        // Simule le comportement du référentiel pour retourner cet utilisateur
        Mockito.`when`(userRepository.getUser()).thenReturn(fakeUser)
        // Act
        // Appel du cas d'utilisation pour récupérer l'utilisateur
        val result = getUserUseCase.execute()
        // Assert
        // Vérifie que l'utilisateur retourné par le cas d'utilisation est le même que l'utilisateur simulé
        assertEquals(fakeUser, result)
    }

    /**
     * Test le comportement du cas d'utilisation `GetUserUsecase` lorsque le référentiel ne retourne aucun utilisateur.
     * Vérifie que le cas d'utilisation retourne `null` dans ce cas.
     */
    @Test
    fun `when repository returns no user, use case should return null`() = runBlocking {
        // Arrange
        // Simule le comportement du référentiel pour retourner `null` (aucun utilisateur)
        Mockito.`when`(userRepository.getUser()).thenReturn(null)
        // Act
        // Appel du cas d'utilisation pour récupérer l'utilisateur
        val result = getUserUseCase.execute()
        // Assert
        // Vérifie que le résultat retourné est `null`
        assertNull(result)
    }
}