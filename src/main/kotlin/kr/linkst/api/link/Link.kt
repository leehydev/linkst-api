package kr.linkst.api.link

import jakarta.persistence.*
import kr.linkst.api.common.BaseEntity
import kr.linkst.api.user.User

@Entity
@Table(name = "links")
class Link(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "slug", nullable = false, unique = true, length = 10)
    val slug: String,

    @Column(name = "origin_url", nullable = false, columnDefinition = "TEXT")
    var originUrl: String,

    @Column(name = "title", nullable = true)
    var title: String? = null,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "click_count", nullable = false)
    var clickCount: Long = 0L
) : BaseEntity()
