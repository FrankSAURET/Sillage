/*
 *     Copyright (C) 2024 Akane Foundation
 *
 *     Gramophone is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gramophone is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.akanework.gramophone.ui.fragments.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.akanework.gramophone.R
import org.akanework.gramophone.logic.utils.SkipScores
import org.akanework.gramophone.ui.SkipReview
import org.akanework.gramophone.ui.fragments.BasePreferenceFragment
import org.akanework.gramophone.ui.fragments.BaseSettingsActivity


/** Sillage : réglages de l'effacement auto, à la racine des paramètres. */
class SkipSettingsActivity : BaseSettingsActivity(
    R.string.settings_category_skip,
    { SkipSettingsFragment() })

class SkipSettingsFragment : BasePreferenceFragment() {

    override fun onResume() {
        super.onResume()
        refreshSkipCounts()
    }

    /** Nombre de morceaux dans chacune des deux listes de l'effacement auto. */
    private fun refreshSkipCounts() {
        viewLifecycleOwner.lifecycleScope.launch {
            val all = SkipReview.load(requireContext())
            findPreference<Preference>("skip_list_delete")?.summary = getString(
                R.string.settings_skip_list_delete_summary,
                SkipReview.toDelete(all, SkipScores.threshold(requireContext())).size)
            findPreference<Preference>("skip_list_positive")?.summary = getString(
                R.string.settings_skip_list_positive_summary, SkipReview.positive(all).size)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_skip, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            "skip_list_delete" -> startActivity(SkipListSettingsActivity.intent(
                requireContext(), SkipListSettingsActivity.TAB_TO_DELETE))
            "skip_list_positive" -> startActivity(SkipListSettingsActivity.intent(
                requireContext(), SkipListSettingsActivity.TAB_POSITIVE))
            "skip_reset_all" -> MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.settings_skip_reset_all)
                .setMessage(R.string.settings_skip_reset_all_confirm)
                .setPositiveButton(R.string.settings_skip_reset_all) { _, _ ->
                    SkipScores.resetAll(requireContext())
                    refreshSkipCounts()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
        return super.onPreferenceTreeClick(preference)
    }
}
