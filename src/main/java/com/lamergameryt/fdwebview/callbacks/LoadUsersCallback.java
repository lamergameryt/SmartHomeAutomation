package com.lamergameryt.fdwebview.callbacks;

import com.lamergameryt.fdwebview.MainView;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class LoadUsersCallback extends CustomCallback {

    public LoadUsersCallback() {
        super.name = "load_users";
    }

    @Override
    public void executeCallback(JSONArray args) {
        // TODO: Get list of users from database.
        String[] users = {
            "Harsh Patil",
            "Gaurav Hote",
            "Nandini Gulhane",
            "Siddhant Ghodke",
            "Ishita Ramdasi",
        };

        int id = 1;
        for (String user : users) {
            JSONObject object = new JSONObject();
            object.put("name", user);
            object.put("id", id++);
            MainView.getWebView().eval("addSetting(" + object + ");");
        }
    }
}
