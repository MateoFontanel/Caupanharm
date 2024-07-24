package perso.caupanharm.backend.models.localdata

data class PlayersMatchData(
    val name: String,
    val matches: List<PlayersMatch>
)

data class PlayersMatch(
    val date: String,
    val map: String,
    val result: String,
    val allyScore: Int,
    val enemyScore: Int
    )