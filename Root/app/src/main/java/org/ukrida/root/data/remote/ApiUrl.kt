package org.ukrida.root.data.remote

import org.ukrida.root.BuildConfig

object ApiUrl {
    fun normalize(url: String?): String? {
        if (url.isNullOrBlank()) return null

        val value = url.trim()
        val isLocal = value.contains("localhost", ignoreCase = true) ||
            value.contains("127.0.0.1", ignoreCase = true) ||
            value.contains("10.0.2.2", ignoreCase = true)

        if (value.startsWith("https://", ignoreCase = true) && !isLocal) {
            return value
        }

        val path = value.substringAfter("://", value).substringAfter("/", value)
            .removePrefix("root_proj/root_api/public/")
            .removePrefix("root_api/public/")
            .removePrefix("public/")
            .removePrefix("/")
        return "${BuildConfig.API_BASE_URL.removeSuffix("/")}/$path"
    }
}