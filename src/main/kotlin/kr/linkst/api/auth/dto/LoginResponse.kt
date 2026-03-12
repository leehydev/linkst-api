package kr.linkst.api.auth.dto

data class LoginResponse(
    val accessToken: String,
    val userId: String
)