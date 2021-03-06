package de.crowdcode.jndi.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.naming.spi.ObjectFactory;

/**
 * @author Ingo Düppe
 */
public class PropertiesFileFactory extends AbstractPropertiesFactory implements ObjectFactory {
	
    private static final Logger LOG = Logger.getLogger(PropertiesFileFactory.class.getName());

	@Override
    protected String resourceNameFromObject(Object object) throws PropertiesConfigException {
        String property = object.toString();
        String fileName = System.getProperty(property);
	    validateFileName(property, fileName);
        return fileName;
    }

    private void validateFileName(String property, String fileName) throws PropertiesConfigException {
        if (fileName == null || fileName.trim().isEmpty() ) {
            String message = "System property "+property+" is not set properly. " + MSG_PROPERTY_SETUP;
            LOG.severe(message);
            throw new PropertiesConfigException(message);
        }
    }

    @Override
    protected InputStream getInputStreamForResource(String resourceName)  throws PropertiesConfigException {
        try {
            return new FileInputStream(resourceName);
        } catch (FileNotFoundException e) {
            String message = "Property file "+resourceName+" not found."+ MSG_PROPERTY_SETUP;
            LOG.severe(message);
            throw new PropertiesConfigException(message, e);
        }
    }

}
