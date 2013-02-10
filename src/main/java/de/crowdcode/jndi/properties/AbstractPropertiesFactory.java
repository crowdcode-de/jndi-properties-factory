package de.crowdcode.jndi.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Ingo Dueppe
 */
public abstract class AbstractPropertiesFactory {
    
    private static final Logger LOG = Logger.getLogger(PropertiesFileFactory.class.getName());

    protected static final String MSG_NOT_NULL = "Parameter object must not be null.";
    protected static final String MSG_PROPERTY_SETUP = "It should have the URL of a property file like ./config/xyz.properties.";

    protected void validateNotNull(Object object) throws PropertiesConfigException {
        if (object == null) {
            LOG.severe(MSG_NOT_NULL);
            throw new PropertiesConfigException(MSG_NOT_NULL);
        }
    }

    protected Properties loadProperties(String resourceName) throws PropertiesConfigException {
        InputStream is = getInputStreamForResource(resourceName);
        
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            String msg = "Error while loading properties from "+resourceName+" ocurred.\n"+e.getMessage();
            LOG.severe(msg);
            throw new PropertiesConfigException(msg, e);
        } finally {
            closeStream(is);
        }
        return properties;
    }

    protected abstract InputStream getInputStreamForResource(String resourceName) throws PropertiesConfigException;

    protected void closeStream(InputStream is) throws PropertiesConfigException {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                throw new PropertiesConfigException("Couldn't close the properties input stream.", e);
            }
        }
    }
}

