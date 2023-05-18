package band.effective.synology

import band.effective.core.Either
import band.effective.core.ErrorReason
import band.effective.synology.models.AuthModel
import band.effective.synology.models.SynologyAlbumInfo
import band.effective.synology.models.respone.AddPhotoToAlbumResponse
import band.effective.synology.models.respone.SynologyAlbumsResponse
import band.effective.synology.models.respone.SynologyAuthResponse
import band.effective.synology.models.respone.UploadPhotoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SynologyRepository {
    suspend fun login()

    suspend fun getAlbums(): Either<ErrorReason, SynologyAlbumsResponse>

    suspend fun uploadPhoto(requestBody: RequestBody): Either<ErrorReason, UploadPhotoResponse>

   // TODO replace  this
    suspend fun addPhotoToAlbums(albumId: Int, itemId: Int): Either<ErrorReason, AddPhotoToAlbumResponse>

    suspend fun uploadPhotoToAlbum(file: ByteArray, fileName: String, fileType: String): Either<ErrorReason, UploadPhotoResponse>

    suspend fun createAlbum(albumName: String): Either<ErrorReason, SynologyAlbumInfo>
}