package com.openclassrooms.arista.ui.sleep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.arista.databinding.FragmentSleepBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment pour afficher et gérer les données de sommeil dans l'application.
 * Ce fragment utilise un `RecyclerView` pour afficher les sessions de sommeil et observe un
 * ViewModel pour les mises à jour des données.
 */
@AndroidEntryPoint
class SleepFragment : Fragment() {

    // Liaison avec la vue générée par ViewBinding pour accéder aux éléments de l'interface utilisateur.
    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!

    // Initialisation du ViewModel injecté avec Hilt pour gérer les données de sommeil.
    private val viewModel: SleepViewModel by viewModels()

    // Adaptateur pour le RecyclerView, initialisé avec une liste vide.
    private val sleepAdapter = SleepAdapter(emptyList())

    /**
     * Crée la vue associée au fragment en utilisant ViewBinding.
     *
     * @param inflater Inflater utilisé pour gonfler la vue.
     * @param container Vue parente dans laquelle le fragment est placé.
     * @param savedInstanceState État sauvegardé du fragment, s'il existe.
     * @return La vue racine gonflée du fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSleepBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Appelée après la création de la vue pour configurer le RecyclerView, les observateurs et
     * déclencher le chargement des données.
     *
     * @param view Vue racine associée au fragment.
     * @param savedInstanceState État sauvegardé du fragment, s'il existe.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure les observateurs pour les données du ViewModel.
        setupObservers()

        // Configure le RecyclerView pour afficher les données de sommeil.
        binding.sleepRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.sleepRecyclerview.adapter = sleepAdapter

        // Déclenche le chargement des données depuis le ViewModel.
        viewModel.fetchSleeps()
    }

    /**
     * Configure un collecteur Flow pour observer les données des sessions de sommeil du ViewModel.
     * Met à jour l'adaptateur lorsque de nouvelles données sont disponibles.
     */
    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sleeps.collect { sleeps ->
                sleepAdapter.updateData(sleeps)
            }
        }
    }

    /**
     * Libère les ressources liées à ViewBinding pour éviter les fuites de mémoire.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
