package com.lamergameryt.fdwebview;

import com.lamergameryt.fdwebview.mysql.DatabaseHandler;
import java.util.HashMap;

public class Statics {

    public static final DatabaseHandler.Location GLOBAL_LOCATION = DatabaseHandler.Location.KITCHEN;
    public static final HashMap<Integer, String> PIN_MAPPINGS = new HashMap<>() {
        {
            put(1, "fan");
            put(2, "speaker");
            put(3, "light");
        }
    };
}
