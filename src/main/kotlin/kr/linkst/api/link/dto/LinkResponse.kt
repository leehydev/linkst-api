package kr.linkst.api.link.dto

import kr.linkst.api.link.Link
import java.time.LocalDateTime
import java.util.UUID

data class LinkResponse(
    val id: UUID,
    val slug: String,
    val shortUrl: String,
    val originUrl: String,
    val title: String?,
    val isActive: Boolean,
    val clickCount: Long,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(link: Link, baseUrl: String = "https://dev.linkst.kr"): LinkResponse {
            return LinkResponse(
                id = link.publicId,
                slug = link.slug,
                shortUrl = "$baseUrl/s/${link.slug}",
                originUrl = link.originUrl,
                title = link.title,
                isActive = link.isActive,
                clickCount = link.clickCount,
                createdAt = link.createdAt
            )
        }
    }
}

