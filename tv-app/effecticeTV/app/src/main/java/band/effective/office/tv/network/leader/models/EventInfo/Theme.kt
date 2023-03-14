package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Theme(
    val childCount: Int,
    val code: Any?,
    val createdBy: Int,
    val gid: Int,
    val id: Int,
    val keywords: List<Any?>,
    val moderatedAt: Any?,
    val moderatedBy: Any?,
    val name: String,
    val old_type: String?,
    val parentId: Any?,
    val photo: PhotoXX,
    val photos: Photos,
    val priority: Int,
    val status: Int,
    val updated_at: String?,
    val visible: Boolean
)