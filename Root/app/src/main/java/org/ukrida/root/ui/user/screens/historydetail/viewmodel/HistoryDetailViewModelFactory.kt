package org.ukrida.root.ui.user.screens.historydetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.MemberRepository

class HistoryDetailViewModelFactory(
    private val accountRepository: AccountRepository,
    private val galleryRepository: GalleryRepository,
    private val memberRepository: MemberRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HistoryDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryDetailViewModel(
                accountRepository,
                galleryRepository,
                memberRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}