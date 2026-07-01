package org.ukrida.root.ui.user.screens.members.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Member

class MemberViewModel : ViewModel() {
    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members = _members.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadMembers(groupId: Int) {
        // TODO Backend Integration
        // repository.listMembers(groupId)
        loadDummy()
    }

    private fun loadDummy() {
        _members.value = listOf(
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
                username = "kevin",
                firstName = "Kevin",
                lastName = "Wijaya",
                profilePhotoUrl = null,
                role = "Member"
            ),
            Member(
                id = 5,
                username = "andrew",
                firstName = "Andrew",
                lastName = "Tan",
                profilePhotoUrl = null,
                role = "Member"
            )
        )
    }
}