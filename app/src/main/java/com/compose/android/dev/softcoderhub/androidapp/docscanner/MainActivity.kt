package com.compose.android.dev.softcoderhub.androidapp.docscanner

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.compose.android.dev.softcoderhub.androidapp.docscanner.adapter.PdfAdapter
import com.compose.android.dev.softcoderhub.androidapp.docscanner.databinding.ActivityMainBinding
import com.compose.android.dev.softcoderhub.androidapp.docscanner.ui.PdfViewModel
import com.compose.android.dev.softcoderhub.androidapp.docscanner.utils.Utils.handleActivityResult
import com.compose.android.dev.softcoderhub.androidapp.docscanner.utils.Utils.onScanButtonClick

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var pdfViewModel: PdfViewModel

    private lateinit var  pdfAdapter: PdfAdapter

    private lateinit var scannerLauncher : ActivityResultLauncher<IntentSenderRequest>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }



        pdfViewModel = ViewModelProvider(this)[PdfViewModel::class.java]

        prepareRecyclerView()
        observeData()

        scannerLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){result->
            handleActivityResult(result,this,pdfViewModel)
        }

        binding.btnScan.setOnClickListener {
            onScanButtonClick(this,scannerLauncher)
        }

    }

    private fun observeData() {
        pdfViewModel.allPdf.observe(this, Observer {pdfList->
            pdfAdapter.setData(pdfList)
        })
    }

    private fun prepareRecyclerView() {
        pdfAdapter = PdfAdapter()
        binding.pdfRecyclerView.apply {
            this.layoutManager=LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = pdfAdapter
        }
    }
}