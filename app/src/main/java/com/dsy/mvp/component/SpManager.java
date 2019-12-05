package com.dsy.mvp.component;

import com.blankj.utilcode.util.SPUtils;

public class SpManager {
    /**
     * 设备ID
     */
    public static String getDeviceId(){
        return SPUtils.getInstance().getString("DeviceId","");
    }
    public static void putDeviceId(String id){
        SPUtils.getInstance().put("DeviceId",id);
    }

}
