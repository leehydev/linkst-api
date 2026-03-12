package kr.linkst.api.auth.controller

import kr.linkst.api.auth.dto.LoginResponse
import kr.linkst.api.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/kakao")
    fun kakaoLogin(@RequestParam code: String): ResponseEntity<LoginResponse> {
        val response = authService.loginWithKakao(code)
        return ResponseEntity.ok(response)
    }
}
