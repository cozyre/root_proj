package org.ukrida.root.ui.admin.screens.approval.viewmodel

import androidx.compose.runtime.mutableStateOf
import org.ukrida.root.data.model.Group

private val _groups = mutableStateOf(
    listOf(
        Group(
            id = 1,
            name = "ROOT Holy Land 2026",
            description = "Tour ke Israel",
            location = "Israel",
            dresscode = null,
            status = "Pending",
            startDate = null,
            endDate = null,
            meetupTime = null,
            meetupAddress = null
        ),
        Group(
            id = 2,
            name = "ROOT Jordan 2026",
            description = "Tour ke Jordan",
            location = "Jordan",
            dresscode = null,
            status = "Approve",
            startDate = null,
            endDate = null,
            meetupTime = null,
            meetupAddress = null
        ),
        Group(
            id = 3,
            name = "ROOT Egypt 2026",
            description = "Tour ke Egypt",
            location = "Egypt",
            dresscode = null,
            status = "Pending",
            startDate = null,
            endDate = null,
            meetupTime = null,
            meetupAddress = null
        )
    )
)

val pendingGroups: List<Group>
    get() = _groups.value.filter {
        it.status.equals("Pending", ignoreCase = true)
    }
