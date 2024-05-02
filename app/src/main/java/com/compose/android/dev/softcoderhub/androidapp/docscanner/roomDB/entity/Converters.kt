package com.compose.android.dev.softcoderhub.androidapp.docscanner.roomDB.entity

import android.net.Uri
import androidx.room.TypeConverter

object Converters {
    @JvmStatic
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @JvmStatic
    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}
