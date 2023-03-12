package band.effective.office.tv.repository

import band.effective.office.tv.model.Photo

interface SynologyPhoto {
    fun getPhotos(): List<Photo>
    fun changeDir(dir: String)
    fun getFiles()
    fun setIP(ip: String)
}