package kr.linkst.api.link.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class CreateLinkRequest(
    @field:NotBlank(message = "URL은 필수입니다")
    @field:URL(message = "올바른 URL 형식이 아닙니다")
    val originUrl: String,

    val title: String? = null
)

data class UpdateLinkRequest(
    val title: String? = null,
    val isActive: Boolean? = null
)
