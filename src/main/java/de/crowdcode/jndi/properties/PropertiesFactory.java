package de.crowdcode.jndi.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

/**
 * @author Ingo Düppe
 */
public class PropertiesFactory implements ObjectFactory {
	
    private static final Logger LOG = Logger.getLogger(PropertiesFactory.class.getName());

    private static final String INFO_TEXT = "It should have the URL of a property file like ./config/xyz.properties.";

	@SuppressWarnings("PMD.ReplaceHashtableWithMap")
	public Object getObjectInstance(Object object, Name name, Context context, Hashtable<?, ?> environment) throws PropertiesFactoryConfigException {
	    return loadPropertiesFromFile(retrieveFileName(object));
	}

    private String retrieveFileName(Object object) throws PropertiesFactoryConfigException {
        if (object == null) {
            LOG.severe("Parameter object must not be NULL.");
            throw new PropertiesFactoryConfigException("Parameter object must not be NULL.", null);
        }
        
        String property = object.toString();
        String fileName = System.getProperty(property);
        
	    checkPropertyConfiguration(property, fileName);
        return fileName;
    }

    private void checkPropertyConfiguration(String property, String fileName)  throws PropertiesFactoryConfigException {
        if (fileName == null || fileName.trim().isEmpty() ) {
	        String message = "System property "+property+" is not set properly. " + INFO_TEXT;
            LOG.severe(message);
            throw new PropertiesFactoryConfigException(message);
	    }
    }

    private Properties loadPropertiesFromFile(String fileName) throws PropertiesFactoryConfigException {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            String message = "Property file "+fileName+" not found."+ INFO_TEXT;
            LOG.severe(message);
            throw new PropertiesFactoryConfigException(message, e);
        } catch (IOException e) {
            String message = "Error while loading property file "+fileName;
            LOG.severe(message);
            throw new PropertiesFactoryConfigException(message, e);
        } finally {
            closeStream(fis);
        }
        return properties;
    }
    
    private void closeStream(FileInputStream fis) throws PropertiesFactoryConfigException {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                throw new PropertiesFactoryConfigException("Couldn't close the properties file input stream.", e);
            }
        }
    }

}
