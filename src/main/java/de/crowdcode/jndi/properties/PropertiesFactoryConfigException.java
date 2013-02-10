package de.crowdcode.jndi.properties;


/**
 * @author Ingo Düppe
 */
public class PropertiesFactoryConfigException extends Exception {

    private static final long serialVersionUID = 1L;

    public PropertiesFactoryConfigException(String message) {
        super(message);
    }

    public PropertiesFactoryConfigException(String message, Throwable cause) {
        super(message, cause);
    }

}
