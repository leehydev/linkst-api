package kr.linkst.api.link

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/s")
class RedirectController(
    private val redirectService: RedirectService
) {

    @GetMapping("/{slug}")
    fun redirect(
        @PathVariable slug: String,
        request: HttpServletRequest
    ): ResponseEntity<Unit> {
        val originUrl = redirectService.getRedirectUrl(slug)

        // 비동기로 로그 저장
        redirectService.saveLog(
            slug = slug,
            referer = request.getHeader("Referer"),
            userAgent = request.getHeader("User-Agent"),
            ipAddress = getClientIp(request)
        )

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI.create(originUrl))
            .build()
    }

    private fun getClientIp(request: HttpServletRequest): String {
        val xForwardedFor = request.getHeader("X-Forwarded-For")
        return if (xForwardedFor != null) {
            xForwardedFor.split(",").first().trim()
        } else {
            request.remoteAddr
        }
    }
}
