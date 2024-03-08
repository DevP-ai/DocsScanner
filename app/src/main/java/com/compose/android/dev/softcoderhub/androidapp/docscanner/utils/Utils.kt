package com.compose.android.dev.softcoderhub.androidapp.docscanner.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
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
}