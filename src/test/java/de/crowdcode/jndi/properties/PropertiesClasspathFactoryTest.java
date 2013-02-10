package de.crowdcode.jndi.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

public class PropertiesClasspathFactoryTest {

	@Test
	public void testLoadingSystemProperties() throws PropertiesConfigException {
		System.getProperties().put("java:/properties", "test.properties");
		Object result = new PropertiesClasspathFactory().getObjectInstance("java:/properties", null, null, null);
		
		assertNotNull(result);
		assertEquals("test.value", ((Properties)result).get("test.key"));
	}
	
	@Test
	public void testLoadingJNDIProperties() throws PropertiesConfigException {
	    Object result = new PropertiesClasspathFactory().getObjectInstance("java:/test", null, null, null);
	    assertNotNull(result);
	    assertEquals("test.value", ((Properties)result).get("test.key"));
	}

	@Test
	public void testLoadingJNDINameProperties() throws PropertiesConfigException {
	    Object result = new PropertiesClasspathFactory().getObjectInstance("test.properties", null, null, null);
	    assertNotNull(result);
	    assertEquals("test.value", ((Properties)result).get("test.key"));
	}
	
	@Test(expected=PropertiesConfigException.class)
	public void testFielNotFound() throws PropertiesConfigException {
	    System.getProperties().put("java:/properties", "does-not-exist.properties");
	    new PropertiesClasspathFactory().getObjectInstance("java:/properties", null, null, null);
	}
	
	@Test(expected=PropertiesConfigException.class)
	public void testExceptionLoading() throws PropertiesConfigException {
	    new PropertiesClasspathFactory().getObjectInstance("java:/system/property/not/defined", null, null, null);
	}
	
	@Test(expected=PropertiesConfigException.class) 
	public void testNullParameters() throws PropertiesConfigException {
	    new PropertiesClasspathFactory().getObjectInstance(null, null, null, null);
	}

}
