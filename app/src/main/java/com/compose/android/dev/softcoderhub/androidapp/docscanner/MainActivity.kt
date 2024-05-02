package com.compose.android.dev.softcoderhub.androidapp.docscanner

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.compose.android.dev.softcoderhub.androidapp.docscanner.adapter.PdfAdapter
import com.compose.android.dev.softcoderhub.androidapp.docscanner.databinding.ActivityMainBinding
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF
import com.compose.android.dev.softcoderhub.androidapp.docscanner.ui.PdfViewModel
import com.compose.android.dev.softcoderhub.androidapp.docscanner.utils.Utils.handleActivityResult
import com.compose.android.dev.softcoderhub.androidapp.docscanner.utils.Utils.onScanButtonClick
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var pdfViewModel: PdfViewModel

    private lateinit var pdfAdapter: PdfAdapter

    private lateinit var scannerLauncher: ActivityResultLauncher<IntentSenderRequest>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)

        pdfViewModel = ViewModelProvider(this)[PdfViewModel::class.java]
        prepareRecyclerView()
        observeData()

        scannerLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                handleActivityResult(result, this, pdfViewModel)
            }

        binding.btnScan.setOnClickListener {
            onScanButtonClick(this, scannerLauncher)
        }

        onClickPdf()

        onDotClick()
    }

    private fun onDotClick() {
        pdfAdapter.onDotClick = {pdf->
            showOptionsDialog(pdf)
        }
    }


    private fun showOptionsDialog(pdf: PDF){
        val options = arrayOf("Rename","Share","Delete")
        AlertDialog.Builder(this)
            .setTitle(pdf.title)
            .setItems(options) { dialog, which: Int ->
                when (which) {
                    0->updateTitle(pdf)
                    1 -> sharePdf(pdf)
                    2 -> deletePdf(pdf)
                }
                dialog.dismiss()
            }
            .show()

    }

    private fun updateTitle(pdf: PDF) {
        showRenameDialog(pdf)
    }

    private fun showRenameDialog(pdf: PDF) {
        val inputField = EditText(this)
        inputField.setText(pdf.title)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Rename PDF")
            .setView(inputField)
            .setPositiveButton("Rename") { dialog, _ ->
                val newTitle = inputField.text.toString()
                if (newTitle.isNotEmpty() && newTitle != pdf.title) {
                    val updatedPdf = pdf.copy(title = newTitle)
                    pdfViewModel.updateTitle(updatedPdf)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }


    private fun sharePdf(pdf: PDF) {
        val path = pdf.filepath!!.path
        val externalUri = FileProvider.getUriForFile(this, packageName + ".provider", File(path!!))
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, externalUri)
                type = "application/pdf"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        startActivity(Intent.createChooser(shareIntent, "share pdf"))
    }

    private fun deletePdf(pdf: PDF) {
        pdfViewModel.deletePdf(pdf)
    }


    private fun onClickPdf() {
        pdfAdapter.onItemClick = {
            val uri = it.filepath.toString()
            val intent = Intent(this, PdfViewActivity::class.java)
            intent.putExtra("link", uri)
            intent.putExtra("title",it.title)
            intent.putExtra("id",it.id)
            startActivity(intent)
        }
    }


    private fun observeData() {
        pdfViewModel.allPdf.observe(this, Observer { pdfList ->
            val reverseList = pdfList.reversed()
            pdfAdapter.setData(reverseList)
        })
    }

    private fun prepareRecyclerView() {
        pdfAdapter = PdfAdapter()
        binding.pdfRecyclerView.apply {
            this.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = pdfAdapter
        }
    }
}