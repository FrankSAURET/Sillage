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

package org.akanework.gramophone.ui.fragments.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.akanework.gramophone.R
import org.akanework.gramophone.logic.enableEdgeToEdgePaddingListener
import org.akanework.gramophone.logic.ui.BaseActivity
import org.akanework.gramophone.logic.utils.SkipScores
import org.akanework.gramophone.ui.MainActivity
import org.akanework.gramophone.ui.ScoredSong
import org.akanework.gramophone.ui.SkipReview
import java.io.File

/** Sillage : les deux listes de l'effacement auto (morceaux à effacer, morceaux à score positif). */
class SkipListSettingsActivity : BaseActivity() {

    companion object {
        const val EXTRA_TAB = "tab"
        const val TAB_TO_DELETE = 0
        const val TAB_POSITIVE = 1

        fun intent(context: Context, tab: Int) =
            Intent(context, SkipListSettingsActivity::class.java).putExtra(EXTRA_TAB, tab)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_blacklist_settings)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.fragment_viewpager)

        findViewById<AppBarLayout>(R.id.appbarlayout).enableEdgeToEdgePaddingListener()
        findViewById<CollapsingToolbarLayout>(R.id.collapsingtoolbar).title =
            getString(R.string.settings_category_skip)
        topAppBar.setNavigationOnClickListener { finish() }

        viewPager2.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun createFragment(position: Int): Fragment =
                SkipListFragment().apply {
                    arguments = Bundle().apply { putInt(EXTRA_TAB, position) }
                }

            override fun getItemCount() = 2
        }
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(if (position == TAB_POSITIVE) R.string.skip_list_positive
                else R.string.skip_list_to_delete)
            tab.view.post {
                try {
                    val lp = tab.view.layoutParams as ViewGroup.MarginLayoutParams
                    lp.marginStart = if (position == 0)
                        resources.getDimension(R.dimen.tab_layout_content_padding).toInt() else 0
                    lp.marginEnd = if (position == tabLayout.tabCount - 1)
                        resources.getDimension(R.dimen.tab_layout_content_padding).toInt() else 0
                    tab.view.layoutParams = lp
                } catch (_: IllegalStateException) {
                }
            }
        }.attach()
        viewPager2.setCurrentItem(intent.getIntExtra(EXTRA_TAB, TAB_TO_DELETE), false)
    }

    /** Une remise à zéro dans une liste change aussi l'autre. */
    fun reloadAll() {
        supportFragmentManager.fragments.filterIsInstance<SkipListFragment>().forEach { it.reload() }
    }
}

class SkipListFragment : Fragment() {
    private var adapter: SkipAdapter? = null
    private val isToDelete get() = requireArguments().getInt(SkipListSettingsActivity.EXTRA_TAB) ==
            SkipListSettingsActivity.TAB_TO_DELETE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_blacklist_settings, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.enableEdgeToEdgePaddingListener()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SkipAdapter().also { recyclerView.adapter = it }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

    fun reload() {
        if (!isAdded || view == null) return
        viewLifecycleOwner.lifecycleScope.launch {
            val all = SkipReview.load(requireContext())
            val list = if (isToDelete)
                SkipReview.toDelete(all, SkipScores.threshold(requireContext()))
            else SkipReview.positive(all)
            adapter?.submit(list)
        }
    }

    private fun resetAndRefresh(paths: List<String>) {
        SkipScores.reset(requireContext(), paths)
        (activity as? SkipListSettingsActivity)?.reloadAll()
    }

    private inner class SkipAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var list: List<ScoredSong> = emptyList()

        fun submit(newList: List<ScoredSong>) {
            list = newList
            @Suppress("NotifyDataSetChanged")
            notifyDataSetChanged()
        }

        override fun getItemCount() = list.size + 1
        override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return object : RecyclerView.ViewHolder(inflater.inflate(
                if (viewType == 0) R.layout.adapter_skip_header else R.layout.adapter_skip_score,
                parent, false)) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val v = holder.itemView
            if (position == 0) {
                v.findViewById<TextView>(R.id.empty).visibility =
                    if (list.isEmpty()) View.VISIBLE else View.GONE
                v.findViewById<MaterialButton>(R.id.reset_all).apply {
                    isEnabled = list.isNotEmpty()
                    setOnClickListener {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(R.string.skip_list_reset_all)
                            .setMessage(R.string.skip_list_reset_all_confirm)
                            .setPositiveButton(R.string.skip_list_reset_all) { _, _ ->
                                resetAndRefresh(list.map { it.path })
                            }
                            .setNegativeButton(android.R.string.cancel, null)
                            .show()
                    }
                }
                v.findViewById<MaterialButton>(R.id.delete_now).apply {
                    visibility = if (isToDelete) View.VISIBLE else View.GONE
                    isEnabled = list.isNotEmpty()
                    setOnClickListener {
                        // retour à l'écran principal, qui efface (avec la confirmation d'Android)
                        SkipReview.shownThisSession = false
                        startActivity(Intent(requireContext(), MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    }
                }
                return
            }
            val song = list[position - 1]
            val meta = song.item?.mediaMetadata
            v.findViewById<TextView>(R.id.title).text =
                meta?.title ?: File(song.path).nameWithoutExtension
            v.findViewById<TextView>(R.id.subtitle).text =
                meta?.artist ?: File(song.path).parent
            v.findViewById<TextView>(R.id.score).text =
                if (song.score > 0) "+${song.score}" else song.score.toString()
            v.findViewById<ImageButton>(R.id.reset).setOnClickListener {
                resetAndRefresh(listOf(song.path))
            }
        }
    }
}
