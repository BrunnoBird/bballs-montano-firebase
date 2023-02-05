package dev.netomarin.bballscore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import dev.netomarin.bballscore.collections.MatchListAdapter
import dev.netomarin.bballscore.collections.MatchListLifecycleObserver
import dev.netomarin.bballscore.collections.MatchListViewModel
import dev.netomarin.bballscore.data.MatchFirestoreRepository
import dev.netomarin.bballscore.data.MatchRepository
import dev.netomarin.bballscore.databinding.FragmentMatchListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MatchListFragment : Fragment() {

    private var _binding: FragmentMatchListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MatchListAdapter

    private val viewModel: MatchListViewModel by activityViewModels {
        val matchRepository = MatchFirestoreRepository()
        MatchListViewModel.Factory(matchRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MatchListLifecycleObserver(viewModel))
        adapter = MatchListAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchListBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
             findNavController().navigate(R.id.action_matchListFragment_to_matchScoreFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.matchListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.matchListRecyclerView.adapter = adapter

        // Adding decorations to our recycler view
        addingDividerDecoration()

        // Observer UI State for changes.
        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) {
            bindUiState(it)
        }
    }

    private fun bindUiState(uiState: MatchListViewModel.UiState) {
        adapter.updateMatches(uiState.matchItemList)
    }

    private fun addingDividerDecoration() {
        // Adding Line between items with MaterialDividerItemDecoration
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)

        // Adding the line at the end of the list
        divider.isLastItemDecorated = true

        val resources = requireContext().resources

        // Adding start spacing
        divider.dividerInsetStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

        // Defining size of the line
        divider.dividerThickness = resources.getDimensionPixelSize(R.dimen.divider_height)
        divider.dividerColor = ContextCompat.getColor(requireContext(), R.color.primary_200)

        binding.matchListRecyclerView.addItemDecoration(divider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}