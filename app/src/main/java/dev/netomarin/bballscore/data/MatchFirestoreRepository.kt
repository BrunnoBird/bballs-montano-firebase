package dev.netomarin.bballscore.data

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.netomarin.bballscore.collections.MatchItem
import kotlinx.coroutines.tasks.await

class MatchFirestoreRepository : MatchRepository {

    private val db = Firebase.firestore

    override suspend fun getMatchList(): List<MatchItem> {
        val resultList = mutableListOf<MatchItem>()
        val matchList = db.collection(COLLECTION)
            .get()
            .addOnFailureListener { e ->
                Log.e("MatchFirestoreRepository", "Error reading Firestore", e)
            }.await()

        for (match in matchList) {
            resultList.add(
                MatchItem(
                    id = match.id,
                    teamA = match.getString("teamA")!!,
                    scoreTeamA = match.getLong("scoreTeamA")!!.toInt(),
                    teamB = match.getString("teamB")!!,
                    scoreTeamB = match.getLong("scoreTeamB")!!.toInt(),
                    matchDate = match.getTimestamp("matchDate")!!
                )
            )
        }
        return resultList
    }

    override suspend fun getMatchDetails(matchId: String): MatchItem {
        TODO("Not yet implemented")
    }

    override suspend fun saveNewMatchScore(match: MatchItem) {
        db.collection(COLLECTION)
            .add(match)
            .addOnSuccessListener { docRef ->
                Log.d("MatchFirestoreRepository", "Match saved with ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("MatchFirestoreRepository", "Error saving match.", e)
            }
    }

    companion object {
        private const val COLLECTION = "partidas"
    }
}