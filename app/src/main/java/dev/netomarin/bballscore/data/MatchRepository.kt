package dev.netomarin.bballscore.data

import dev.netomarin.bballscore.collections.MatchItem

interface MatchRepository {
    suspend fun getMatchList(): List<MatchItem>
    suspend fun getMatchDetails(matchId: String): MatchItem
    suspend fun saveNewMatchScore(match: MatchItem)
}