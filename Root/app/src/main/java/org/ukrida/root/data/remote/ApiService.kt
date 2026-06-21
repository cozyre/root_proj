package org.ukrida.root.data.remote

import org.ukrida.root.data.model.ApiResponse
import org.ukrida.root.data.model.AuthData
import org.ukrida.root.data.model.GroupResponse
import org.ukrida.root.data.model.GroupSingleResponse
import org.ukrida.root.data.model.LoginRequest
import org.ukrida.root.data.model.RegisterRequest
import org.ukrida.root.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //login & register
    @POST("index.php?route=auth/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse<AuthData>

    @POST("index.php?route=auth/register")
    suspend fun register(@Body body: RegisterRequest): ApiResponse<User>

    // Groups
    @GET("index.php")
    suspend fun getAllTours(
        @Query("route") route: String = "group/index"
    ): Response<GroupResponse>

    @GET("index.php")
    suspend fun getTourById(
        @Query("route") route: String = "group/show",
        @Query("id") id: Int
    ): Response<GroupSingleResponse>

    @GET("index.php")
    suspend fun getPastTours(
        @Query("route") route: String = "group/history",
        @Query("limit") limit: Int = 10
    ): Response<GroupResponse>
}