package dev.netomarin.bballscore.collections

import androidx.lifecycle.*
import dev.netomarin.bballscore.data.MatchRepository
import kotlinx.coroutines.launch

class MatchListViewModel(private val matchRepository: MatchRepository) : ViewModel() {

    /**
     * Mutable Live Data that initialize with the current list of saved Habits.
     */
    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(matchItemList = emptyList()))
    }

    fun onResume() {
        viewModelScope.launch {
            refreshMatchList()
        }
    }

    private suspend fun refreshMatchList() {
        uiState.postValue(UiState(matchRepository.getMatchList()))
    }

    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    data class UiState(val matchItemList: List<MatchItem>)

    @Suppress("UNCHECKED_CAST")
    class Factory(private val matchRepository: MatchRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MatchListViewModel(matchRepository) as T
        }
    }
}