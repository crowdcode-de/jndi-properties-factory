package de.crowdcode.jndi.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

/**
 * @author Ingo Dueppe
 */
public abstract class AbstractPropertiesFactory implements ObjectFactory {
    
    private static final Logger LOG = Logger.getLogger(PropertiesFileFactory.class.getName());

    protected static final String MSG_NOT_NULL = "Parameter object must not be null.";
    protected static final String MSG_PROPERTY_SETUP = "It should have the URL of a property file like ./config/xyz.properties.";
    

    @Override
    @SuppressWarnings("PMD.ReplaceHashtableWithMap")
    public Object getObjectInstance(Object object, Name name, Context context, Hashtable<?, ?> environment)  throws PropertiesConfigException{
        validateNotNull(object);
        return loadProperties(resourceNameFromObject(object));
    }

    protected abstract InputStream getInputStreamForResource(String resourceName) throws PropertiesConfigException;
    
    protected abstract String resourceNameFromObject(Object object) throws PropertiesConfigException;

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

