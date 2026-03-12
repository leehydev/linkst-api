package kr.linkst.api.link

import org.springframework.data.jpa.repository.JpaRepository

interface LinkLogRepository : JpaRepository<LinkLog, Long> {
    fun countByLinkId(linkId: Long): Long
}
