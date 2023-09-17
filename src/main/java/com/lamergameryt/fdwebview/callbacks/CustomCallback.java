package com.lamergameryt.fdwebview.callbacks;

import org.json.JSONArray;

public abstract class CustomCallback {

    protected String name = null;

    public abstract void executeCallback(JSONArray args);

    public String getName() {
        return name;
    }
}
