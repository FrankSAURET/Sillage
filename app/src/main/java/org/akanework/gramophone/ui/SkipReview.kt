/*
 *     Copyright (C) 2026 Frank SAURET
 *
 *     Sillage is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Sillage is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.akanework.gramophone.ui

import android.content.Context
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.akanework.gramophone.R
import org.akanework.gramophone.logic.gramophoneApplication
import org.akanework.gramophone.logic.getFile
import org.akanework.gramophone.logic.requireMediaStoreId
import org.akanework.gramophone.logic.utils.SkipScores
import uk.akane.libphonograph.dynamicitem.Favorite
import uk.akane.libphonograph.manipulator.ItemManipulator

/** Un morceau qui a un score de skip non nul. [item] est null si le fichier n'est plus dans la bibliothèque. */
data class ScoredSong(val item: MediaItem?, val path: String, val score: Int, val favorite: Boolean)

/**
 * Sillage : propose d'effacer les morceaux dont le score de skip atteint le seuil
 * (ou les efface directement si « Effacer automatiquement » est actif).
 * Affiché au plus une fois par démarrage de l'application, ou à la demande depuis les réglages.
 */
object SkipReview {
    /** Remis à false par les boutons « Effacer maintenant » des réglages. */
    var shownThisSession = false

    /** Tous les morceaux de score non nul, du plus haut au plus bas. */
    suspend fun load(context: Context): List<ScoredSong> = withContext(Dispatchers.Default) {
        val scores = SkipScores.all(context)
        if (scores.isEmpty()) return@withContext emptyList()
        val reader = context.gramophoneApplication.reader
        val byPath = reader.songListFlow.first()
            .mapNotNull { item -> item.getFile()?.path?.let { it to item } }.toMap()
        val favorites = reader.playlistListFlow.first().find { it is Favorite }?.songList
            ?.mapNotNull { it.getFile()?.path }?.toSet().orEmpty()
        scores.map { (path, score) -> ScoredSong(byPath[path], path, score, path in favorites) }
            .sortedByDescending { it.score }
    }

    /** Morceaux à proposer à l'effacement : seuil atteint, présents dans la bibliothèque, jamais un favori. */
    fun toDelete(list: List<ScoredSong>, threshold: Int) =
        list.filter { it.score >= threshold && !it.favorite && it.item != null }

    fun positive(list: List<ScoredSong>) = list.filter { it.score > 0 }

    fun maybePropose(activity: MainActivity, force: Boolean = false) {
        if (shownThisSession && !force) return
        shownThisSession = true
        activity.lifecycleScope.launch {
            val candidates = toDelete(load(activity), SkipScores.threshold(activity))
            if (candidates.isEmpty()) return@launch
            if (SkipScores.autoDelete(activity)) delete(activity, candidates)
            else show(activity, candidates)
        }
    }

    /** Efface les morceaux (confirmation d'Android 11+ comprise) et remet leur score à zéro. */
    private fun delete(activity: MainActivity, songs: List<ScoredSong>) {
        SkipScores.reset(activity, songs.map { it.path })
        activity.lifecycleScope.launch(Dispatchers.Default) {
            // Android 11+ affiche sa propre confirmation et renvoie null ;
            // avant, la confirmation est celle de la boîte de dialogue : on efface directement
            ItemManipulator.deleteSongs(activity,
                songs.map { it.item!!.getFile()!! to it.item.requireMediaStoreId() })?.invoke()
        }
    }

    private fun show(activity: MainActivity, candidates: List<ScoredSong>) {
        val labels = candidates.map {
            activity.getString(R.string.skip_review_item, it.item!!.mediaMetadata.title ?: "",
                it.item.mediaMetadata.artist ?: "", it.score)
        }.toTypedArray<CharSequence>()
        val checked = BooleanArray(candidates.size) { true }
        MaterialAlertDialogBuilder(activity)
            .setTitle(R.string.skip_review_title)
            .setMultiChoiceItems(labels, checked) { _, which, isChecked -> checked[which] = isChecked }
            .setPositiveButton(R.string.delete) { _, _ ->
                val toDelete = candidates.filterIndexed { i, _ -> checked[i] }
                // les morceaux décochés sont gardés : leur score repart de zéro
                SkipScores.reset(activity, candidates.map { it.path })
                if (toDelete.isNotEmpty()) delete(activity, toDelete)
            }
            .setNegativeButton(R.string.skip_review_keep) { _, _ ->
                SkipScores.reset(activity, candidates.map { it.path })
            }
            .setNeutralButton(R.string.skip_review_later, null)
            .show()
    }
}
