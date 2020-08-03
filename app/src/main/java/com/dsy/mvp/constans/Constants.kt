package com.dsy.mvp.constans

import android.os.Environment
import com.dsy.mvp.App
import java.io.File

object Constants {
    //================= PATH ====================
    val PATH_DATA = (App.instance.cacheDir.absolutePath
            + File.separator + "data")
    val PATH_CACHE = "$PATH_DATA/NetCache"
    val PATH_SDCARD = (Environment.getExternalStorageDirectory().absolutePath
            + File.separator + "codeest" + File.separator + "GeekNews")

    //================= PREFERENCE ====================
    const val SP_NIGHT_MODE = "night_mode"
}