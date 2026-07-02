package org.ukrida.root.ui.user.screens.groupmenus.member.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.MemberRepository

class MemberViewModelFactory(
    private val memberRepository: MemberRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(
                memberRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}