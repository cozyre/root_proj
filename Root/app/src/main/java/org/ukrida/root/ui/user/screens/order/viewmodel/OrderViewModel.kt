package org.ukrida.root.ui.user.screens.order.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.AccountStatus
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

class OrderViewModel(
    private val groupRepository: GroupRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    data class OrderUiState(
        val group: Resource<Group> = Resource.Loading(),
        val accountStatus: Resource<AccountStatus?> = Resource.Success(null),
        val orderAction: Resource<Unit>? = null
    )

    fun loadOrder(groupId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(group = Resource.Loading()) }
            
            val groupResult = groupRepository.getTourById(groupId)
            val statusResult = accountRepository.getOrderStatus(groupId)

            if (groupResult.isSuccess) {
                val groupData = groupResult.getOrNull()!!
                val statusData = statusResult.getOrNull()

                // Sync statusJoin from AccountStatus to Group model if available
                val updatedGroup = groupData.copy(
                    statusJoin = statusData?.statusJoin ?: groupData.statusJoin
                )

                _uiState.update { 
                    it.copy(
                        group = Resource.Success(updatedGroup),
                        accountStatus = Resource.Success(statusData)
                    )
                }
            } else {
                val errorMsg = groupResult.exceptionOrNull()?.message ?: "Failed to fetch group details"
                _uiState.update { it.copy(group = Resource.Error(errorMsg)) }
            }
        }
    }

    fun createOrder(groupId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(orderAction = Resource.Loading()) }
            val result = accountRepository.orderTour(groupId)
            if (result.isSuccess) {
                _uiState.update { it.copy(orderAction = Resource.Success(Unit)) }
                loadOrder(groupId) // Refresh to update status to "pending"
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Order failed"
                _uiState.update { it.copy(orderAction = Resource.Error(errorMsg)) }
            }
        }
    }

    fun resetOrderAction() {
        _uiState.update { it.copy(orderAction = null) }
    }
}
