package com.dsy.mvp.component;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dsy.mvp.R;

/**
 * 权限获取工具类
 */
public abstract class PermissionsRequest {


    private static final int PERMISSION_REQUEST_CODE = 0;
    private Activity activity;
    private boolean isRequireCheck = true; // 是否需要系统权限检测, 防止和系统提示框重叠

    public PermissionsRequest(Activity activity) {
        this.activity = activity;
    }

    // 判断是否缺少权限
    private boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission) ==
                    PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }

    public void requestPermissions(String... permissions) {
        if (isRequireCheck) {
            if (lacksPermissions(permissions)) {
                // 请求权限兼容低版本
                ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
            } else {
                // 全部权限都已获取
                onPermissionsGranted();
            }
        } else {
            isRequireCheck = true;
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            onPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPermissionsDenied();
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 启动应用的设置
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        });

        builder.setCancelable(false);

        builder.show();
    }


    /**
     * 有权限
     */
    protected abstract  void onPermissionsGranted();

    /**
     * 无权限
     */
    protected abstract  void onPermissionsDenied();

}
