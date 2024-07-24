package perso.caupanharm.backend.models.henrik3

data class Henrik3MatchesV1(
    val status: Int,
    val results: Results,
    val data: List<MatchV1>
)





data class MapInfo(
    val id: String,
    val name: String
)

data class Season(
    val id: String,
    val short: String
)

data class Character(
    val id: String,
    val name: String
)

data class Shots(
    val head: Int,
    val body: Int,
    val leg: Int
)

data class Damage(
    val made: Int,
    val received: Int
)


