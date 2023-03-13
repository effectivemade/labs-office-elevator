package band.effective.office.tv.repository

import band.effective.office.tv.core.network.Resource
import band.effective.office.tv.model.Photo

interface SynologyPhoto {
    suspend fun getPhotos(): Resource<List<Photo>>

   suspend fun changeDir(dir: String): Resource<Boolean>

    suspend fun getFiles(): Resource<Boolean>

    suspend fun auth(): Resource<Boolean>
}
