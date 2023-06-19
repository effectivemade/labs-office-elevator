package band.effective.office.tv.screen.message.secondaryMessage

sealed class SecondaryMessageScreenEvents {
    object OnClickPlayButton : SecondaryMessageScreenEvents()
    object OnClickNextButton : SecondaryMessageScreenEvents()
    object OnClickPrevButton : SecondaryMessageScreenEvents()
}