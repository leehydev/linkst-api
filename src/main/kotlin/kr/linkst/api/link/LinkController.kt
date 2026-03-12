package kr.linkst.api.link

import jakarta.validation.Valid
import kr.linkst.api.common.PageResponse
import kr.linkst.api.link.dto.CreateLinkRequest
import kr.linkst.api.link.dto.LinkResponse
import kr.linkst.api.link.dto.UpdateLinkRequest
import kr.linkst.api.user.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/links")
class LinkController(
    private val linkService: LinkService
) {

    @PostMapping
    fun createLink(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: CreateLinkRequest
    ): ResponseEntity<LinkResponse> {
        val response = linkService.createLink(user, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getMyLinks(
        @AuthenticationPrincipal user: User,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<PageResponse<LinkResponse>> {
        val response = linkService.getMyLinks(user, page, size)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{publicId}")
    fun getLink(
        @AuthenticationPrincipal user: User,
        @PathVariable publicId: UUID
    ): ResponseEntity<LinkResponse> {
        val response = linkService.getLink(user, publicId)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{publicId}")
    fun updateLink(
        @AuthenticationPrincipal user: User,
        @PathVariable publicId: UUID,
        @Valid @RequestBody request: UpdateLinkRequest
    ): ResponseEntity<LinkResponse> {
        val response = linkService.updateLink(user, publicId, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{publicId}")
    fun deleteLink(
        @AuthenticationPrincipal user: User,
        @PathVariable publicId: UUID
    ): ResponseEntity<Unit> {
        linkService.deleteLink(user, publicId)
        return ResponseEntity.noContent().build()
    }
}
