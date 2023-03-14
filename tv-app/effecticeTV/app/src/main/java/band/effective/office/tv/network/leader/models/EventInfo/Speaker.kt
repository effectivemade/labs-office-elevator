package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Speaker(
    val completed: Boolean,
    val completed_at: Any?,
    val control_passed: Any?,
    val created_at: String,
    val event_id: Int,
    val format: Any?,
    val id: String,
    val moderation: String,
    val number_participants: Any?,
    val quiz_answer_id: Any?,
    val rejected_by: Any?,
    val role: String,
    val role_id: Int,
    val space_id: Any?,
    val status: Int,
    val team_id: Any?,
    val updated_at: Any?,
    val user: User,
    val user_id: Int,
    val visible: Boolean,
    val w_group_ids: Any?
)