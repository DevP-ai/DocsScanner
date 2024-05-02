package com.compose.android.dev.softcoderhub.androidapp.docscanner

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.compose.android.dev.softcoderhub.androidapp.docscanner.databinding.ActivityPdfViewBinding
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF
import com.compose.android.dev.softcoderhub.androidapp.docscanner.ui.PdfViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import java.io.File

class PdfViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfViewBinding
    private var progressDialog: ProgressDialog? = null
    private var sharePath:String?=""
    private var id:Int =0
    private lateinit var pdfViewModel: PdfViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdfViewModel = ViewModelProvider(this)[PdfViewModel::class.java]


        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading PDF...")
            setCancelable(false)
        }


        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.smartBannerAd.loadAd(adRequest)



        binding.toolMenu.setOnClickListener {
            showPopUp(it)
        }

        val filepath = intent.getStringExtra("link")
        val file = filepath?.let {
            File(it)
        }
        val title = intent.getStringExtra("title")
        id = intent.getIntExtra("id",0)

        binding.pdfView.fromFile(File(file!!.toURI().path))
            .defaultPage(0)
            .spacing(12)
            .enableAnnotationRendering(false)
            .enableDoubletap(true)
            .onLoad {
                progressDialog?.dismiss()
            }
            .load()



        binding.pdfView.useBestQuality(true)
        binding.pdfView.fitToWidth(0)

        binding.toolbar.title = title.toString()

        binding.btnShare.setOnClickListener {
            sharePdf(file.toURI().path)
        }
        sharePath = file.toURI().path
    }



    private fun showPopUp(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.toolbar_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            onOptionsItemSelected(menuItem)
        }
        popupMenu.show()
    }

    private fun sharePdf(path: String?) {
        val externalUri = FileProvider.getUriForFile(this, packageName + ".provider", File(path!!))
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, externalUri)
                type = "application/pdf"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        startActivity(Intent.createChooser(shareIntent, "share pdf"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.privacy ->{
                val policyUrl = "https://doc-hosting.flycricket.io/docs-scanner/feb58ab1-2f4b-4644-9b8c-d5f587eb6bb5/privacy"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(policyUrl))
                startActivity(browserIntent)
                return true
            }
            R.id.pdfShare ->{
                val externalUri = FileProvider.getUriForFile(this, packageName + ".provider", File(sharePath!!))
                val shareIntent =
                    Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_STREAM, externalUri)
                        type = "application/pdf"
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                startActivity(Intent.createChooser(shareIntent, "share pdf"))
                return true
            }
            else -> return  super.onOptionsItemSelected(item)
        }
    }


}



