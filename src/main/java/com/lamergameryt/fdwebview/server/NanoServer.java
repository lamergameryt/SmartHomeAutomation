package com.lamergameryt.fdwebview.server;

import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.io.InputStream;

public class NanoServer extends NanoHTTPD {

    public NanoServer(int port) throws IOException {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        InputStream stream = NanoServer.class.getResourceAsStream("/static/" + session.getUri().substring(1));
        if (session.getUri().replaceAll("/", "").isEmpty() || stream == null) {
            return newFixedLengthResponse("Invalid file entered.");
        }

        try {
            return newFixedLengthResponse(
                Response.Status.OK,
                getMimeTypeForFile(session.getUri().substring(1)),
                stream,
                stream.available()
            );
        } catch (IOException e) {
            return newFixedLengthResponse("Could not read the file data.");
        }
    }
}
