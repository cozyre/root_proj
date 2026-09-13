package org.ukrida.root.data.remote

import org.ukrida.root.BuildConfig

object ApiUrl {
    fun normalize(url: String?): String? {
        if (url.isNullOrBlank()) return null

        val base = BuildConfig.API_BASE_URL.removeSuffix("/")
        val path = url.substringAfter("://", "").substringAfter("/", "")
        return "$base/${path.removePrefix("/")}"
    }
}