package io.express;

import java.io.File;
import java.net.URL;

public class PathUtil {
    public static File appPath;
    public static File cfgPath;
    public static File dataPath;
    public static File serverPath;
    public static File webContextPath;

    private static DefaultConfig defaultConfig = null;

    private static void init() {
        if (cfgPath != null && dataPath != null) {
            return;
        }
        try {
            File path = new File("./").getAbsoluteFile().getParentFile().getParentFile();
            serverPath = path;
            if (path.getPath().endsWith("tomcat") || path.getPath().indexOf("tomcat") != -1) {
                appPath = path.getParentFile();
            } else {
                appPath = path;
            }
            cfgPath = new File(appPath, "config/");
            dataPath = new File(appPath, "data/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取应用根目录,亦即安装目录
     *
     * @return String
     */
    public static File getAppPath() {
        init();
        return appPath;
    }

    /**
     * 取tomcat目录
     *
     * @return String
     */
    public static File getServerPath() {
        init();
        return serverPath;
    }

    /**
     * 取web应用目录
     *
     * @return String
     */
    public static File getWebContextPath() {
        if (webContextPath == null) {
            URL url = Thread.currentThread().getContextClassLoader().getResource("");
            File file = new File(url.getFile());
            webContextPath = file.getParentFile().getParentFile();
        }
        return webContextPath;
    }

    public static double getDouble(String id) {
        if (defaultConfig == null) {
            defaultConfig = new DefaultConfig(cfgPath + "/config.properties");
        }

        return defaultConfig.getDouble(id);
    }

    public static int getInteger(String id) {
        if (cfgPath == null || cfgPath.getPath().length() == 0) {
            init();
        }

        if (defaultConfig == null) {
            defaultConfig = new DefaultConfig(cfgPath + "/config.properties");
        }

        return defaultConfig.getInteger(id);
    }


    public static String getString(String id) {
        return getString(id, null);
    }

    public static String getString(String id, String deft) {
        if (cfgPath == null || cfgPath.getPath().length() == 0) {
            init();
        }

        if (defaultConfig == null) {
            defaultConfig = new DefaultConfig(cfgPath + "/config.properties");
        }

        return defaultConfig.getString(id, deft);
    }
}
