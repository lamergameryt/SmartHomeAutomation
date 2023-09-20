package com.lamergameryt.fdwebview.callbacks;

import org.json.JSONArray;

@SuppressWarnings("unused")
public class UpdateSettingsCallback extends CustomCallback {

    public UpdateSettingsCallback() {
        super.name = "update_settings";
    }

    @Override
    public void executeCallback(JSONArray args) {
        // TODO: Handle settings callback by updating database.
        for (int i = 0; i < args.length(); i++) System.out.println(args);
    }
}
