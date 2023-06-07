package band.effective

// set name environment variables here
object MattermostSettings {
    const val mattermostToken = "MATTERMOST_TOKEN"
    const val emojiToRequestSave = "EMOJI_TO_SAVE_REQUEST"
    const val emojiToSaveSuccess = "EMOJI_TO_SAVE_SACCESS"
    const val baseURL = "MATTERMOST_BASE_URL"
}
// synologyAlbumTypeName  - какой шаблон альбомов будет для создания фотографий Привем при шаблоне "Best Photo" будет
// "Best Photo 2023"
object SynologySettings {
    const val synologyAccount = "SYNOLOGY_ACCOUNT"
    const val synologyPassword = "SYNOLOGY_PASSWORD"
    const val synologyAlbumTypeName = "SYNOLOGY_ALBUM_TYPE_NAME"
    const val baseURL = "SYNOLOGY_BASE_URL"
}

