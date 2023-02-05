package dev.netomarin.bballscore.collections

import com.google.firebase.Timestamp

data class MatchItem(
    val id: String? = null,
    val teamA: String? = null,
    val teamB: String? = null,
    val matchDate: Timestamp = Timestamp.now(),
    val scoreTeamA: Int? = 0,
    val scoreTeamB: Int? = 0
)