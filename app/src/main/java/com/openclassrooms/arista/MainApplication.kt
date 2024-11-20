package com.openclassrooms.arista

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe Application principale de l'application.
 * Utilisée pour initialiser les bibliothèques et composants nécessaires au démarrage.
 *
 * Annotée avec `@HiltAndroidApp` pour activer l'injection de dépendances via Hilt dans
 * toute l'application. Cette annotation génère automatiquement les composants Hilt,
 * et fait de cette classe le point d'entrée pour Hilt.
 */
@HiltAndroidApp
class MainApplication : Application()
