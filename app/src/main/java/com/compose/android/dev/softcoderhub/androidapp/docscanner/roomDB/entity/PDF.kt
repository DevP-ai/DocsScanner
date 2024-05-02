package com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PdfList")
data class PDF(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String?="",
    val filepath :String?=""
)
