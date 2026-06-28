package org.ukrida.root.data.dummy
import org.ukrida.root.data.model.*

object DummyAuthModelsData {

    val users = listOf(
        User(
            id = 1,
            username = "michaelt",
            email = "michael@example.com",
            firstName = "Michael",
            lastName = "Timothy",
            profilePhotoUrl = null,
            phone = "081234567890",
            role = "participant"
        ),
        User(
            id = 2,
            username = "johndoe",
            email = "john@example.com",
            firstName = "John",
            lastName = "Doe",
            profilePhotoUrl = null,
            phone = "081298765432",
            role = "leader"
        ),
        User(
            id = 3,
            username = "janesmith",
            email = "jane@example.com",
            firstName = "Jane",
            lastName = "Smith",
            profilePhotoUrl = null,
            phone = "081355566677",
            role = "participant"
        ),
        User(
            id = 4,
            username = "adminroot",
            email = "admin@example.com",
            firstName = "ROOT",
            lastName = "Admin",
            profilePhotoUrl = null,
            phone = "081111111111",
            role = "admin"
        ),
        User(
            id = 5,
            username = "alexlee",
            email = "alex@example.com",
            firstName = "Alex",
            lastName = "Lee",
            profilePhotoUrl = null,
            phone = "081322233344",
            role = "participant"
        )
    )

    val authDataList = users.mapIndexed { index, user ->
        AuthData(
            token = "dummy_token_${index + 1}",
            user = user
        )
    }

    val loginRequests = listOf(
        LoginRequest(
            identifier = "michaelt",
            password = "password123"
        ),
        LoginRequest(
            identifier = "johndoe",
            password = "password123"
        ),
        LoginRequest(
            identifier = "janesmith",
            password = "password123"
        ),
        LoginRequest(
            identifier = "adminroot",
            password = "admin123"
        ),
        LoginRequest(
            identifier = "alexlee",
            password = "password123"
        )
    )

    val registerRequests = listOf(
        RegisterRequest(
            firstName = "Michael",
            lastName = "Timothy",
            username = "michaelt",
            email = "michael@example.com",
            phone = "081234567890",
            password = "password123",
            passwordConfirmation = "password123"
        ),
        RegisterRequest(
            firstName = "John",
            lastName = "Doe",
            username = "johndoe",
            email = "john@example.com",
            phone = "081298765432",
            password = "password123",
            passwordConfirmation = "password123"
        ),
        RegisterRequest(
            firstName = "Jane",
            lastName = "Smith",
            username = "janesmith",
            email = "jane@example.com",
            phone = "081355566677",
            password = "password123",
            passwordConfirmation = "password123"
        ),
        RegisterRequest(
            firstName = "ROOT",
            lastName = "Admin",
            username = "adminroot",
            email = "admin@example.com",
            phone = "081111111111",
            password = "admin123",
            passwordConfirmation = "admin123"
        ),
        RegisterRequest(
            firstName = "Alex",
            lastName = "Lee",
            username = "alexlee",
            email = "alex@example.com",
            phone = "081322233344",
            password = "password123",
            passwordConfirmation = "password123"
        )
    )

    val authResponses = authDataList.map {
        ApiResponse(
            success = true,
            data = it,
            message = "Login berhasil"
        )
    }
}