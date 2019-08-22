package com.knero.android.tools.debug.display;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

public class DebugDisplayPermissionFragment
        extends Fragment {
    public static final int REQUEST_CODE_SET_OVERLAYS_PERMISSION = 10;
    private DebugDisplayPermissionCallback mCallback;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void showGetPermissionDialog() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (Settings.canDrawOverlays(getContext())) {
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("没有展示权限")
                .setMessage("在Android6.0过后，添加了权限限制，你可以去\"设置 --> 通用 --> 应用管理 --> 更多 --> 配置应用 --> 在其他应用的上层显示 --> 选择你的APP --> 运行在其他应用的上层显示\"设置。")
                .setNegativeButton("去授权", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DebugDisplayPermissionFragment.this.setPermission();
                            }
                        }
                ).setPositiveButton("暂不授权", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (DebugDisplayPermissionFragment.this.mCallback != null) {
                            DebugDisplayPermissionFragment.this.mCallback.onResult(false);
                        }
                    }
                }).create();
        dialog.show();
    }

    private void setPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (!Settings.canDrawOverlays(getContext())) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_SET_OVERLAYS_PERMISSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SET_OVERLAYS_PERMISSION) {
            if ((Build.VERSION.SDK_INT >= 23) && (!Settings.canDrawOverlays(getContext()))) {
                if (this.mCallback != null) {
                    this.mCallback.onResult(false);
                }
            } else if (this.mCallback != null) {
                this.mCallback.onResult(true);
            }
        }
    }

    public void setCallback(DebugDisplayPermissionCallback callback) {
        this.mCallback = callback;
    }
}

