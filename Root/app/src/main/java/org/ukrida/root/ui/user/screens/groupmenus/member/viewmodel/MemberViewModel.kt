package org.ukrida.root.ui.user.screens.groupmenus.member.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Member
import org.ukrida.root.data.model.MemberDetail
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.utils.Resource

class MemberViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _members = MutableStateFlow<Resource<List<Member>>>(Resource.Loading())
    val members: StateFlow<Resource<List<Member>>> = _members.asStateFlow()

    private val _member = MutableStateFlow<Resource<MemberDetail>>(Resource.Loading())
    val member: StateFlow<Resource<MemberDetail>> = _member.asStateFlow()

    fun loadMembers(groupId: Int) {
        viewModelScope.launch {
            _members.value = Resource.Loading()
            _members.value = memberRepository.listMembers(groupId)
        }
    }

    fun loadMember(userId: Int, groupId: Int) {
        viewModelScope.launch {
            _member.value = Resource.Loading()
            _member.value = memberRepository.getMemberDetail(userId, groupId)
        }
    }
}
