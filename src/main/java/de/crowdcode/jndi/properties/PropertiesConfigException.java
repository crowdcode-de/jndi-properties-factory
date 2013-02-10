package de.crowdcode.jndi.properties;


/**
 * @author Ingo Düppe
 */
public class PropertiesConfigException extends Exception {

    private static final long serialVersionUID = 1L;

    public PropertiesConfigException(String message) {
        super(message);
    }

    public PropertiesConfigException(String message, Throwable cause) {
        super(message, cause);
    }

}
