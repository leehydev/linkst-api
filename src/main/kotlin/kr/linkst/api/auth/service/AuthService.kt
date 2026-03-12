package kr.linkst.api.auth.service

import kr.linkst.api.auth.client.KakaoClient
import kr.linkst.api.auth.dto.LoginResponse
import kr.linkst.api.auth.jwt.JwtProvider
import kr.linkst.api.user.User
import kr.linkst.api.user.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val kakaoClient: KakaoClient,
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository
) {
    fun loginWithKakao(code: String): LoginResponse {
        // 1. 인가 코드로 카카오 액세스 토큰 요청
        val tokenResponse = kakaoClient.getAccessToken(code)

        // 2. 액세스 토큰으로 사용자 정보 조회
        val userInfo = kakaoClient.getUserInfo(tokenResponse.accessToken)
        val kakaoId = userInfo.id.toString()

        // 3. 기존 유저 조회 또는 새로 생성
        val user = userRepository.findByKakaoId(kakaoId) ?: userRepository.save(User(kakaoId = kakaoId))

        // 4. JWT 토큰 발급
        val accessToken = jwtProvider.createToken(user.publicId.toString())

        return LoginResponse(
            accessToken = accessToken, userId = user.publicId.toString()
        )
    }
}
