package perso.caupanharm.backend.models


data class BracketMatchData (
    val id: String,
    val nextMatchId: String?,
    val tournamentRoundText: String,
    val state : State,
    val participants : List<Participant>
)

enum class State{
    NO_SHOW,
    WALK_OVER,
    NO_PARTY,
    DONE,
    SCORE_DONE
}

data class Participant(
    val id: String,
    val resultText : String,
    val isWinner: Boolean,
    val status : Status?,
    val name: String
)

enum class Status {
    PLAYED,
    NO_SHOW,
    WALK_OVER,
    NO_PARTY
}