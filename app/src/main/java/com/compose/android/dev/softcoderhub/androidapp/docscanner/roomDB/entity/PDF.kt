package com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "PdfList")
@TypeConverters(Converters::class)
data class PDF(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String?="",
    val filepath: Uri?=null
)
