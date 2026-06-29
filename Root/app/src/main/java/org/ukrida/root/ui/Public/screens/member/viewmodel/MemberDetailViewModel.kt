package org.ukrida.root.ui.Public.screens.members.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.MemberDetail

class MemberDetailViewModel : ViewModel() {
    private val _member = MutableStateFlow<MemberDetail?>(null)
    val member = _member.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    fun loadMember(
        userId: Int,
        groupId: Int
    ) {
        // TODO Backend Integration
        // repository.getMemberDetail(userId, groupId)
        loadDummy(userId)
    }
    private fun loadDummy(userId: Int) {
        _member.value = MemberDetail(
            id = userId,
            username = "josh",
            firstName = "Josh",
            lastName = "Valentino",
            email = "josh@email.com",
            phone = "081234567890",
            profilePhotoUrl = null,
            role = "Member",
            statusJoin = "Accepted",
            joinDate = "2026-06-29"
        )
    }
}