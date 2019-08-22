package com.knero.android.tools.example.event;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.knero.android.tools.blackbox.internal.BlackBoxEvent;

/**
 * @author knero
 * @since 2019-08-21
 */
public class LoginEvent extends BlackBoxEvent {
    @Override
    protected void execute() {
        boolean hasLogin = checkLogin();
        if (hasLogin) {
            success();
        } else {
            Intent intent = new Intent(getFragment().getContext(), LoginActivity.class);
            startActivityForResult(intent);
        }
    }

    private boolean checkLogin() {
        Context context = getFragment().getActivity();
        SharedPreferences preferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        return preferences.getBoolean("hasLogin", false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (checkLogin()) {
            success();
        } else {
            error(new Exception("not login"));
        }
    }
}
