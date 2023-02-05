package dev.netomarin.bballscore.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.netomarin.bballscore.R
import dev.netomarin.bballscore.databinding.MatchItemBinding
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MatchListAdapter(private val viewModel: MatchListViewModel) :
    RecyclerView.Adapter<MatchListAdapter.ViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<MatchItem> = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MatchItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    fun updateMatches(matchItemList: List<MatchItem>) {
        asyncListDiffer.submitList(matchItemList)
    }

    class ViewHolder(
        private val binding: MatchItemBinding,
        private val viewModel: MatchListViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        private val formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm z").withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault());

        fun bind(matchItem: MatchItem) {
            binding.matchTitleTextView.text =
                binding.matchTitleTextView.context.getString(
                    R.string.match_item_title,
                    matchItem.teamA,
                    matchItem.scoreTeamA,
                    matchItem.scoreTeamB,
                    matchItem.teamB
                )
            binding.matchDayTextView.text =
                formatter.format(matchItem.matchDate.toDate().toInstant())
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<MatchItem>() {
        override fun areItemsTheSame(oldItem: MatchItem, newItem: MatchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MatchItem, newItem: MatchItem): Boolean {
            return (oldItem.teamA == newItem.teamB) && (oldItem.matchDate == newItem.matchDate)
        }
    }
}