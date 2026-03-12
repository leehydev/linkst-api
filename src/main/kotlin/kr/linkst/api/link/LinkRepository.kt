package kr.linkst.api.link

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LinkRepository : JpaRepository<Link, Long> {
    fun findBySlug(slug: String): Link?
    fun findByPublicId(publicId: UUID): Link?
    fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<Link>
    fun existsBySlug(slug: String): Boolean
}
