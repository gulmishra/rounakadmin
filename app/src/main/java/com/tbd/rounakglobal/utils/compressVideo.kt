package com.tbd.rounakglobal.utils


import android.content.Context
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File

fun compressVideo(inputUri: Uri, outputFilePath: String,context: Context) {
    val inputFilePath = getPathFromUri(inputUri,context) // Implement this function to get the file path from URI

    val cmd = arrayOf(
        "-i", inputFilePath,
        "-vf", "scale=640:360", // Adjust resolution
        "-b:v", "500k", // Adjust video bitrate
        "-b:a", "128k", // Adjust audio bitrate
        outputFilePath
    )

    FFmpeg.executeAsync(cmd) { executionId, returnCode ->
        if (returnCode == Config.RETURN_CODE_SUCCESS) {
            // Compression successful
        } else if (returnCode == Config.RETURN_CODE_CANCEL) {
            // Compression cancelled
        } else {
            // Compression failed
        }
    }
}

// Utility function to get file path from URI
private fun getPathFromUri(uri: Uri,context : Context): String? {
    var path: String? = null
    val projection = arrayOf(MediaStore.Video.Media.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            path = it.getString(columnIndex)
        }
    }
    return path
}