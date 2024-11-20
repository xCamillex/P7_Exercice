package com.openclassrooms.arista.ui.sleep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.arista.R
import com.openclassrooms.arista.domain.model.Sleep
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

/**
 * Adaptateur pour afficher une liste de données de type `Sleep` dans un RecyclerView.
 * Cette classe gère l'affichage des informations sur les sessions de sommeil (heure de début, durée, qualité).
 *
 * @property sleeps Liste des sessions de sommeil à afficher dans le RecyclerView.
 */
class SleepAdapter(private var sleeps: List<Sleep>) :
    RecyclerView.Adapter<SleepAdapter.SleepViewHolder>() {

    /**
     * Appelée pour créer un nouveau ViewHolder lorsque le RecyclerView en a besoin.
     * Cette méthode gonfle le layout XML `item_sleep` pour chaque élément.
     *
     * @param parent Vue parente dans laquelle le ViewHolder sera ajouté.
     * @param viewType Type de vue (pas utilisé ici, mais nécessaire pour l'override).
     * @return Une instance de `SleepViewHolder` contenant les vues nécessaires.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sleep, parent, false)
        return SleepViewHolder(itemView)
    }

    /**
     * Appelée pour lier les données à une vue spécifique (ViewHolder) dans le RecyclerView.
     * Met à jour les vues du ViewHolder avec les données d'un élément `Sleep`.
     *
     * @param holder Le ViewHolder qui doit être mis à jour.
     * @param position Position de l'élément dans la liste des données.
     */
    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val sleep = sleeps[position]
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        holder.tvStartTime.text = "Start Time: ${sleep.startTime.format(formatter)}"
        holder.tvDuration.text = "Duration: ${sleep.duration} minutes"
        holder.tvQuality.text = "Quality: ${sleep.quality}"
    }

    /**
     * Retourne le nombre total d'éléments dans la liste.
     *
     * @return Nombre d'éléments dans la liste des sessions de sommeil.
     */
    override fun getItemCount() = sleeps.size

    /**
     * ViewHolder interne utilisé pour stocker les vues nécessaires pour afficher un élément `Sleep`.
     *
     * @param itemView Vue racine de l'élément affiché dans le RecyclerView.
     */
    inner class SleepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvStartTime: TextView
        var tvDuration: TextView
        var tvQuality: TextView

        init {
            tvStartTime = itemView.findViewById(R.id.tv_start_time)
            tvDuration = itemView.findViewById(R.id.tv_duration)
            tvQuality = itemView.findViewById(R.id.tv_quality)
        }
    }

    /**
     * Met à jour les données affichées par l'adaptateur avec une nouvelle liste de sessions de sommeil.
     * Appelle `notifyDataSetChanged` pour rafraîchir l'affichage.
     *
     * @param newSleeps Nouvelle liste de données à afficher dans le RecyclerView.
     */
    fun updateData(newSleeps: List<Sleep>) {
        this.sleeps = newSleeps
        notifyDataSetChanged()
    }
}
