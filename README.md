## PropertiesFactory 
           
JNDI-Properties provides two simple JNDI ObjectFactory classes that enable you to bind a property file as a Properties object into the jndi tree of JBoss AS 7.

For Instance,

   @Resource(mappedName="java:/app-configuration")
   private java.util.Properties properties;

You can choose between `PropertiesFileFactory` or `PropertiesClasspathFactory` class. 
`PropertiesFileFactory` loads the properties from a filesystem and `PropertiesClasspathFactory` loads the properties from classpath.     


###JBoss AS 7 Setup:
                                                    
Setting up the module:

Put into the folder de/crowdcode/jndi/properties/main the jndi-properties.jar file and a module.xml containing

    <?xml version="1.0" encoding="UTF-8"?>
    <module xmlns="urn:jboss:module:1.1" name="de.crowdcode.jndi.properties">
	    <resources>
		    <resource-root path="."/>
		    <resource-root path="jndi-properties.jar"/>
	    </resources>  
	    <dependencies>
	  	    <module name="javax.api" />
        </dependencies>
    </module>

### Configuring of `PropertyFileFactory`

Define the JNDI Binding in the `standalone.xml`:

    <subsystem xmlns="urn:jboss:domain:naming:1.1">
        <bindings>
            <object-factory name="java:/property-config" module="de.crowdcode.jndi.properties" class="de.crowdcode.jndi.properties.PropertiesFileFactory"/>
        </bindings>
    </subsystem>                                                                                                                                              

And as JBoss AS 7.1.1 doesn't support configuration of ObjectFactories, you need to define a system property with the name of the jndi name.

    <system-properties>
        <property name="java:/property-config" value="/absolute/path/to/the/file/application.properties"/>
    </system-properties>

#### Configuring of `PropertyClasspathFactory`

    <subsystem xmlns="urn:jboss:domain:naming:1.1">
        <bindings>
            <object-factory name="java:/property-config" module="de.crowdcode.jndi.properties" class="de.crowdcode.jndi.properties.PropertiesClasspathFactory"/>
        </bindings>
    </subsystem>                                                                                                                                              
	
Add your properties files into the de/crowdcode/jndi/properties folder.

# License

<a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
	<img alt="Creative Commons Lizenzvertrag" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
</a>
<br />   

<subsystem xmlns="urn:jboss:domain:naming:1.1">
    <bindings>
        <object-factory name="java:/property-config" module="de.crowdcode.jndi.properties" class="de.crowdcode.jndi.properties.PropertiesFactory"/>
    </bindings>
</subsystem>

<span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text" property="dct:title" rel="dct:type">
	jndi-properties-factory
</span> 
by 
<span xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Ingo Düppe</span> 
is licensed under the 
<a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)</a>.