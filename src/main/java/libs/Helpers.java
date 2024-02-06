/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rene
 */
public class Helpers {

    private static Properties config;

    public static Properties getConfig() {
        if (config == null) {
            try {
                InputStream is = Helpers.class.getResourceAsStream("/config.properties");
                config = new Properties();
                config.load(is);
            } catch (IOException ex) {
                throw new RuntimeException("Configuration non lisible", ex);
            }
        }
        return config;
    }
}
