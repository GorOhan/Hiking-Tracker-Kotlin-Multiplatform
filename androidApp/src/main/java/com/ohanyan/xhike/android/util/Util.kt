package com.ohanyan.xhike.android.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun copyUriToInternalStorage(context: Context, uri: Uri): String? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val fileName = "image_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)
    val outputStream: OutputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024) // Buffer size
            var read: Int
            while (input.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
    }

    return file.absolutePath
}

fun Context.hasLocationPermission(): Boolean =
    ActivityCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

