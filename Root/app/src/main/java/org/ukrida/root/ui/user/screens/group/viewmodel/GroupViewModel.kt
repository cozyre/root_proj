package org.ukrida.root.ui.user.screens.group.viewmodel

import android.accounts.Account
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.GroupWithDetails
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

class GroupViewModel(
    private val groupRepository: GroupRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _groups = MutableStateFlow<Resource<List<GroupWithDetails>>>(Resource.Loading())
    val groups: StateFlow<Resource<List<GroupWithDetails>>> = _groups

    init {
        loadGroups()
    }

    fun loadGroups() {
        viewModelScope.launch {
            loadMyTours()
        }
    }

    private suspend fun loadMyTours() {
        _groups.value = Resource.Loading()

        val toursResult = groupRepository.getAllTours()
        if (toursResult.isFailure) {
            _groups.value = Resource.Error(toursResult.exceptionOrNull()?.message ?: "Failed to load tours")
            return
        }

        val tours = toursResult.getOrNull() ?: emptyList()

        val combined = coroutineScope {
            tours.map { tour ->
                async {
                    accountRepository.getOrderStatus(tour.id).mapCatching { detail ->
                        GroupWithDetails(
                            id = tour.id,
                            name = tour.name,
                            description = tour.description,
                            startDate = tour.startDate,
                            endDate = tour.endDate,
                            location = tour.location,
                            dresscode = tour.dresscode,
                            meetupTime = tour.meetupTime,
                            meetupAddress = tour.meetupAddress,
                            status = tour.status,
                            statusJoin = detail.statusJoin,
                            joinDate = tour.joinDate
                        )
                    }
                }
            }.awaitAll()
        }.mapNotNull { result ->
            result.getOrNull().also { detail ->
                result.exceptionOrNull()?.let {
                    Log.w("GroupViewModel", "Failed to load details: ${it.message}")
                }
            }
        }

        _groups.value = if (combined.isNotEmpty()) {
            Resource.Success(combined)
        } else {
            Resource.Error("No groups found")
        }
    }
}