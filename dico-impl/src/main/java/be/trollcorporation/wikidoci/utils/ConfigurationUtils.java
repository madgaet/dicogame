package be.trollcorporation.wikidoci.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by madgaet on 19-06-16.
 */
public final class ConfigurationUtils {

	public static final String DEFAULT_PATH = "application.properties";
	private static final String RESOURCES_PATH = ClassLoader.getSystemResource(DEFAULT_PATH).toString().substring(5);
    private static final Logger LOG = Logger.getLogger(ConfigurationUtils.class.getName());

    private Properties props;
    private static ConfigurationUtils singleton;

    private ConfigurationUtils() {
        props = new Properties();
        try {
			props.load(Files.newInputStream(new File(RESOURCES_PATH).toPath(), StandardOpenOption.READ));
		} catch (IOException e) {
			LOG.severe("Error while loading configuration data.");
		}
    }

    private static ConfigurationUtils getInstance() {
    	if (singleton == null) {
    		synchronized (ConfigurationUtils.class) {
				if (singleton == null) {
					singleton = new ConfigurationUtils();
				}
			}
    	}
        return singleton;
    }

    public static String getProperty(final String key, final String defaultValue) {
        return getInstance().props.getProperty(key, defaultValue);
    }

    public static int getPropertyAsInt(final String key, final int defaultValue) {
        try {
            return Integer.valueOf(getInstance().props.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
