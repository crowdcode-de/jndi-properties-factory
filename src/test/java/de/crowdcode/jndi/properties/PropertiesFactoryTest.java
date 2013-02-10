package de.crowdcode.jndi.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

public class PropertiesFactoryTest {

	@Test
	public void testLoadingSystemProperties() throws PropertiesFactoryConfigException {
		System.getProperties().put("java:/properties", resourceAsAbsoluteFilePath("test.properties"));
		Object result = new PropertiesFactory().getObjectInstance("java:/properties", null, null, null);
		
		assertNotNull(result);
		assertEquals("test.value", ((Properties)result).get("test.key"));
	}
	
	@Test(expected=PropertiesFactoryConfigException.class)
	public void testFielNotFound() throws PropertiesFactoryConfigException {
	    System.getProperties().put("java:/properties", "does-not-exist.properties");
	    Object result = new PropertiesFactory().getObjectInstance("java:/properties", null, null, null);
	    
	    assertNotNull(result);
	    assertEquals("test.value", ((Properties)result).get("test.key"));
	}
	
	@Test(expected=PropertiesFactoryConfigException.class)
	public void testExceptionLoading() throws PropertiesFactoryConfigException {
	    new PropertiesFactory().getObjectInstance("java:/system/property/not/defined", null, null, null);
	}
	
	@Test(expected=PropertiesFactoryConfigException.class) 
	public void testNullParameters() throws PropertiesFactoryConfigException {
	    new PropertiesFactory().getObjectInstance(null, null, null, null);
	}

	private String resourceAsAbsoluteFilePath(String name) {
		return Thread.currentThread().getContextClassLoader().getResource(name).getFile();
	}

}
