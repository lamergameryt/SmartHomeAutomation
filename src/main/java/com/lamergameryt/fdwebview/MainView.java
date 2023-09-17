package com.lamergameryt.fdwebview;

import ca.weblite.webview.WebView;
import com.lamergameryt.fdwebview.callbacks.CustomCallback;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONArray;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainView {

    private static WebView webView;
    private static NanoServer server;
    private static final Logger logger = LoggerFactory.getLogger(MainView.class);

    public static void main(String[] args) {
        try {
            server = new NanoServer(0);
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException e) {
            logger.error("Failed to start the server, exiting the program.", e);
            return;
        }

        logger.info(getBaseUrl());
        webView =
            new WebView()
                .url(getBaseUrl() + "/index.html")
                .size(800, 550)
                .resizable(true)
                .title("Smart Home Automation");

        Reflections reflections = new Reflections("com.lamergameryt.fdwebview.callbacks");
        reflections
            .getSubTypesOf(CustomCallback.class)
            .forEach(clazz -> {
                try {
                    CustomCallback callback = clazz.getDeclaredConstructor().newInstance();
                    if (callback.getName() == null) {
                        logger.error("Callback name not set, skipping callback: " + clazz.getName());
                        return;
                    }

                    webView.addJavascriptCallback(
                        callback.getName(),
                        c -> callback.executeCallback(new JSONArray(c))
                    );
                } catch (
                    InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException
                    | NoSuchMethodException e
                ) {
                    throw new RuntimeException(e);
                }
            });

        webView.show(true);
    }

    public static WebView getWebView() {
        return webView;
    }

    public static String getBaseUrl() {
        return "http://localhost:" + server.getListeningPort();
    }
}
