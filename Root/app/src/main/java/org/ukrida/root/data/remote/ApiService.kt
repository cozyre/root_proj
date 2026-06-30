package org.ukrida.root.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.ukrida.root.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    //login & register
    @POST("index.php?route=auth/login")
    suspend fun login(@Body body: LoginRequest): ApiResponse<AuthData>

    @POST("index.php?route=auth/register")
    suspend fun register(@Body body: RegisterRequest): ApiResponse<User>

    @POST("index.php")
    suspend fun refreshToken(
        @Query("route") route: String = "auth/refresh"
    ): Response<ApiResponse<Map<String, Any>>>

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

    //Journal -----------------------------------------------------------------
    @POST("index.php")
    suspend fun createJournal(
        @Query("route") route: String = "journal/create",
        @Body body: CreateJournalRequest
    ): Response<ApiResponse<Journal>>

    @GET("index.php")
    suspend fun listJournals(
        @Query("route")    route: String = "journal/list",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<List<Journal>>>

    @POST("index.php")
    suspend fun updateJournal(
        @Query("route") route: String = "journal/update",
        @Body body: UpdateJournalRequest
    ): Response<ApiResponse<Journal>>

    @POST("index.php")
    suspend fun deleteJournal(
        @Query("route") route: String = "journal/delete",
        @Body body: DeleteJournalRequest
    ): Response<ApiResponse<Unit>>

    //Gallery -----------------------------------------------------------------
    @GET("index.php")
    suspend fun listImages(
        @Query("route")    route: String = "gallery/list",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<List<GroupImage>>>

    /**
     * Upload an image (from device gallery OR camera).
     * Android side: convert the URI/File to a MultipartBody.Part before calling.
     * Both sources (camera + gallery) produce a File — the upload call is identical.
     */
    @Multipart
    @POST("index.php")
    suspend fun uploadImage(
        @Query("route")     route: String = "gallery/upload",
        @Part("group_id")   groupId: RequestBody,
        @Part("caption")    caption: RequestBody?,
        @Part            image: MultipartBody.Part
    ): Response<ApiResponse<GroupImage>>

    //Member -----------------------------------------------------------------
    @GET("index.php")
    suspend fun listMembers(
        @Query("route")    route: String = "member/list",
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<List<Member>>>

    @GET("index.php")
    suspend fun getMemberDetail(
        @Query("route")    route: String = "member/detail",
        @Query("user_id")  userId: Int,
        @Query("group_id") groupId: Int
    ): Response<ApiResponse<MemberDetail>>

    @GET("index.php")
    suspend fun getProfile(
        @Query("route") route: String = "profile/get"
    ): Response<ApiResponse<Profile>>

    @POST("index.php")
    suspend fun updateProfile(
        @Query("route") route: String = "profile/update",
        @Body body: UpdateProfileRequest
    ): Response<ApiResponse<Profile>>

    //Admin -----------------------------------------------------------------

    @GET("index.php")
    suspend fun getPendingAccounts(
        @Query("route") route: String = "admin/accounts/pending",
        @Query("group_id") groupId: Int? = null
    ): Response<ApiResponse<List<PendingAccount>>>


    // Admin — Trips ─────────────────────────────────────────────────────────
    /** GET ?route=admin/trips  →  list of completed/archived trips */
    @GET("index.php")
    suspend fun adminGetTrips(
        @Query("route") route: String = "admin/trips"
    ): Response<ApiResponse<List<CompletedTrip>>>

    /** POST ?route=admin/trip/create */
    @POST("index.php")
    suspend fun adminCreateTrip(
        @Query("route") route: String = "admin/trip/create",
        @Body body: AdminTripRequest
    ): Response<ApiResponse<AdminTripResult>>

    /** POST ?route=admin/trip/update */
    @POST("index.php")
    suspend fun adminUpdateTrip(
        @Query("route") route: String = "admin/trip/update",
        @Body body: AdminTripUpdateRequest
    ): Response<ApiResponse<AdminTripResult>>

    // Admin — Orders ────────────────────────────────────────────────────────

    /** POST ?route=admin/order/approve */
    @POST("index.php")
    suspend fun adminApproveOrder(
        @Query("route") route: String = "admin/order/approve",
        @Body body: AdminOrderAction
    ): Response<ApiResponse<AdminOrderResult>>

    /** POST ?route=admin/order/reject */
    @POST("index.php")
    suspend fun adminRejectOrder(
        @Query("route") route: String = "admin/order/reject",
        @Body body: AdminOrderAction
    ): Response<ApiResponse<AdminOrderResult>>

    // Admin — Members ───────────────────────────────────────────────────────

    /** POST ?route=admin/member/remove */
    @POST("index.php")
    suspend fun adminRemoveMember(
        @Query("route") route: String = "admin/member/remove",
        @Body body: AdminMemberRemoveRequest
    ): Response<ApiResponse<Unit>>

    // Admin — Gallery ───────────────────────────────────────────────────────

    /** POST ?route=admin/gallery/removeImage */
    @POST("index.php")
    suspend fun adminRemoveImage(
        @Query("route") route: String = "admin/gallery/removeImage",
        @Body body: AdminImageRemoveRequest
    ): Response<ApiResponse<Unit>>

    // Admin — Songs ─────────────────────────────────────────────────────────

    /** POST ?route=admin/song/create  →  add to global library */
    @POST("index.php")
    suspend fun adminCreateSong(
        @Query("route") route: String = "admin/song/create",
        @Body body: AdminSongRequest
    ): Response<ApiResponse<Song>>

    /** POST ?route=admin/song/addToGroup  →  assign to group/day */
    @POST("index.php")
    suspend fun adminAddSongToGroup(
        @Query("route") route: String = "admin/song/addToGroup",
        @Body body: AdminSongAddToGroupRequest
    ): Response<ApiResponse<AdminSongAddResult>>

    // Admin — Devotions ─────────────────────────────────────────────────────

    /** POST ?route=admin/devotion/create */
    @POST("index.php")
    suspend fun adminCreateDevotion(
        @Query("route") route: String = "admin/devotion/create",
        @Body body: AdminDevotionRequest
    ): Response<ApiResponse<AdminDevotion>>

    /** POST ?route=admin/devotion/update */
    @POST("index.php")
    suspend fun adminUpdateDevotion(
        @Query("route") route: String = "admin/devotion/update",
        @Body body: AdminDevotionUpdateRequest
    ): Response<ApiResponse<AdminDevotion>>
}