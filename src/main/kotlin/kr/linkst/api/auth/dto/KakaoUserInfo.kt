package kr.linkst.api.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfo(
    val id: Long,

    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount?
)

data class KakaoAccount(
    val profile: KakaoProfile?
)

data class KakaoProfile(
    val nickname: String?
)