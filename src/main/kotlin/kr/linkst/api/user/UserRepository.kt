package kr.linkst.api.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByKakaoId(kakaoId: String): User?
}
