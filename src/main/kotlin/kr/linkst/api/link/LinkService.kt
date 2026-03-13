package kr.linkst.api.link

import kr.linkst.api.common.PageResponse
import kr.linkst.api.link.dto.CreateLinkRequest
import kr.linkst.api.link.dto.LinkResponse
import kr.linkst.api.link.dto.UpdateLinkRequest
import kr.linkst.api.user.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class LinkService(
    private val linkRepository: LinkRepository,
    @Value("\${app.base-url}") private val baseUrl: String
) {
    companion object {
        private const val SLUG_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        private const val SLUG_LENGTH = 6
    }

    @Transactional
    fun createLink(user: User, request: CreateLinkRequest): LinkResponse {
        val slug = generateUniqueSlug()

        val link = Link(
            user = user,
            slug = slug,
            originUrl = request.originUrl,
            title = request.title
        )

        val savedLink = linkRepository.save(link)
        return LinkResponse.from(savedLink, baseUrl)
    }

    @Transactional(readOnly = true)
    fun getMyLinks(user: User, page: Int, size: Int): PageResponse<LinkResponse> {
        val pageable = PageRequest.of(page, size)
        val linkPage = linkRepository.findByUserIdOrderByCreatedAtDesc(user.id, pageable)

        return PageResponse.from(linkPage) { LinkResponse.from(it, baseUrl) }
    }

    @Transactional(readOnly = true)
    fun getLink(user: User, publicId: UUID): LinkResponse {
        val link = linkRepository.findByPublicId(publicId)
            ?: throw NoSuchElementException("링크를 찾을 수 없습니다")

        if (link.user.id != user.id) {
            throw IllegalAccessException("접근 권한이 없습니다")
        }

        return LinkResponse.from(link, baseUrl)
    }

    @Transactional
    fun updateLink(user: User, publicId: UUID, request: UpdateLinkRequest): LinkResponse {
        val link = linkRepository.findByPublicId(publicId)
            ?: throw NoSuchElementException("링크를 찾을 수 없습니다")

        if (link.user.id != user.id) {
            throw IllegalAccessException("접근 권한이 없습니다")
        }

        request.title?.let { link.title = it }
        request.isActive?.let { link.isActive = it }

        return LinkResponse.from(link, baseUrl)
    }

    @Transactional
    fun deleteLink(user: User, publicId: UUID) {
        val link = linkRepository.findByPublicId(publicId)
            ?: throw NoSuchElementException("링크를 찾을 수 없습니다")

        if (link.user.id != user.id) {
            throw IllegalAccessException("접근 권한이 없습니다")
        }

        linkRepository.delete(link)
    }

    private fun generateUniqueSlug(): String {
        var slug: String
        do {
            slug = (1..SLUG_LENGTH)
                .map { SLUG_CHARS.random() }
                .joinToString("")
        } while (linkRepository.existsBySlug(slug))
        return slug
    }
}
