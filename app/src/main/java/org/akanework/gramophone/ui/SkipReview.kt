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

import android.widget.Toast
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
import uk.akane.libphonograph.manipulator.ItemManipulator

/**
 * Sillage : propose d'effacer les morceaux dont le score de skip atteint le seuil.
 * Affiché au plus une fois par démarrage de l'application, ou à la demande depuis les réglages.
 */
object SkipReview {
    /** Remis à false par le réglage « Proposer maintenant ». */
    var shownThisSession = false

    fun maybePropose(activity: MainActivity, force: Boolean = false) {
        if (shownThisSession && !force) return
        shownThisSession = true
        activity.lifecycleScope.launch(Dispatchers.Default) {
            val threshold = SkipScores.threshold(activity)
            val scores = SkipScores.all(activity).filterValues { it >= threshold }
            val candidates: List<Pair<MediaItem, Int>> = if (scores.isEmpty()) emptyList() else
                activity.gramophoneApplication.reader.songListFlow.first()
                    .mapNotNull { item -> item.getFile()?.path?.let { p -> scores[p]?.let { item to it } } }
                    .sortedByDescending { it.second }
            withContext(Dispatchers.Main) {
                if (candidates.isEmpty()) {
                    if (force) Toast.makeText(activity, R.string.skip_review_none, Toast.LENGTH_LONG).show()
                    return@withContext
                }
                show(activity, candidates)
            }
        }
    }

    private fun show(activity: MainActivity, candidates: List<Pair<MediaItem, Int>>) {
        val labels = candidates.map { (item, score) ->
            activity.getString(R.string.skip_review_item, item.mediaMetadata.title ?: "",
                item.mediaMetadata.artist ?: "", score)
        }.toTypedArray<CharSequence>()
        val checked = BooleanArray(candidates.size) { true }
        val paths = candidates.mapNotNull { it.first.getFile()?.path }
        MaterialAlertDialogBuilder(activity)
            .setTitle(R.string.skip_review_title)
            .setMultiChoiceItems(labels, checked) { _, which, isChecked -> checked[which] = isChecked }
            .setPositiveButton(R.string.delete) { _, _ ->
                val toDelete = candidates.filterIndexed { i, _ -> checked[i] }.map { it.first }
                // les morceaux décochés sont gardés : leur score repart de zéro
                SkipScores.reset(activity, paths)
                if (toDelete.isEmpty()) return@setPositiveButton
                activity.lifecycleScope.launch(Dispatchers.Default) {
                    // Android 11+ affiche sa propre confirmation et renvoie null ;
                    // avant, la confirmation est celle de cette boîte : on efface directement
                    ItemManipulator.deleteSongs(activity,
                        toDelete.map { it.getFile()!! to it.requireMediaStoreId() })?.invoke()
                }
            }
            .setNegativeButton(R.string.skip_review_keep) { _, _ -> SkipScores.reset(activity, paths) }
            .setNeutralButton(R.string.skip_review_later, null)
            .show()
    }
}
