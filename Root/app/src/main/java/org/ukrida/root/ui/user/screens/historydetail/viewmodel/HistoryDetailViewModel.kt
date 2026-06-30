package org.ukrida.root.ui.user.screens.historydetail.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.model.Member

class HistoryDetailViewModel : ViewModel() {

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members = _members.asStateFlow()

    private val _gallery = MutableStateFlow<List<GroupImage>>(emptyList())
    val gallery = _gallery.asStateFlow()

    fun loadHistoryDetail(groupId: Int) {
        loadDummy(groupId)
    }

    private fun loadDummy(groupId: Int) {
        _group.value = getDummyGroup(groupId)
        _members.value = getDummyMembers()
        _gallery.value = getDummyGallery()
    }
    private fun getDummyGroup(groupId: Int): Group {

        val groups = listOf(

            Group(
                id = 1,
                name = "Holy Land",
                description = "Experience the places where Jesus walked.",
                location = "Jerusalem",
                dresscode = "Casual",
                status = "Completed",
                startDate = "2026-10-01",
                endDate = "2026-10-12",
                meetupTime = "08:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = "2026-07-15",
                statusJoin = "Approved"
            ),

            Group(
                id = 2,
                name = "Jordan Pilgrimage",
                description = "Visit the Jordan River and Mount Nebo.",
                location = "Jordan",
                dresscode = "Casual",
                status = "Completed",
                startDate = "2026-11-05",
                endDate = "2026-11-12",
                meetupTime = "09:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = "2026-08-01",
                statusJoin = "Approved"
            )

        )

        return groups.firstOrNull { it.id == groupId }
            ?: groups.first()
    }
    private fun getDummyMembers() = listOf(

        Member(
            id = 1,
            username = "josh",
            firstName = "Josh",
            lastName = "Valentino",
            profilePhotoUrl = null,
            role = "Leader"
        ),

        Member(
            id = 2,
            username = "claudio",
            firstName = "Claudio",
            lastName = "Jose",
            profilePhotoUrl = null,
            role = "Member"
        ),

        Member(
            id = 3,
            username = "richard",
            firstName = "Richard",
            lastName = "Sutisna",
            profilePhotoUrl = null,
            role = "Member"
        ),
        Member(
            id = 4,
            username = "josh",
            firstName = "Josh",
            lastName = "Valentino",
            profilePhotoUrl = null,
            role = "Leader"
        ),

        Member(
            id = 5,
            username = "claudio",
            firstName = "Claudio",
            lastName = "Jose",
            profilePhotoUrl = null,
            role = "Member"
        ),

        Member(
            id = 6,
            username = "richard",
            firstName = "Richard",
            lastName = "Sutisna",
            profilePhotoUrl = null,
            role = "Member"
        ),
        Member(
            id = 7,
            username = "josh",
            firstName = "Josh",
            lastName = "Valentino",
            profilePhotoUrl = null,
            role = "Leader"
        ),
        Member(
            id = 8,
            username = "claudio",
            firstName = "Claudio",
            lastName = "Jose",
            profilePhotoUrl = null,
            role = "Member"
        ),

        Member(
            id = 9,
            username = "richard",
            firstName = "Richard",
            lastName = "Sutisna",
            profilePhotoUrl = null,
            role = "Member"
        )
    )
    private fun getDummyGallery() = listOf(

        GroupImage(
            id = 1,
            groupId = 1,
            imageUrl = "",
            caption = "Pyramid",
            imageType = "cover",
            sortOrder = 1,
            createdAt = ""
        ),

        GroupImage(
            id = 2,
            groupId = 1,
            imageUrl = "",
            caption = "Jerusalem",
            imageType = "gallery",
            sortOrder = 2,
            createdAt = ""
        ),
        GroupImage(
            id = 3,
            groupId = 1,
            imageUrl = "",
            caption = "Pyramid",
            imageType = "cover",
            sortOrder = 1,
            createdAt = ""
        ),

        GroupImage(
            id = 4,
            groupId = 1,
            imageUrl = "",
            caption = "Jerusalem",
            imageType = "gallery",
            sortOrder = 2,
            createdAt = ""
        ),
        GroupImage(
            id = 5,
            groupId = 1,
            imageUrl = "",
            caption = "Pyramid",
            imageType = "cover",
            sortOrder = 1,
            createdAt = ""
        ),

        GroupImage(
            id = 6,
            groupId = 1,
            imageUrl = "",
            caption = "Jerusalem",
            imageType = "gallery",
            sortOrder = 2,
            createdAt = ""
        ),
        GroupImage(
            id = 7,
            groupId = 1,
            imageUrl = "",
            caption = "Pyramid",
            imageType = "cover",
            sortOrder = 1,
            createdAt = ""
        ),

        GroupImage(
            id = 8,
            groupId = 1,
            imageUrl = "",
            caption = "Jerusalem",
            imageType = "gallery",
            sortOrder = 2,
            createdAt = ""
        )
    )
}