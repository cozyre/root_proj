package org.ukrida.root.data.remote

import org.ukrida.root.data.model.*
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

    // Groups -----------------------------------------------------------------
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

    //Accounts -----------------------------------------------------------------
    // POST ?route=account/order
    @POST("index.php")
    suspend fun orderTour(
        @Query("route") route: String = "account/order",
        @Body body: OrderRequest
    ): Response<ApiResponse<OrderResult>>

    // GET ?route=account/status&group_id=1
    @GET("index.php")
    suspend fun getOrderStatus(
        @Query("route") route: String = "account/status",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<AccountStatus>>

    // GET ?route=account/groupDetail&group_id=1
    @GET("index.php")
    suspend fun getGroupDetail(
        @Query("route") route: String = "account/groupDetail",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<GroupDetail>>

    // Itinerary -----------------------------------------------------------------
    @GET("index.php")
    suspend fun getItinerary(
        @Query("route")    route: String = "itinerary/getByDate",
        @Query("group_id") groupId: Int,
        @Query("date")     date: String
    ): Response<ApiResponse<Itinerary>>

    @GET("index.php")
    suspend fun getItineraryDates(
        @Query("route")    route: String = "itinerary/getDates",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<List<ItineraryDate>>>

    //Songs -----------------------------------------------------------------
    /** All songs in a group's songbook (no day filter) */
    @GET("index.php")
    suspend fun getSongs(
        @Query("route")    route: String = "song/list",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<List<SongSummary>>>

    /** Songs for a specific itinerary day */
    @GET("index.php")
    suspend fun getSongsByDate(
        @Query("route")    route: String = "song/listByDate",
        @Query("group_id") groupId: Int,
        @Query("date")     date: String
    ): Response<ApiResponse<List<SongSummary>>>

    /** Full song detail with lyrics */
    @GET("index.php")
    suspend fun getSong(
        @Query("route") route: String = "song/get",
        @Query("id")    id: Int
    ): Response<ApiResponse<Song>>

    /** Search within a group's assigned songs */
    @GET("index.php")
    suspend fun searchSongs(
        @Query("route")    route: String = "song/search",
        @Query("group_id") groupId: Int,
        @Query("q")        query: String
    ): Response<ApiResponse<List<SongSummary>>>

    /** Browse global song library (admin use) */
    @GET("index.php")
    suspend fun browseSongs(
        @Query("route") route: String = "song/browse",
        @Query("q")     query: String = ""
    ): Response<ApiResponse<List<SongBrowseItem>>>

    //Devotions -----------------------------------------------------------------
    @GET("index.php")
    suspend fun getDevotion(
        @Query("route")    route: String = "devotion/get",
        @Query("group_id") groupId: Int,
        @Query("date")     date: String
    ): Response<ApiResponse<Devotion>>

    @GET("index.php")
    suspend fun getDevotionDates(
        @Query("route")    route: String = "devotion/getDates",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<List<DevotionDate>>>
}