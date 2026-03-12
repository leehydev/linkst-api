package kr.linkst.api.common

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val content: List<T>,
    val totalCount: Long,
    val page: Int,
    val size: Int,
    val totalPages: Int
) {
    companion object {
        fun <T : Any> from(page: Page<T>): PageResponse<T> {
            return PageResponse(
                content = page.content,
                totalCount = page.totalElements,
                page = page.number,
                size = page.size,
                totalPages = page.totalPages
            )
        }

        fun <T : Any, R> from(page: Page<T>, transform: (T) -> R): PageResponse<R> {
            return PageResponse(
                content = page.content.map(transform),
                totalCount = page.totalElements,
                page = page.number,
                size = page.size,
                totalPages = page.totalPages
            )
        }
    }
}
