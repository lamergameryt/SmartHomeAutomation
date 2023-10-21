package com.lamergameryt.fdwebview.server;

import com.lamergameryt.fdwebview.MainView;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class NanoServer extends NanoHTTPD {

    public NanoServer(int port) throws IOException {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        URL url;
        try {
            url = new URL(MainView.getBaseUrl() + session.getUri());
        } catch (MalformedURLException e) {
            return newFixedLengthResponse("Invalid request passed.");
        }

        String path = url.getPath();
        if (path.endsWith("/")) path = path.substring(1, path.length() - 1); else path = path.substring(1);

        InputStream stream = NanoServer.class.getResourceAsStream("/static/" + path);
        if (session.getUri().replaceAll("/", "").isEmpty() || stream == null) {
            return newFixedLengthResponse("Invalid file entered.");
        }

        try {
            return newFixedLengthResponse(
                Response.Status.OK,
                getMimeTypeForFile(path),
                stream,
                stream.available()
            );
        } catch (IOException e) {
            return newFixedLengthResponse("Could not read the file data.");
        }
    }
}
