package kr.linkst.api.link

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RedirectService(
    private val linkRepository: LinkRepository,
    private val linkLogRepository: LinkLogRepository
) {

    @Transactional
    fun getRedirectUrl(slug: String): String {
        val link = linkRepository.findBySlug(slug)
            ?: throw NoSuchElementException("링크를 찾을 수 없습니다")

        if (!link.isActive) {
            throw IllegalStateException("비활성화된 링크입니다")
        }

        link.clickCount++

        return link.originUrl
    }

    @Async
    @Transactional
    fun saveLog(slug: String, referer: String?, userAgent: String?, ipAddress: String?) {
        val link = linkRepository.findBySlug(slug) ?: return

        val log = LinkLog(
            link = link,
            accessedAt = LocalDateTime.now(),
            referer = referer,
            userAgent = userAgent,
            ipAddress = ipAddress
        )

        linkLogRepository.save(log)
    }
}
