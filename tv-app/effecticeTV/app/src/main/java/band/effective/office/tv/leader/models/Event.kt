package LeaderApi.JsonElements

data class Event(
    val id: Int,
    val createdBy: Int,
    val live_public: Boolean,
    val live: List<String>?,
    val stat: Stat,
    val themes: List<Theme>,
    val type: Type,
    val info: String?, // allways null
    val status: String?,
    val moderation: String,
    val full_info: String,
    val full_name: String,
    val date_start: String,
    val date_end: String,
    val format: String,
    val space: Space,
    val plase: String?, // allways null
    val schedules: List<Schedul>,
    val networking: Networking?,
    val participation_format: String,
    val team_size_min: Int,
    val team_size_max: Int,
    val team_type: String?, // allways null
    val finished: Boolean,
    val afterQuizId: String?,
    val needFeedback: Boolean,
    val hash_tags: List<String>,
    val network_parent_id: Int?,
    val city: String,
    val city_id: Int,
    val halls: List<Hall>,
    val photo: String,
    val photo_520: String,
    val photo_360: String,
    val photo_180: String,
    val timezone: TimeZone,
    val needStartNotification: Boolean,
    val delivered: Boolean,
    val indexedAt: Int,
    val isFavorite: Boolean
)
