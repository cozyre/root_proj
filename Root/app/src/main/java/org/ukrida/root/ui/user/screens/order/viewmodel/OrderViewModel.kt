package org.ukrida.root.ui.user.screens.order.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.AccountStatus
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

class OrderViewModel(
    private val accountRepository: AccountRepository,
    private val groupRepository: GroupRepository
) : ViewModel() {
    private val _group = MutableStateFlow<Resource<Group>>(Resource.Loading())
    val group: StateFlow<Resource<Group>> = _group

    private val _order = MutableStateFlow<Resource<AccountStatus>>(Resource.Loading())
    val order: StateFlow<Resource<AccountStatus>> = _order
    fun loadOrder(groupId: Int) {
        viewModelScope.launch {
             groupRepository.getTourById(groupId)
        }
    }

    private suspend fun updateOrder(){
        val result = groupRepository.getTourById(9)
    }

    private fun getDummyGroup(groupId: Int): Group {
        val groups = listOf(
            Group(
                id = 1,
                name = "Holy Land",
                description = "Experience the places where Jesus walked.",
                location = "Jerusalem",
                dresscode = "Casual",
                status = "Open",
                startDate = "2026-10-01",
                endDate = "2026-10-12",
                meetupTime = "08:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = null,
                statusJoin = null
            ),
            Group(
                id = 2,
                name = "Jordan Pilgrimage",
                description = "Visit the Jordan River and Mount Nebo.",
                location = "Jordan",
                dresscode = "Casual",
                status = "Open",
                startDate = "2026-11-05",
                endDate = "2026-11-12",
                meetupTime = "09:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = null,
                statusJoin = "Pending"
            )
        )
        return groups.firstOrNull { it.id == groupId }
            ?: groups.first()
    }
}