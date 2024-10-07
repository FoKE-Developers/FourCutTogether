package com.foke.together.util

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.core.content.ContextCompat.startActivity
import androidx.print.PrintHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

object BitmapUtil {
    suspend fun saveBitmap(
        graphicsLayer: GraphicsLayer,
        context: Context,
        fileName: String
    ): Uri {
        var uri : Uri = Uri.EMPTY
        val bitmap = graphicsLayer.toImageBitmap()
        bitmap.asAndroidBitmap().saveToDisk(context, fileName)
        return uri
    }

    private suspend fun Bitmap.saveToDisk(context: Context, fileName: String): Uri {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            fileName + ".jpg"
        )

        file.writeBitmap(this, Bitmap.CompressFormat.JPEG, 100)

        return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    /**
     * We call [MediaScannerConnection] to index the newly created image inside MediaStore to be visible
     * for other apps, as well as returning its [MediaStore] Uri
     */
    private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
        return suspendCancellableCoroutine { continuation ->
            MediaScannerConnection.scanFile(
                context,
                arrayOf(filePath),
                arrayOf("image/jpg")
            ) { _, scannedUri ->
                if (scannedUri == null) {
                    continuation.cancel(Exception("File $filePath could not be scanned"))
                } else {
                    continuation.resume(scannedUri)
                }
            }
        }
    }

    fun shareBitmap(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpg"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(context, createChooser(intent, "Share your image"), null)
    }

    fun printBitmap(context: Context, bitmap: Bitmap){
        // can use getActivity()
        val photoPrinter = PrintHelper(context)
        photoPrinter.printBitmap("Print", bitmap)
    }
}