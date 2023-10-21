package com.lamergameryt.fdwebview.callbacks;

import com.lamergameryt.fdwebview.MainView;
import com.lamergameryt.fdwebview.mysql.Models;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class LoadUsersCallback extends CustomCallback {

    public LoadUsersCallback() {
        super.name = "load_users";
    }

    @Override
    public void executeCallback(JSONArray args) {
        List<Models.User> users = MainView.getHandler().getAllUsers();
        for (Models.User user : users) {
            JSONObject object = new JSONObject();
            object.put("name", user.name());
            object.put("id", user.id());
            MainView.getWebView().eval("addSetting(" + object + ");");
        }
    }
}
