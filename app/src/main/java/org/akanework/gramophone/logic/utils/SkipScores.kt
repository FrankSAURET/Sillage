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

package org.akanework.gramophone.logic.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

/**
 * Sillage : score de skip par morceau, rangé par chemin de fichier.
 * +1 quand le morceau est passé dans sa première minute, −1 quand il est écouté jusqu'au bout.
 * Un morceau dont le score atteint le seuil réglable est proposé à l'effacement.
 */
object SkipScores {
    private const val PREFS_NAME = "skip_scores"
    const val KEY_THRESHOLD = "skip_threshold"
    const val DEFAULT_THRESHOLD = 3
    const val SKIP_WINDOW_MS = 60_000L

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun add(context: Context, path: String, delta: Int) {
        val p = prefs(context)
        val value = p.getInt(path, 0) + delta
        // un score nul n'apporte rien : on n'encombre pas le fichier
        p.edit { if (value == 0) remove(path) else putInt(path, value) }
    }

    fun reset(context: Context, paths: Collection<String>) {
        prefs(context).edit { paths.forEach { remove(it) } }
    }

    fun all(context: Context): Map<String, Int> =
        prefs(context).all.mapNotNull { (k, v) -> (v as? Int)?.let { k to it } }.toMap()

    fun threshold(context: Context): Int =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
            .getInt(KEY_THRESHOLD, DEFAULT_THRESHOLD)
}
