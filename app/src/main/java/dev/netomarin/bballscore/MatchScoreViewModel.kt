package dev.netomarin.bballscore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dev.netomarin.bballscore.collections.MatchItem
import dev.netomarin.bballscore.data.MatchRepository
import kotlinx.coroutines.launch
import java.util.*

class MatchScoreViewModel(private val matchRepository: MatchRepository) : ViewModel() {

    fun saveMatch(teamA: String, scoreTeamA: Int, teamB: String, scoreTeamB: Int) {
        viewModelScope.launch {
            matchRepository.saveNewMatchScore(
                MatchItem(
                    teamA = teamA,
                    scoreTeamA = scoreTeamA,
                    teamB = teamB,
                    scoreTeamB = scoreTeamB,
                    matchDate = Timestamp.now()
                )
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val matchRepository: MatchRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MatchScoreViewModel(matchRepository) as T
        }
    }
}