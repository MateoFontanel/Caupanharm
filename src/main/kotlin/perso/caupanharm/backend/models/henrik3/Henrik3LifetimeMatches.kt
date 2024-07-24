package perso.caupanharm.backend.models.henrik3

data class Henrik3LifetimeMatches (
    val status: String,
    val name: String?,
    val tag: String?,
    val results: Results,
    val data: List<MatchV1>
)