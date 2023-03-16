package band.effective.office.tv.repository

import band.effective.office.tv.model.domain.Resource

interface SynologyPhoto {
    suspend fun getPhotosUrl(folderPath: String): Resource<List<String>>

    suspend fun auth(): Resource<Boolean>
}

