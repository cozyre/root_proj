package org.ukrida.root.data.repository

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.remote.ApiService
import org.ukrida.root.utils.Resource
import java.io.File

class GalleryRepository(private val api: ApiService) {

    suspend fun listImages(groupId: Int): Resource<List<GroupImage>> = safeCall {
        val res = api.listImages(groupId = groupId)
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data ?: emptyList())
        else
            Resource.Error(res.body()?.message ?: "Failed to load gallery")
    }

    suspend fun uploadImage(
        groupId: Int,
        imageFile: File,
        caption: String? = null
        ): Resource<GroupImage> = safeCall {
        val mimeType = when (imageFile.extension.lowercase()) {
        "png"  -> "image/png"
        "webp" -> "image/webp"
        else   -> "image/jpeg" }

        val imagePart = MultipartBody.Part.createFormData(
        "image",
        imageFile.name,
        imageFile.asRequestBody(mimeType.toMediaType())
        )
        val groupIdPart = groupId.toString().toRequestBody("text/plain".toMediaType())
        val captionPart = caption?.toRequestBody("text/plain".toMediaType())
        val res = api.uploadImage(
            groupId = groupIdPart,
            caption = captionPart,
            image   = imagePart
        )
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Upload failed")
    }
    private suspend fun <T> safeCall(block: suspend () -> Resource<T>): Resource<T> {
        return try {
            block()
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}