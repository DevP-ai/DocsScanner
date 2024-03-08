package com.compose.android.dev.softcoderhub.androidapp.docscanner.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.core.content.FileProvider
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_BASE
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import java.io.File

object Utils {

    fun handleActivityResult(activityResult: ActivityResult,context: Context){
        try {
            val resultCode = activityResult.resultCode
            val result = GmsDocumentScanningResult.fromActivityResultIntent(activityResult.data)

            if(resultCode == Activity.RESULT_OK && result != null){
                result.pages?.let { pages ->
                    for (page in pages){
                        val imageUri = pages[0].imageUri
                    }
                }

                result.pdf?.uri?.path?.let { path->
                    val externalUri = FileProvider.getUriForFile(context, context.packageName + ".provider", File(path))
                    Toast.makeText(context,externalUri.path.toString(),Toast.LENGTH_SHORT).show()
//                    val shareIntent =
//                        Intent(Intent.ACTION_SEND).apply {
//                            putExtra(Intent.EXTRA_STREAM, externalUri)
//                            type = "application/pdf"
//                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                        }
//                    startActivity(Intent.createChooser(shareIntent, "share pdf"))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    fun onScanButtonClick(activity: Activity,scannerLauncher : ActivityResultLauncher<IntentSenderRequest>){
        try {
            val options = GmsDocumentScannerOptions.Builder()
                .setScannerMode(SCANNER_MODE_BASE)
                .setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
                .setGalleryImportAllowed(true)
                .setScannerMode(SCANNER_MODE_FULL)
                .setPageLimit(10)

            GmsDocumentScanning.getClient(options.build())
                .getStartScanIntent(activity)
                .addOnSuccessListener { intentSender: IntentSender ->
                    scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
                }
                .addOnFailureListener() { e: Exception ->
                    e.message?.let { Log.e("error", it) }
                }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}