package org.ukrida.root.data

import android.content.Context
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.AuthRepository
import org.ukrida.root.data.repository.DevotionRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.ItineraryRepository
import org.ukrida.root.data.repository.JournalRepository
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.data.repository.SongRepository
import org.ukrida.root.utils.SessionManager
import kotlin.getValue

class AppContainer {

    private val api = RetrofitClient.instance

//    val adminRepository by lazy {
//        AdminRepository(api)
//    }

    val groupRepository by lazy {
        GroupRepository(api)
    }

    val accountRepository by lazy {
        AccountRepository(api)
    }

    val authRepository by lazy {
        AuthRepository(api)
    }

    val devotionRepository by lazy {
        DevotionRepository(api)
    }

    val galleryRepository by lazy {
        GalleryRepository(api)
    }

    val itineraryRepository by lazy {
        ItineraryRepository(api)
    }

    val journalRepository by lazy {
        JournalRepository(api)
    }

    val memberRepository by lazy {
        MemberRepository(api)
    }

//    val profileRepository by lazy {
//        ProfileRepository(api)
//    }

    val songRepository by lazy {
        SongRepository(api)
    }
}