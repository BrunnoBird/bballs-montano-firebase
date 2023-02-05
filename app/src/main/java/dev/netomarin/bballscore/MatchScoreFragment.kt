package dev.netomarin.bballscore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.netomarin.bballscore.data.MatchFirestoreRepository
import dev.netomarin.bballscore.databinding.FragmentMatchScoreBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MatchScoreFragment : Fragment() {

    private var _binding: FragmentMatchScoreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MatchScoreViewModel by activityViewModels {
        val matchRepository = MatchFirestoreRepository()
        MatchScoreViewModel.Factory(matchRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchScoreBinding.inflate(inflater, container, false)
        binding.save.setOnClickListener { saveMatchScore() }
        return binding.root
    }

    private fun saveMatchScore() {
        viewModel.saveMatch(
            binding.editTextTeamA.text.toString(),
            binding.editTextScoreA.text.toString().toInt(),
            binding.editTextTeamB.text.toString(),
            binding.editTextScoreB.text.toString().toInt()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}