package com.knero.android.tools.example;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.knero.android.tools.blackbox.Action;
import com.knero.android.tools.blackbox.BlackBox;
import com.knero.android.tools.blackbox.ErrorAction;
import com.knero.android.tools.blackbox.internal.PermissionEvent;
import com.knero.android.tools.example.event.LoginEvent;
import com.knero.android.tools.file.browser.FileBrowser;
import com.knero.android.tools.file.browser.filter.Filter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BlackBox.of(this).with(new PermissionEvent(Manifest.permission.READ_PHONE_STATE)).execute(new Action() {
            @Override
            public void call() {
                
            }
        });
    }

    public void checkLogin(View view) {
        BlackBox.of(this).with(new LoginEvent()).execute(new Action() {
            @Override
            public void call() {
                Toast.makeText(MainActivity.this, "already login", Toast.LENGTH_SHORT).show();
            }
        }, new ErrorAction() {
            @Override
            public boolean call(Throwable throwable) {
                Toast.makeText(MainActivity.this, "pleas login first", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void requestPermission(View view) {
        BlackBox.of(this)
                .with(new LoginEvent(), new ErrorAction() {
                    @Override
                    public boolean call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                })
                .with(new PermissionEvent(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
                .execute(new Action() {
                    @Override
                    public void call() {
                        Toast.makeText(MainActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                    }
                }, new ErrorAction() {
                    @Override
                    public boolean call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "获取权限失败，请重试", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    public void browserFile(View view) {
        FileBrowser.of(this)
                .filter(new Filter() {
                    @Override
                    public boolean filter(String name) {
                        return !name.endsWith(".zip") && !name.endsWith(".txt");
                    }
                })
                .selectCount(10)
                .start(new FileBrowser.OnFileListener() {
                    @Override
                    public void onSuccess(List<String> files) {
                        Toast.makeText(MainActivity.this, files.get(0), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
