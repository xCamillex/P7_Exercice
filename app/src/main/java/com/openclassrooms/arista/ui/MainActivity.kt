package com.openclassrooms.arista.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.openclassrooms.arista.R
import com.openclassrooms.arista.databinding.ActivityMainBinding
import com.openclassrooms.arista.ui.exercise.ExerciseFragment
import com.openclassrooms.arista.ui.sleep.SleepFragment
import com.openclassrooms.arista.ui.user.UserDataFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activité principale de l'application, qui gère la navigation entre les différents fragments
 * via une barre de navigation inférieure (`BottomNavigationView`).
 * Utilise Android Jetpack et Hilt pour une architecture moderne et réactive.
 *
 * Fonctionnalités principales :
 * - Initialisation et gestion d'une `BottomNavigationView` pour la navigation utilisateur.
 * - Préserve l'état du menu sélectionné à travers les changements de configuration.
 * - Charge les fragments correspondants à chaque élément de menu.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Liaison avec le fichier de mise en page via View Binding.
    private lateinit var binding: ActivityMainBinding

    // Identifiant du menu actuellement sélectionné.
    private var selectedMenuItemId: Int = R.id.nav_user_data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure le listener pour gérer les clics sur les éléments de la navigation inférieure.
        binding.bottomNavigation.setOnItemSelectedListener(navListener)

        // Si aucune instance sauvegardée n'existe, affiche par défaut le fragment des données utilisateur.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UserDataFragment()).commit()
        } else {
            // Restaure l'état du menu sélectionné après une reconfiguration.
            savedInstanceState.getInt(SELECTED_MENU_ITEM, R.id.nav_user_data)
                .also { selectedMenuItemId = it }
            binding.bottomNavigation.selectedItemId = selectedMenuItemId
        }
    }

    /**
     * Sauvegarde l'état actuel du menu sélectionné pour le restaurer après une reconfiguration.
     *
     * @param outState Conteneur pour sauvegarder l'état de l'activité.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU_ITEM, selectedMenuItemId)
    }

    /**
     * Listener pour gérer la navigation lorsque l'utilisateur clique sur un élément
     * de la `BottomNavigationView`.
     */
    private val navListener = NavigationBarView.OnItemSelectedListener { item ->
        // Obtient le fragment correspondant à l'élément de menu sélectionné.
        getFragmentById(item.itemId)?.let { selectedFragment ->
            selectedMenuItemId = item.itemId
            // Remplace le fragment affiché dans le conteneur.
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).commit()
            true
        } ?: false
    }

    /**
     * Retourne le fragment associé à un identifiant d'élément de menu.
     *
     * @param menuId Identifiant de l'élément de menu.
     * @return Le fragment correspondant, ou `null` si l'identifiant est inconnu.
     */
    private fun getFragmentById(menuId: Int): Fragment? {
        return when (menuId) {
            R.id.nav_user_data -> UserDataFragment()
            R.id.nav_exercise -> ExerciseFragment()
            R.id.nav_sleep -> SleepFragment()
            else -> null
        }
    }

    companion object {
        // Clé pour sauvegarder et restaurer l'identifiant du menu sélectionné.
        private const val SELECTED_MENU_ITEM = "selected_menu_item"
    }
}