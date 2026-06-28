package org.ukrida.root.data

import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GroupRepository

class AppContainer {

    private val api = RetrofitClient.instance

    val adminRepository by lazy {
        AdminRepository(api)
    }

    val groupRepository by lazy {
        GroupRepository(api)
    }

}