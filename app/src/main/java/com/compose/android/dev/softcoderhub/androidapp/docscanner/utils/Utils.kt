package com.compose.android.dev.softcoderhub.androidapp.docscanner.utils

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF
import com.compose.android.dev.softcoderhub.androidapp.docscanner.ui.PdfViewModel
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_BASE
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Utils {
    fun handleActivityResult(activityResult: ActivityResult,context: Context,viewModel: PdfViewModel){
        try {
            val resultCode = activityResult.resultCode
            val result = GmsDocumentScanningResult.fromActivityResultIntent(activityResult.data)

            if(resultCode == Activity.RESULT_OK && result != null){

                result.pdf?.uri?.path?.let { path->

                    val title = "Docs_${System.currentTimeMillis()}"

                    val pdfDoc = PDF(
                        id = 0,
                        title =title,
                        filepath = Uri.parse(result.pdf!!.uri.path)
                    )

                    insertPdf(pdfDoc,viewModel)

                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun insertPdf(pdfDoc: PDF,viewModel: PdfViewModel) {
        GlobalScope.launch (Dispatchers.IO){
            try {
                viewModel.insertPdf(pdfDoc)
            }catch (e:Exception){
                e.printStackTrace()
            }
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
                .addOnFailureListener{ e: Exception ->
                    e.message?.let { Log.e("error", it) }
                }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}