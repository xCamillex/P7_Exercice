package com.openclassrooms.arista.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.arista.databinding.FragmentUserDataBinding
import com.openclassrooms.arista.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Fragment pour afficher et gérer les données utilisateur.
 * Cette classe utilise un `ViewModel` pour observer et récupérer les données de l'utilisateur
 * et met à jour l'interface utilisateur en conséquence.
 *
 * Fonctionnalités principales :
 * - Utilisation de Hilt pour l'injection de dépendances.
 * - Gestion des données utilisateur avec `StateFlow`.
 * - Mise à jour des champs d'interface utilisateur (nom et email) en fonction des données observées.
 */
@AndroidEntryPoint
class UserDataFragment : Fragment() {

    // Liaison vers le fichier XML de mise en page associé.
    private lateinit var binding: FragmentUserDataBinding

    // Injection du ViewModel avec Hilt.
    private val viewModel: UserDataViewModel by viewModels()

    /**
     * Création de la vue du fragment en utilisant View Binding.
     * @return La racine de la vue du fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configuration de l'observation des données utilisateur lorsque la vue est créée.
     * Récupère les données utilisateur depuis le `ViewModel` et met à jour les champs d'interface utilisateur.
     *
     * @param view La vue racine du fragment.
     * @param savedInstanceState État enregistré précédent (le cas échéant).
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe le flux de données utilisateur et met à jour l'interface utilisateur.
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userFlow.collect { user: User? ->
                user?.let {

                    // Mise à jour des champs de texte avec les données de l'utilisateur.
                    binding.etName.setText(it.name)
                    binding.etEmail.setText(it.email)
                }
            }
        }
    }
}
