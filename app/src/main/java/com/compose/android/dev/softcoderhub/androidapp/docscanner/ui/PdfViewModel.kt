package com.compose.android.dev.softcoderhub.androidapp.docscanner.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.db.PdfDatabase
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PdfViewModel(application: Application):AndroidViewModel(application) {
    private val pdfRepository:PdfRepository

    val allPdf: LiveData<List<PDF>>
        init {
            val pdfDao = PdfDatabase.getInstance(application).pdfDao()
            pdfRepository = PdfRepository(pdfDao)
            allPdf = pdfRepository.getAllPdf()
        }

    suspend fun insertPdf(pdf: PDF) = viewModelScope.launch(Dispatchers.IO) {
        pdfRepository.insertPdf(pdf)
    }

    fun deletePdf(pdf: PDF){
        viewModelScope.launch (Dispatchers.IO){
            pdfRepository.deletePdf(pdf)
        }
    }

    fun updateTitle(pdf: PDF){
        viewModelScope.launch (Dispatchers.IO){
            pdfRepository.updateTitle(pdf)
        }
    }

}