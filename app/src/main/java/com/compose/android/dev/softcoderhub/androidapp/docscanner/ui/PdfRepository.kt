package com.compose.android.dev.softcoderhub.androidapp.docscanner.ui

import androidx.lifecycle.LiveData
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.db.PdfDao
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF

class PdfRepository(private val pdfDao: PdfDao) {

    suspend fun insertPdf(pdf: PDF){
        pdfDao.insertPdf(pdf)
    }

    fun  getAllPdf():LiveData<List<PDF>>{
        return pdfDao.getAllPdf()
    }

    suspend fun deletePdf(pdf: PDF){
        pdfDao.deletePdf(pdf)
    }

    suspend fun  updateTitle(pdf: PDF){
        pdfDao.updateTitle(pdf)
    }
}