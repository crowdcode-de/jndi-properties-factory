package de.crowdcode.jndi.properties;

import java.io.InputStream;
import java.util.logging.Logger;

/**
 * @author idueppe
 */
public class PropertiesClasspathFactory extends AbstractPropertiesFactory {

    static final Logger LOG = Logger.getLogger(PropertiesFileFactory.class.getName());

    @Override
    protected String resourceNameFromObject(Object object) {
        String jndi = object.toString();
        String resourceName = System.getProperty(jndi);
        if (resourceName == null || resourceName.isEmpty()) {
            resourceName = resourceNameFromJndi(jndi);
        }
        return resourceName;
    }

    private String resourceNameFromJndi(String jndi) {
        int index = jndi.lastIndexOf('/');
        if (index > -1 && index < jndi.length()-1) {
            return jndi.substring(index+1)+".properties";
        } else {
            return jndi;
        }
    }

    @Override
    protected InputStream getInputStreamForResource(String resourceName) throws PropertiesConfigException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
        
        if (is == null) {
            String message = "Resource "+resourceName+" not found in classpath.";
            LOG.severe(message);
            throw new PropertiesConfigException(message);
        }
        return is;
    }

}
