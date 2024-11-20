package com.openclassrooms.arista.ui.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.arista.R
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.ui.exercise.ExerciseAdapter.ExerciseViewHolder
import java.time.format.DateTimeFormatter

/**
 * Adaptateur pour afficher une liste d'exercices dans un `RecyclerView`. Cette classe utilise un `ListAdapter` pour
 * afficher des objets `Exercise`, en permettant de gérer efficacement les mises à jour de la liste avec un callback `DiffUtil`.
 * Elle inclut également un mécanisme pour supprimer un exercice via l'interface `DeleteExerciseInterface`.
 *
 * @param context L'interface `DeleteExerciseInterface` utilisée pour interagir avec les actions de suppression d'exercice.
 */
class ExerciseAdapter(private val context: DeleteExerciseInterface) :
    ListAdapter<Exercise, ExerciseViewHolder>(
        DIFF_CALLBACK
    ) {

    /**
     * Crée une nouvelle vue pour un élément de la liste d'exercices.
     *
     * @param parent Le groupe parent où l'élément de la liste sera inséré.
     * @param viewType Le type de vue pour l'élément (non utilisé ici, mais nécessaire pour
     * l'implémentation de `onCreateViewHolder`).
     * @return Un nouveau `ExerciseViewHolder` contenant la vue de l'élément.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    /**
     * Lie les données d'un exercice à la vue correspondante.
     *
     * @param holder Le `ViewHolder` qui contient les vues de l'élément.
     * @param position La position de l'exercice dans la liste.
     */
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        holder.tvStartTime.text =
            String.format("Start Time: %s", exercise!!.startTime.format(formatter))
        holder.tvDuration.text = String.format("Duration: %d minutes", exercise.duration)
        holder.tvCategory.text = String.format("Category: %s", exercise.category.toString())
        holder.tvIntensity.text = String.format("Intensity: %d", exercise.intensity)
        holder.ivDelete.setOnClickListener { _: View? -> context.deleteExercise(exercise) }
    }

    /**
     * ViewHolder qui contient les vues pour un élément d'exercice.
     */
    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvStartTime: TextView
        var tvDuration: TextView
        var tvCategory: TextView
        var tvIntensity: TextView
        var ivDelete: ImageView

        init {
            tvStartTime = itemView.findViewById(R.id.tv_start_time)
            tvDuration = itemView.findViewById(R.id.tv_duration)
            tvCategory = itemView.findViewById(R.id.tv_category)
            tvIntensity = itemView.findViewById(R.id.tv_intensity)
            ivDelete = itemView.findViewById(R.id.delete)
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Exercise> =
            object : DiffUtil.ItemCallback<Exercise>() {
                /**
                 * Vérifie si deux éléments d'exercice sont identiques.
                 */
                override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                    return oldItem === newItem
                }

                /**
                 * Vérifie si le contenu de deux exercices est le même.
                 */
                override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                    return oldItem == newItem
                }
            }
    }
}