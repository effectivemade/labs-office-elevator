package band.effective.office.elevator.ui.models

import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class UserDataEditProfile
    (val title: StringResource,
     val icon: ImageResource) {
    Person(
        icon = MainRes.images.person_ic,
        title = MainRes.strings.last_name_and_first_name,
    ),
    Post(
        icon = MainRes.images.symbols_work,
        title = MainRes.strings.post,
    ),
    Phone(
        icon = MainRes.images.mask_number,
        title = MainRes.strings.phone_number,
    ),
    Telegram(
        icon = MainRes.images.mask_commercial_at,
        title = MainRes.strings.telegram,
    )
}
inline fun <reified : Enum<UserDataEditProfile>> getAllUserDataEditProfile(): Array<UserDataEditProfile> {
    return (enumValues())
}