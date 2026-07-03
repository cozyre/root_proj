package org.ukrida.root.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File
import java.util.UUID

fun uriToTempFile(
    context: Context,
    uri: Uri
): File {
    val contentResolver = context.contentResolver

    val mimeType = contentResolver.getType(uri)

    val extension =
        MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(mimeType)
            ?: "jpg"

    val inputStream =
        contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open selected image")

    val file =
        File(
            context.cacheDir,
            "ongoing_trip_${UUID.randomUUID()}.$extension"
        )

    file.outputStream().use { output ->
        inputStream.copyTo(output)
    }

    return file
}