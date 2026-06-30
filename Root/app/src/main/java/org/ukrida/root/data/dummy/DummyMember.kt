package org.ukrida.root.data.dummy
import org.ukrida.root.data.model.*

object DummyMemberData {

    val members = listOf(
        Member(
            id = 1,
            username = "michaelt",
            firstName = "Michael",
            lastName = "Timothy",
            profilePhotoUrl = null,
            role = "participant"
        ),
        Member(
            id = 2,
            username = "johndoe",
            firstName = "John",
            lastName = "Doe",
            profilePhotoUrl = null,
            role = "mentor"
        ),
        Member(
            id = 3,
            username = "janesmith",
            firstName = "Jane",
            lastName = "Smith",
            profilePhotoUrl = null,
            role = "participant"
        ),
        Member(
            id = 4,
            username = "adminroot",
            firstName = "ROOT",
            lastName = "Admin",
            profilePhotoUrl = null,
            role = "admin"
        ),
        Member(
            id = 5,
            username = "alexlee",
            firstName = "Alex",
            lastName = "Lee",
            profilePhotoUrl = null,
            role = "participant"
        ),
        Member(
            id = 6,
            username = "mariaangelica",
            firstName = "Maria",
            lastName = "Angelica",
            profilePhotoUrl = null,
            role = "coordinator"
        ),
        Member(
            id = 7,
            username = "daniels",
            firstName = "Daniel",
            lastName = "Santoso",
            profilePhotoUrl = null,
            role = "mentor"
        ),

        Member(
            id = 8,
            username = "samuelw",
            firstName = "Samuel",
            lastName = "Wijaya",
            profilePhotoUrl = null,
            role = "mentor"
        ),

        Member(
            id = 9,
            username = "jonathant",
            firstName = "Jonathan",
            lastName = "Tan",
            profilePhotoUrl = null,
            role = "coordinator"
        ),

        Member(
            id = 10,
            username = "kevinh",
            firstName = "Kevin",
            lastName = "Hartono",
            profilePhotoUrl = null,
            role = "coordinator"
        )


    )

    val memberDetails = listOf(
        MemberDetail(
            id = 1,
            username = "michaelt",
            firstName = "Michael",
            lastName = "Timothy",
            email = "michael@example.com",
            phone = "081234567890",
            profilePhotoUrl = null,
            role = "participant",
            statusJoin = "approved",
            joinDate = "2025-06-15"
        ),
        MemberDetail(
            id = 2,
            username = "johndoe",
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            phone = "081298765432",
            profilePhotoUrl = null,
            role = "Mentor",
            statusJoin = "approved",
            joinDate = "2025-06-10"
        ),
        MemberDetail(
            id = 3,
            username = "janesmith",
            firstName = "Jane",
            lastName = "Smith",
            email = "jane@example.com",
            phone = "081355566677",
            profilePhotoUrl = null,
            role = "participant",
            statusJoin = "approved",
            joinDate = "2025-06-18"
        ),
        MemberDetail(
            id = 4,
            username = "adminroot",
            firstName = "ROOT",
            lastName = "Admin",
            email = "admin@example.com",
            phone = "081111111111",
            profilePhotoUrl = null,
            role = "admin",
            statusJoin = "approved",
            joinDate = "2025-06-01"
        ),
        MemberDetail(
            id = 5,
            username = "alexlee",
            firstName = "Alex",
            lastName = "Lee",
            email = "alex@example.com",
            phone = "081322233344",
            profilePhotoUrl = null,
            role = "participant",
            statusJoin = "pending",
            joinDate = "2025-06-25"
        ),
        MemberDetail(
            id = 6,
            username = "mariaangelica",
            firstName = "Maria",
            lastName = "Angelica",
            email = "maria@example.com",
            phone = "081377788899",
            profilePhotoUrl = null,
            role = "coordinator",
            statusJoin = "approved",
            joinDate = "2025-06-12"
        ),
        MemberDetail(
            id = 7,
            username = "daniels",
            firstName = "Daniel",
            lastName = "Santoso",
            email = "daniel@example.com",
            phone = "081355511122",
            profilePhotoUrl = null,
            role = "mentor",
            statusJoin = "approved",
            joinDate = "2025-06-11"
        ),

        MemberDetail(
            id = 8,
            username = "samuelw",
            firstName = "Samuel",
            lastName = "Wijaya",
            email = "samuel@example.com",
            phone = "081366677788",
            profilePhotoUrl = null,
            role = "mentor",
            statusJoin = "approved",
            joinDate = "2025-06-13"
        ),

        MemberDetail(
            id = 9,
            username = "jonathant",
            firstName = "Jonathan",
            lastName = "Tan",
            email = "jonathan@example.com",
            phone = "081377744455",
            profilePhotoUrl = null,
            role = "coordinator",
            statusJoin = "approved",
            joinDate = "2025-06-14"
        ),

        MemberDetail(
            id = 10,
            username = "kevinh",
            firstName = "Kevin",
            lastName = "Hartono",
            email = "kevin@example.com",
            phone = "081388899900",
            profilePhotoUrl = null,
            role = "coordinator",
            statusJoin = "approved",
            joinDate = "2025-06-16"
        )
    )

    val currentMember = memberDetails.first()

    fun getMemberById(id: Int): Member? =
        members.find { it.id == id }

    fun getMemberDetailById(id: Int): MemberDetail? =
        memberDetails.find { it.id == id }
}