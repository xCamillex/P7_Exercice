package com.openclassrooms.arista.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Classe AppDatabase représentant la base de données Room pour l'application.
 * Elle inclut les tables "User", "Sleep" et "Exercise".
 * Fournit des méthodes DAO pour accéder aux différentes tables et une fonction pour initialiser la
 * base avec des données par défaut.
 */
@Database(entities = [UserDto::class, SleepDto::class, ExerciseDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Méthodes abstraites pour accéder aux DAO de chaque entité
    abstract fun userDtoDao(): UserDtoDao
    abstract fun sleepDtoDao(): SleepDtoDao
    abstract fun exerciseDtoDao(): ExerciseDtoDao

    /**
     * Callback pour initialiser la base de données avec des données par défaut.
     * Appelée lors de la création de la base de données.
     *
     * @param scope CoroutineScope permettant d'exécuter l'initialisation en tâche de fond.
     */
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    // Remplir la base de données avec des données de test
                    populateDatabase(database.sleepDtoDao(), database.userDtoDao())
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Retourne l'instance unique de la base de données. Crée l'instance si elle n'existe pas encore.
         *
         * @param context Contexte de l'application pour l'accès à la base de données.
         * @param coroutineScope CoroutineScope pour la gestion de tâches en arrière-plan.
         */
        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope)) // Ajoute le callback pour remplir la base avec des données de test
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Remplit la base de données avec des données par défaut (données de sommeil et utilisateur).
         *
         * @param sleepDao DAO pour accéder aux données de sommeil.
         * @param userDtoDao DAO pour accéder aux données d'utilisateur.
         */
        suspend fun populateDatabase(sleepDao: SleepDtoDao, userDtoDao: UserDtoDao) {

            // Insertion des enregistrements de sommeil
            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 480, quality = 4
                )
            )
            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 450, quality = 3
                )
            )

            // Insertion d'un enregistrement utilisateur par défaut
            userDtoDao.insertUser(
                UserDto(
                    name = "Jean",
                    email = "jean@free.fr",
                    password = "password"
                )
            )
        }
    }
}