package de.crowdcode.jndi.properties;

import java.io.File;
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
 * 
 * @author Ingo Düppe
 */
public class PropertiesFactory implements ObjectFactory {
	
	private static Logger LOG = Logger.getLogger(PropertiesFactory.class.getName());

	public Object getObjectInstance(Object object, Name name, Context context, Hashtable<?, ?> environment) throws PropertiesFactoryConfigException {
	    String propertyName = object.toString();
        String propertyFileName = System.getProperty(propertyName);
	    if (propertyFileName == null || propertyFileName.trim().isEmpty() ) {
	        
	        String message = "System property "+propertyName+" is not set properly. " +
	        		"It should have the URL of a property file like file://xyz.properties.";
	        LOG.severe(message);
            throw new PropertiesFactoryConfigException(message);
	    }
	    
	    File propertyFile = new File(propertyFileName);
	    if (!propertyFile.exists()) {
	        String message = "Property file "+propertyFile+" does not exist.";
	        LOG.severe(message);
            throw new PropertiesFactoryConfigException(message);
	    }

	    Properties properties = new Properties();
	    try {
            properties.load(new FileInputStream(propertyFile));
        } catch (FileNotFoundException e) {
            String message = "Property file "+propertyFile+" not found.";
            LOG.severe(message);
            throw new PropertiesFactoryConfigException(message, e);
        } catch (IOException e) {
            String message = "Error while loading property file "+propertyFile;
            LOG.severe(message);
            throw new PropertiesFactoryConfigException(message, e);
        }
		
		return properties;
	}

}
