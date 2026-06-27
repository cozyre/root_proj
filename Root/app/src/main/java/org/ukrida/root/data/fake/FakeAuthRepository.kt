package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummyAuthModelsData
import org.ukrida.root.data.model.AuthData
import org.ukrida.root.data.model.User
import org.ukrida.root.utils.Resource

class FakeAuthRepository {

    suspend fun login(
        identifier: String,
        password: String
    ): Resource<AuthData> {

        delay(1000) // simulasi network delay

        val index = DummyAuthModelsData.loginRequests.indexOfFirst {
            it.identifier == identifier &&
                    it.password == password
        }

        return if (index != -1) {
            Resource.Success(
                DummyAuthModelsData.authDataList[index]
            )
        } else {
            Resource.Error("Invalid username/email or password")
        }
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String
    ): Resource<User> {

        delay(1000)

        if (password != passwordConfirmation) {
            return Resource.Error("Password confirmation does not match")
        }

        val usernameExists = DummyAuthModelsData.users.any {
            it.username.equals(username, ignoreCase = true)
        }

        if (usernameExists) {
            return Resource.Error("Username already exists")
        }

        val emailExists = DummyAuthModelsData.users.any {
            it.email.equals(email, ignoreCase = true)
        }

        if (emailExists) {
            return Resource.Error("Email already exists")
        }

        val newUser = User(
            id = DummyAuthModelsData.users.size + 1,
            username = username,
            email = email,
            firstName = firstName,
            lastName = lastName,
            profilePhotoUrl = null,
            phone = phone,
            role = "participant"
        )

        return Resource.Success(newUser)
    }
}