package com.lamergameryt.fdwebview;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Adapted from <a href="https://stackoverflow.com/a/22462785">StackOverFlow</a>
 * Returns all classes present in the specified packageName as an ArrayList.
 */
public class Reflections {

    private final String packageName;

    public Reflections(String packageName) {
        this.packageName = packageName;
    }

    private void checkDirectory(File directory, String packageName, ArrayList<Class<?>> classes)
        throws ClassNotFoundException {
        File tmpDirectory;

        if (directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();

            assert files != null;
            for (String file : files) {
                if (file.endsWith(".class")) {
                    try {
                        classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6)));
                    } catch (NoClassDefFoundError e) {
                        // do nothing. this class hasn't been found by the
                        // loader, and we don't care.
                    }
                } else if ((tmpDirectory = new File(directory, file)).isDirectory()) {
                    checkDirectory(tmpDirectory, packageName + "." + file, classes);
                }
            }
        }
    }

    private void checkJarFile(JarURLConnection connection, ArrayList<Class<?>> classes)
        throws ClassNotFoundException, IOException {
        JarFile jarFile = connection.getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        String name;

        for (JarEntry jarEntry; entries.hasMoreElements() && ((jarEntry = entries.nextElement()) != null);) {
            name = jarEntry.getName();

            if (name.contains(".class")) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.contains(packageName)) {
                    classes.add(Class.forName(name));
                }
            }
        }
    }

    public ArrayList<Class<?>> getClassesForPackage() {
        ArrayList<Class<?>> classes = new ArrayList<>();

        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();

            if (cld == null) return classes;

            Enumeration<URL> resources = cld.getResources(packageName.replace('.', '/'));
            URLConnection connection;

            for (URL url; resources.hasMoreElements() && ((url = resources.nextElement()) != null);) {
                try {
                    connection = url.openConnection();

                    if (connection instanceof JarURLConnection) {
                        checkJarFile((JarURLConnection) connection, classes);
                    } else if ("file".equals(url.getProtocol())) {
                        checkDirectory(
                            new File(URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8)),
                            packageName,
                            classes
                        );
                    } else return classes;
                } catch (IOException | ClassNotFoundException ioex) {
                    return classes;
                }
            }
        } catch (NullPointerException | IOException ex) {
            return classes;
        }

        return classes;
    }
}
