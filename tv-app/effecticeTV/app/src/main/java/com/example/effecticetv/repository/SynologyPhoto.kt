package com.example.effecticetv.repository

import com.example.effecticetv.network.synology.model.Photo

interface SynologyPhoto {
    fun getPhotos(): List<Photo>
    fun changeDir(dir: String)
    fun getFiles()
    fun setIP(ip: String)
}