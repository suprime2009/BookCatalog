package com.softserveinc.model.util;

import java.io.IOException;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used for testing. Class used to obtain Remote Bean.
 * Class written using the Singleton pattern.
 */
public class TestHelper {

	/**
	 * Constant, URL for property file.
	 */
	public static final String URL_PROPERTY_FILE_CONTEXT = "properties/context.properties";
	
	private static Logger log = LoggerFactory.getLogger(TestHelper.class);
	private static TestHelper testHelper = null;
	private Context context;
	private final Properties props = new Properties();

	/**
	 * Private constructor
	 */
	private TestHelper() {
		context = getInitialContext();
	}

	/**
	 * This method returns instance of class TestHelper. Instance has initialized javax.naming.Context 
	 * with properties for testing database. Instance can be used for getting Remote Beans from EJBContainer.
	 * 
	 * @return TestHelper object
	 */
	public static TestHelper getInstance() {
		if (testHelper != null) {
			return testHelper;
		} else {
			return testHelper = new TestHelper();
		}
	}

	/**
	 * This method creates EJBContainer with properties for testing database and initialize 
	 * javax.naming.Context object.
	 * 
	 * @return javax.naming.Context object
	 */
	private Context getInitialContext() {
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream(URL_PROPERTY_FILE_CONTEXT));
			log.debug("Successfully uploaded property file= context.properties");
		} catch (IOException e) {
			log.error("Problem with uploading property file= context.properties", e);
		}
		return context = EJBContainer.createEJBContainer(props).getContext();
	}

	/**
	 * This method looking for Remote Beans in EJBContainer. 
	 * 
	 * @param Class clazz, which implements remote interface.
	 * @return Instance of remote bean, if bean has been found.
	 */
	public <T> Object getRemoteBean(Class<T> clazz) {
		T object = null;
		try {
			object = (T) context
					.lookup("java:global/BookLibrary-cdi/" + clazz.getSimpleName() + "!" + clazz.getName() + "Remote");
			log.debug("Remote bean {} successfully found", object.getClass().getSimpleName());
		} catch (NamingException e) {
			log.error("Remote bean {} could not found", object.getClass().getSimpleName(), e);
		}
		return object;
	}

	/**
	 * Method used to close javax.naming.Context session.
	 */
	public void closeSession() {
		try {
			context.close();
			log.debug("Context successfully closed");
		} catch (NamingException e) {
			log.warn("Can't close Context", e);
		}
	}

}
