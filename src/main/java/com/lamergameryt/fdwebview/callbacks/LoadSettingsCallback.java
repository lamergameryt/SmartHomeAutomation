package com.lamergameryt.fdwebview.callbacks;

import com.lamergameryt.fdwebview.MainView;
import com.lamergameryt.fdwebview.Statics;
import com.lamergameryt.fdwebview.mysql.Models;
import java.text.MessageFormat;
import java.util.List;
import org.json.JSONArray;

@SuppressWarnings("unused")
public class LoadSettingsCallback extends CustomCallback {

    public LoadSettingsCallback() {
        super.name = "load_settings";
    }

    @Override
    public void executeCallback(JSONArray args) {
        if (args.isEmpty()) return;

        int userId = args.getInt(0);
        List<Models.Setting> settings = MainView
            .getHandler()
            .getSettingsById(Statics.GLOBAL_LOCATION, userId);

        for (Models.Setting setting : settings) {
            if (!Statics.PIN_MAPPINGS.containsKey(setting.pinId())) return;

            String mappedDevice = Statics.PIN_MAPPINGS.get(setting.pinId());
            updateElementValue(mappedDevice, setting.value());
        }
    }

    private void updateElementValue(String elementId, int value) {
        String code =
            """
                var elem = document.getElementById(''{0}'');
                var offset = parseInt(elem.getAttribute(''offset''));
                if ((elem || '{}').type === ''checkbox'')
                    elem.checked = Math.floor({1}/offset) != 0;
                else
                    elem.value = Math.floor({1}/offset).toString();
                """.strip();

        MainView.getWebView().eval(MessageFormat.format(code, elementId, value));
    }
}
