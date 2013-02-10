package de.crowdcode.jndi.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

public class PropertiesFileFactoryTest {

	@Test
	public void testLoadingSystemProperties() throws PropertiesConfigException {
		System.getProperties().put("java:/properties", resourceAsAbsoluteFilePath("test.properties"));
		Object result = new PropertiesFileFactory().getObjectInstance("java:/properties", null, null, null);
		
		assertNotNull(result);
		assertEquals("test.value", ((Properties)result).get("test.key"));
	}
	
	@Test(expected=PropertiesConfigException.class)
	public void testFielNotFound() throws PropertiesConfigException {
	    System.getProperties().put("java:/properties", "does-not-exist.properties");
	    Object result = new PropertiesFileFactory().getObjectInstance("java:/properties", null, null, null);
	    
	    assertNotNull(result);
	    assertEquals("test.value", ((Properties)result).get("test.key"));
	}
	
	@Test(expected=PropertiesConfigException.class)
	public void testExceptionLoading() throws PropertiesConfigException {
	    new PropertiesFileFactory().getObjectInstance("java:/system/property/not/defined", null, null, null);
	}
	
	@Test(expected=PropertiesConfigException.class) 
	public void testNullParameters() throws PropertiesConfigException {
	    new PropertiesFileFactory().getObjectInstance(null, null, null, null);
	}

	private String resourceAsAbsoluteFilePath(String name) {
		return Thread.currentThread().getContextClassLoader().getResource(name).getFile();
	}

}
