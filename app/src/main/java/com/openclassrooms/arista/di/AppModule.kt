package com.openclassrooms.arista.di

import android.content.Context
import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.database.AppDatabase
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * Module Dagger Hilt pour fournir les dépendances de l'application, y compris les instances de base
 * de données, les DAO, les repositories, et une portée de coroutine partagée pour gérer les tâches
 * de manière supervisée.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Fournit une instance de CoroutineScope avec un SupervisorJob et le Dispatchers.Main.
     * Ce scope peut être injecté pour gérer les coroutines de manière supervisée dans les repositories
     * ou d'autres composants de l'application.
     *
     * @return CoroutineScope Le scope de coroutine fourni pour l'application.
     */
    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    /**
     * Fournit une instance unique de la base de données `AppDatabase`.
     * Cette instance est partagée dans toute l'application pour assurer l'accès aux données persistantes via les DAO.
     *
     * @param context Le contexte de l'application requis pour l'initialisation de la base de données.
     * @param coroutineScope Le CoroutineScope utilisé pour gérer les opérations de la base de données.
     * @return AppDatabase L'instance de la base de données.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context, coroutineScope: CoroutineScope): AppDatabase {
        return AppDatabase.getDatabase(context, coroutineScope)
    }

    /**
     * Fournit une instance de `UserDtoDao` pour accéder aux opérations liées aux utilisateurs dans la base de données.
     *
     * @param appDatabase L'instance de la base de données `AppDatabase`.
     * @return UserDtoDao Le DAO pour les opérations utilisateur.
     */
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDtoDao {
        return appDatabase.userDtoDao()
    }

    /**
     * Fournit une instance de `SleepDtoDao` pour accéder aux opérations liées aux enregistrements
     * de sommeil dans la base de données.
     *
     * @param appDatabase L'instance de la base de données `AppDatabase`.
     * @return SleepDtoDao Le DAO pour les opérations de sommeil.
     */
    @Provides
    fun provideSleepDao(appDatabase: AppDatabase): SleepDtoDao {
        return appDatabase.sleepDtoDao()
    }

    /**
     * Fournit une instance de `ExerciseDtoDao` pour accéder aux opérations liées aux exercices
     * dans la base de données.
     *
     * @param appDatabase L'instance de la base de données `AppDatabase`.
     * @return ExerciseDtoDao Le DAO pour les opérations d'exercice.
     */
    @Provides
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDtoDao {
        return appDatabase.exerciseDtoDao()
    }

    /**
     * Fournit une instance unique de `UserRepository` pour gérer les opérations liées aux
     * utilisateurs dans l'application.
     * Ce repository dépend du DAO `UserDtoDao` pour exécuter les requêtes de la base de données.
     *
     * @param userDao Le DAO pour les opérations utilisateur.
     * @return UserRepository Le repository pour la gestion des utilisateurs.
     */
    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDtoDao): UserRepository {
        return UserRepository(userDao)
    }

    /**
     * Fournit une instance unique de `SleepRepository` pour gérer les opérations liées au sommeil
     * dans l'application.
     * Ce repository dépend du DAO `SleepDtoDao` pour exécuter les requêtes de la base de données.
     *
     * @param sleepDtoDao Le DAO pour les opérations de sommeil.
     * @return SleepRepository Le repository pour la gestion des enregistrements de sommeil.
     */
    @Provides
    @Singleton
    fun provideSleepRepository(sleepDtoDao: SleepDtoDao): SleepRepository {
        return SleepRepository(sleepDtoDao)
    }

    /**
     * Fournit une instance unique de `ExerciseRepository` pour gérer les opérations liées aux
     * exercices dans l'application.
     * Ce repository dépend du DAO `ExerciseDtoDao` pour exécuter les requêtes de la base de données.
     *
     * @param exerciseDtoDao Le DAO pour les opérations d'exercice.
     * @return ExerciseRepository Le repository pour la gestion des exercices.
     */
    @Provides
    @Singleton
    fun provideExerciseRepository(exerciseDtoDao: ExerciseDtoDao): ExerciseRepository {
        return ExerciseRepository(exerciseDtoDao)
    }
}