package com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF

@Dao
interface PdfDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPdf(pdf: PDF)

    @Query("SELECT * FROM PdfList")
    fun  getAllPdf():LiveData<List<PDF>>
}