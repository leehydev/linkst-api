package kr.linkst.api.link

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "link_logs",
    indexes = [Index(name = "idx_link_logs_link_id_accessed_at", columnList = "link_id, accessed_at")]
)
class LinkLog(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id", nullable = false)
    val link: Link,

    @Column(name = "accessed_at", nullable = false)
    val accessedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "referer", nullable = true)
    val referer: String? = null,

    @Column(name = "user_agent", nullable = true, columnDefinition = "TEXT")
    val userAgent: String? = null,

    @Column(name = "ip_address", nullable = true, length = 45)
    val ipAddress: String? = null,

    @Column(name = "country", nullable = true, length = 2)
    val country: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
}
