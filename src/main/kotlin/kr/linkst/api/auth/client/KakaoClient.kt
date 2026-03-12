package kr.linkst.api.auth.client

import kr.linkst.api.auth.dto.KakaoTokenResponse
import kr.linkst.api.auth.dto.KakaoUserInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoClient(
    @Value($$"${kakao.client-id}")
    private val clientId: String,

    @Value($$"${kakao.client-secret}")
    private val clientSecret: String,

    @Value($$"${kakao.redirect-uri}")
    private val redirectUri: String
) {
    private val authClient = WebClient.builder()
        .baseUrl("https://kauth.kakao.com")
        .build()

    private val apiClient = WebClient.builder()
        .baseUrl("https://kapi.kakao.com")
        .build()

    fun getAccessToken(code: String): KakaoTokenResponse {
        return authClient.post()
            .uri("/oauth/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(
                "grant_type=authorization_code" +
                        "&client_id=$clientId" +
                        "&redirect_uri=$redirectUri" +
                        "&client_secret=$clientSecret" +
                        "&code=$code"
            )
            .retrieve()
            .bodyToMono(KakaoTokenResponse::class.java)
            .block() ?: throw RuntimeException("Failed to get Kakao access token")
    }

    fun getUserInfo(accessToken: String): KakaoUserInfo {
        return apiClient.get()
            .uri("/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(KakaoUserInfo::class.java)
            .block() ?: throw RuntimeException("Failed to get Kakao user info")
    }
}