package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummyMemberData
import org.ukrida.root.data.model.Member
import org.ukrida.root.data.model.MemberDetail
import org.ukrida.root.utils.Resource

class FakeMemberRepository {

    suspend fun listMembers(
        groupId: Int
    ): Resource<List<Member>> {

        delay(500)

        return Resource.Success(
            DummyMemberData.members
        )
    }

    suspend fun getMemberDetail(
        userId: Int,
        groupId: Int
    ): Resource<MemberDetail> {

        delay(500)

        val member = DummyMemberData.getMemberDetailById(userId)

        return if (member != null) {
            Resource.Success(member)
        } else {
            Resource.Error("Member not found")
        }
    }
}