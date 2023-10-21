package com.lamergameryt.fdwebview.callbacks;

import com.lamergameryt.fdwebview.MainView;
import com.lamergameryt.fdwebview.Statics;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class UpdateSettingsCallback extends CustomCallback {

    public UpdateSettingsCallback() {
        super.name = "update_settings";
    }

    @Override
    public void executeCallback(JSONArray args) {
        JSONObject settings = args.getJSONObject(0);
        int userId = settings.getInt("userId");

        for (String device : settings.keySet()) {
            Optional<Integer> pinId = Statics.PIN_MAPPINGS
                .entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), device))
                .map(Map.Entry::getKey)
                .findFirst();
            if (pinId.isEmpty()) return;

            Object value = settings.get(device);
            int integerValue;
            if (value instanceof Boolean) integerValue = ((Boolean) value) ? 1 : 0; else if (
                value instanceof Integer
            ) integerValue = (Integer) value; else integerValue = Integer.parseInt((String) value);

            MainView
                .getHandler()
                .updateSettingValue(Statics.GLOBAL_LOCATION, userId, pinId.get(), integerValue);
        }
    }
}
