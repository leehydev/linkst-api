package kr.linkst.api.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.linkst.api.common.BaseEntity

@Entity
@Table(name="users")
class User (
    @Column(name = "kakao_id", nullable = false, unique = true)
    val kakaoId: String
): BaseEntity()
