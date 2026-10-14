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

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import org.akanework.gramophone.R
import org.akanework.gramophone.logic.hasNotificationPermission
import org.akanework.gramophone.logic.supportsNotificationPermission
import org.akanework.gramophone.ui.SkipReview
import org.akanework.gramophone.ui.fragments.settings.SkipListSettingsActivity

/**
 * Sillage : notification chaque fois que la liste des morceaux à effacer gagne 10 morceaux
 * (10, 20, 30…). Jamais envoyée pendant qu'Android Auto est connecté : elle attend alors la
 * prochaine vérification hors voiture.
 */
object SkipNotifier {
    private const val CHANNEL_ID = "skipReview"
    private const val NOTIFY_ID = 2
    private const val PENDING_INTENT_ID = 2
    private const val KEY_MILESTONE = "skip_notified_milestone"
    const val STEP = 10

    suspend fun check(context: Context, inCar: Boolean) {
        if (inCar) return
        val app = context.applicationContext
        val count = SkipReview.toDelete(SkipReview.load(app), SkipScores.threshold(app)).size
        val milestone = count / STEP * STEP
        val prefs = PreferenceManager.getDefaultSharedPreferences(app)
        val last = prefs.getInt(KEY_MILESTONE, 0)
        if (milestone == last) return
        // le palier redescend quand on efface ou remet à 0 : la notification peut alors revenir
        prefs.edit { putInt(KEY_MILESTONE, milestone) }
        // avec « Effacer automatiquement », la liste se vide d'elle-même : rien à signaler
        if (milestone > last && !SkipScores.autoDelete(app)) notify(app, count)
    }

    @SuppressLint("MissingPermission") // vérifiée par hasNotificationPermission
    private fun notify(context: Context, count: Int) {
        if (supportsNotificationPermission() && !context.hasNotificationPermission()) return
        val nm = NotificationManagerCompat.from(context)
        nm.createNotificationChannel(
            NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
                .setName(context.getString(R.string.skip_notification_channel))
                .build()
        )
        nm.notify(NOTIFY_ID, NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_gramophone_monochrome)
            setContentTitle(context.getString(R.string.skip_notification_title))
            setContentText(context.getString(R.string.skip_notification_text, count))
            setAutoCancel(true)
            setOnlyAlertOnce(true)
            setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            setContentIntent(PendingIntent.getActivity(
                context, PENDING_INTENT_ID,
                SkipListSettingsActivity.intent(context, SkipListSettingsActivity.TAB_TO_DELETE),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT))
        }.build())
    }
}
