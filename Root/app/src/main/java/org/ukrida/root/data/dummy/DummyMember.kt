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
            role = "leader"
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
            role = "leader",
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
        )
    )

    val currentMember = memberDetails.first()

    fun getMemberById(id: Int): Member? =
        members.find { it.id == id }

    fun getMemberDetailById(id: Int): MemberDetail? =
        memberDetails.find { it.id == id }
}