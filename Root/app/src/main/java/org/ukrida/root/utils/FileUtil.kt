package org.ukrida.root.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtil {
    fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver ?: return null
        val filePath = context.cacheDir.absolutePath + File.separator + "temp_image_" + System.currentTimeMillis() + ".jpg"
        val file = File(filePath)

        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream?.read(buffer).also { len = it ?: -1 } != -1) {
                outputStream.write(buffer, 0, len)
            }
            outputStream.flush()
            outputStream.close()
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }
}
