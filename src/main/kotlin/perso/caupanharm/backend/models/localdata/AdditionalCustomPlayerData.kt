package perso.caupanharm.backend.models.localdata

data class AdditionalCustomPlayerData(
    val name: String,
    val stats: Stats
)

data class Stats(
    val rank: String,
    val favoriteAgents: List<String>,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val kast: Float,
    val kd: Float,
    val acs: Float,
    val killsPerRound: Float,
    val wins: Int,
    val losses: Int
)