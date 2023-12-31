package com.lamergameryt.fdwebview.callbacks;

import com.lamergameryt.fdwebview.MainView;
import org.json.JSONArray;

@SuppressWarnings("unused")
public class LoginCallback extends CustomCallback {

    public LoginCallback() {
        super.name = "login";
    }

    @Override
    public void executeCallback(JSONArray args) {
        if (args.length() < 2) return;

        String username = args.getString(0);
        String password = args.getString(1);

        if (username.equals("admin") && password.equals("password")) {
            MainView.getWebView().url(MainView.getBaseUrl() + "/settings_page.html");
        } else {
            MainView.getWebView().eval("invalidLogin();");
        }
    }
}
