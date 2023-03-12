package band.effective.office.tv.repository.impl

import band.effective.office.tv.model.Photo
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.repository.SynologyPhoto
import javax.inject.Inject

class SynologyPhotoImpl @Inject constructor(
    private val synologyApi: SynologyApi
): SynologyPhoto {
    override fun getPhotos(): List<Photo> {
        TODO("Not yet implemented")
    }

    override fun changeDir(dir: String) {
        TODO("Not yet implemented")
    }

    override fun getFiles() {
        TODO("Not yet implemented")
    }

    override fun setIP(ip: String) {
        TODO("Not yet implemented")
    }
}