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
                if ((elem || '{}').type === ''checkbox'')
                    elem.checked = {1} != 0;
                else
                    elem.value = ''{1}'';
                """.strip();

        MainView.getWebView().eval(MessageFormat.format(code, elementId, value));
    }
}
