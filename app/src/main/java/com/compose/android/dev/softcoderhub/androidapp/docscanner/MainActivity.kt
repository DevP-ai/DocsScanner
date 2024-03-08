package com.compose.android.dev.softcoderhub.androidapp.docscanner

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.compose.android.dev.softcoderhub.androidapp.docscanner.databinding.ActivityMainBinding
import com.compose.android.dev.softcoderhub.androidapp.docscanner.utils.Utils.handleActivityResult
import com.compose.android.dev.softcoderhub.androidapp.docscanner.utils.Utils.onScanButtonClick

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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


        scannerLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){result->
            handleActivityResult(result,this)
        }

        binding.btnScan.setOnClickListener {
            onScanButtonClick(this,scannerLauncher)
        }
    }
}