package LeaderApi.JsonElements

data class Schedul(
    val hallId: Int?,
    val name: String,
    val description: String?,
    val speaker: String,
    val area: String?,
    val dateStart: String,
    val dateEnd: String,
    val hall: Hall?
)
