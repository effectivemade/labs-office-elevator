package LeaderApi.JsonElements

data class Space(
    val id: Int,
    val phone: String,
    val phoneExtension: String?, // allways null
    val addressId: Int,
    val active: Boolean,
    val kworkingState: String?, // allways null
    val agenda: List<String>,
    val square:Double,
    val email: String,
    val name: String,
    val description: String,
    val rating: String?, // allways null
    val type:String,
    val minimalPeriod: Int,
    val photos: List<Photo>,
    val tags: List<String>,
    val socialNetworks: List<SocialNetwork>,
    val scheduleOnRequest: Boolean,
    val stat: SpaceStat,
    val createdAt: String,
    val updatedAt: String,
    val restrictEventOwnerOfflineConfirm: Boolean,
    val address: Address
)
