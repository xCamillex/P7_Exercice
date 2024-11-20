package com.openclassrooms.arista.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.arista.R
import com.openclassrooms.arista.databinding.FragmentExerciseBinding
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime

interface DeleteExerciseInterface {
    fun deleteExercise(exercise: Exercise?)
}
/**
 * Fragment représentant l'écran des exercices dans l'application. Ce fragment affiche une liste des exercices,
 * permet d'ajouter de nouveaux exercices via un formulaire et offre la possibilité de supprimer des exercices.
 * Le fragment est responsable de l'interaction avec le `ViewModel` pour obtenir et manipuler les données des exercices.
 * Il utilise un `RecyclerView` pour afficher la liste et un `FloatingActionButton` pour ajouter de nouveaux exercices.
 *
 * Le fragment implémente l'interface `DeleteExerciseInterface` pour gérer la suppression d'un exercice via un clic sur
 * l'icône de suppression d'un élément de la liste.
 */
@AndroidEntryPoint
class ExerciseFragment : Fragment(), DeleteExerciseInterface {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExerciseViewModel by viewModels()
    private lateinit var exerciseAdapter: ExerciseAdapter

    /**
     * Crée et renvoie la vue du fragment, incluant le binding et la configuration du recyclerview.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Appelée après la création de la vue. Configure le RecyclerView, observe les changements d'exercices et configure
     * le bouton flottant pour ajouter un nouvel exercice.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeExercises()
    }

    /**
     * Configure le RecyclerView avec un adaptateur et un `LinearLayoutManager` pour afficher la liste des exercices.
     */
    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter(this)
        binding.exerciseRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.exerciseRecyclerview.adapter = exerciseAdapter
    }

    /**
     * Observe les changements dans la liste des exercices via le `ViewModel` et soumet les données à l'adaptateur.
     */
    private fun observeExercises() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.exercisesFlow.collect { exercises ->
                exerciseAdapter.submitList(exercises)
            }
        }
    }

    /**
     * Configure le bouton flottant pour afficher un dialog d'ajout d'exercice.
     */
    private fun setupFab() {
        binding.fab.setOnClickListener { showAddExerciseDialog() }
    }

    /**
     * Affiche un dialog permettant à l'utilisateur d'ajouter un nouvel exercice.
     */
    private fun showAddExerciseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_exercise, null)
        setupDialogViews(dialogView).also {
            AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle(R.string.add_new_exercise)
                .setPositiveButton(R.string.add) { _, _ -> addExercise(it) }
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    /**
     * Configure les vues nécessaires pour le formulaire de création d'exercice (durée, catégorie et intensité).
     */
    private fun setupDialogViews(dialogView: View): Triple<EditText, Spinner, EditText> {
        val durationEditText = dialogView.findViewById<EditText>(R.id.durationEditText)
        val categorySpinner = dialogView.findViewById<Spinner>(R.id.categorySpinner).apply {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                ExerciseCategory.values()
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }
        val intensityEditText = dialogView.findViewById<EditText>(R.id.intensityEditText)
        return Triple(durationEditText, categorySpinner, intensityEditText)
    }

    /**
     * Valide les champs de saisie pour s'assurer que la durée et l'intensité sont valides.
     * Si les champs sont invalides, des messages d'erreur sont affichés et l'exercice n'est pas ajouté.
     */
    private fun addExercise(views: Triple<EditText, Spinner, EditText>) {
        val (durationEditText, categorySpinner, intensityEditText) = views

        val durationStr = durationEditText.text.toString().trim()
        val intensityStr = intensityEditText.text.toString().trim()

        val isDurationValid = validateDuration(durationStr)
        val isIntensityValid = validateIntensity(intensityStr)

        if (!isDurationValid || !isIntensityValid) return

        val duration = durationStr.toInt()
        val intensity = intensityStr.toInt()
        val category = categorySpinner.selectedItem as ExerciseCategory

        val newExercise =
            Exercise(System.currentTimeMillis(), LocalDateTime.now(), duration, category, intensity)
        viewModel.addNewExercise(newExercise)
    }

    /**
     * Valide si la durée de l'exercice est bien remplie.
     */
    private fun validateDuration(duration: String): Boolean {
        if (duration.isBlank()) {
            Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * Valide si l'intensité est un nombre valide et compris entre 1 et 10.
     */
    private fun validateIntensity(intensity: String): Boolean {
        if (intensity.isBlank()) {
            Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
            return false
        }

        return try {
            val intensityValue = intensity.toInt()
            if (intensityValue !in 1..10) {
                Toast.makeText(
                    requireContext(),
                    R.string.intensity_should_be_between_1_and_10,
                    Toast.LENGTH_SHORT
                ).show()
                false
            } else {
                true
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(
                requireContext(),
                R.string.invalid_input_please_enter_valid_numbers,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    /**
     * Libère la référence de `_binding` lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Implémente la méthode de l'interface `DeleteExerciseInterface` pour supprimer un exercice.
     */
    override fun deleteExercise(exercise: Exercise?) {
        exercise?.let { viewModel.deleteExercise(it) }
    }
}