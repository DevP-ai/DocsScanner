package com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity.PDF

@Database(entities = [PDF::class], version = 1)
abstract class PdfDatabase :RoomDatabase(){
    abstract fun pdfDao():PdfDao

    companion object{
        @Volatile
        var INSTANCE : PdfDatabase?=null

        @Synchronized
        fun getInstance(context: Context):PdfDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,
                    PdfDatabase::class.java,
                    "PdfList")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return  INSTANCE as PdfDatabase
        }
    }
}