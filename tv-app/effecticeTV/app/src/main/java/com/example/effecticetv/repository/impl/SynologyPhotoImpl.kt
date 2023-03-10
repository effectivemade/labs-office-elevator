package com.example.effecticetv.repository.impl

import com.example.effecticetv.network.synology.model.Photo
import com.example.effecticetv.repository.SynologyPhoto

class SynologyPhotoImpl(
    //private val restApi:,

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