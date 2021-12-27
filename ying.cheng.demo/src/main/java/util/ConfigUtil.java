package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigUtil {


    private static final String properiesName = "config/beta.properties";
    private static Properties prop = new Properties();
    static {
        InputStream is = null;
        try {
            is = ConfigUtil.class.getClassLoader().getResourceAsStream(properiesName);
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPropery(String key) {
        return prop.getProperty(key);
    }

}

