package org.ukrida.root.ui.admin.screens.finished.viewmodel

data class MemberUiModel(
    val id: String,
    val name: String,
    val profilePhotoUrl: String?
)

data class DocumentationUiModel(
    val id: String,
    val imageUrl: String?
)

data class LeaderUiModel(
    val name: String,
    val photoUrl: String?
)

data class ApprovalUiModel(
    val accountId: Int,
    val userName: String,
    val groupName: String,
    val status: String
)