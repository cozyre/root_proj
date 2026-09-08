package org.ukrida.root.data.repository

import okhttp3.MultipartBody
import org.ukrida.root.data.model.Profile
import org.ukrida.root.data.model.UpdateProfileRequest
import org.ukrida.root.data.remote.ApiService
import org.ukrida.root.utils.Resource

class ProfileRepository(private val api: ApiService) {

    suspend fun getProfile(): Resource<Profile> {
        return try {
            val res = api.getProfile()
            if (res.isSuccessful && res.body()?.success == true && res.body()?.data != null)
                Resource.Success(res.body()!!.data!!)
            else
                Resource.Error(res.body()?.message ?: "Failed to load profile")
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    suspend fun updateProfile(
        firstName: String,
        lastName: String,
        username: String,
        phone: String?,
        bio: String?,
        hidePhone: Boolean
    ): Resource<Profile> {
        return try {
            val res = api.updateProfile(
                body = UpdateProfileRequest(firstName, lastName, username, phone, bio, hidePhone)
            )
            if (res.isSuccessful && res.body()?.success == true && res.body()?.data != null)
                Resource.Success(res.body()!!.data!!)
            else
                Resource.Error(res.body()?.message ?: "Failed to update profile")
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    suspend fun uploadProfilePhoto(image: MultipartBody.Part): Resource<Profile> {
        return try {
            val res = api.uploadProfilePhoto(image = image)
            if (res.isSuccessful && res.body()?.success == true && res.body()?.data != null)
                Resource.Success(res.body()!!.data!!)
            else
                Resource.Error(res.body()?.message ?: "Failed to upload photo")
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}
