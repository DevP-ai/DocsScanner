package com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF

@Dao
interface PdfDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPdf(pdf: PDF)

    @Query("SELECT * FROM PdfList")
    fun  getAllPdf():LiveData<List<PDF>>

    @Delete
    suspend fun deletePdf(pdf: PDF)

    @Update
    suspend fun updateTitle(pdf: PDF)
}