package band.effective.office.tv.leader.models

data class Hall(
    val id: Int,
    val name: String,
    val capacity: Int,
    val type:String,
    val square:Int,
    val tags:List<String>,
    val preparePeriod: Int,
    val photos: Photo?,
    val space_id: Int,
    val active: Boolean,
    val deleted_at: String?,
    val deleted_by: String?,
    val description: String,
    val order:Int
)
