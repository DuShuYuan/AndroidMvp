package com.dsy.mvp.constans;

import android.os.Environment;

import com.dsy.mvp.App;

import java.io.File;

public class Constants {


    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath()
            + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "codeest" + File.separator + "GeekNews";

    //================= PREFERENCE ====================

    public static final String SP_NIGHT_MODE = "night_mode";

}
