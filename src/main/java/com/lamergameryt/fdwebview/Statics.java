package com.lamergameryt.fdwebview;

import com.lamergameryt.fdwebview.mysql.DatabaseHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    private static File faceMappingScript = null;

    public static File getFaceMappingScript() {
        if (faceMappingScript != null) return faceMappingScript;

        try (InputStream in = Statics.class.getResourceAsStream("/face_mapping.py")) {
            if (in == null) return null;

            File tempFile = Files.createTempFile("fms", ".py").toFile();
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                for (int bytesRead; (bytesRead = in.read(buffer)) != -1;) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            faceMappingScript = tempFile;
            return faceMappingScript;
        } catch (IOException ex) {
            return null;
        }
    }

    // https://stackoverflow.com/a/35446009
    // Fastest performance when converting InputStream to String.
    public static String streamToString(InputStream stream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = stream.read(buffer)) != -1;) {
            result.write(buffer, 0, length);
        }

        return result.toString(StandardCharsets.UTF_8);
    }
}
