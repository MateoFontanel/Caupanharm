package perso.caupanharm.backend.models.henrik3

data class Henrik3Errors(
    val errors: List<Error>
)

data class Error(
    val code: Int,
    val message: String,
    val status: Int,
    val details: String?
)