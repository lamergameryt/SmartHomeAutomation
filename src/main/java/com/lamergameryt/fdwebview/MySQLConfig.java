package com.lamergameryt.fdwebview;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLConfig {

    @SuppressWarnings("FieldCanBeLocal")
    private final Logger logger = LoggerFactory.getLogger(MySQLConfig.class);

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQLConfig(String fileName) {
        try (InputStream stream = this.getClass().getResourceAsStream("/" + fileName)) {
            if (stream == null) throw new IOException(
                "The configuration file for MySQLConfig was not found."
            );

            JSONObject config = new JSONObject(streamToString(stream));

            this.host = config.getString("host");
            this.port = config.getInt("port");
            this.database = config.getString("db_name");
            this.username = config.getString("db_username");
            this.password = config.getString("db_password");
        } catch (JSONException e) {
            logger.error(
                "Invalid mysql.json configuration style, please use the example config file given.",
                e
            );
            System.exit(1);
        } catch (IOException e) {
            logger.error("Error loading the mysql.json config file.", e);
            System.exit(1);
        }
    }

    // https://stackoverflow.com/a/35446009
    private String streamToString(InputStream stream) throws IOException {
        int bufferSize = 1024;
        char[] buffer = new char[bufferSize];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0;) {
            out.append(buffer, 0, numRead);
        }
        return out.toString();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
