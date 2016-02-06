package com.softserveinc.model.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used for testing. Class used to insert testing data from file
 * into testing database and delete all records from tables of database. Class
 * written using the Singleton pattern.
 *
 */
public class DBUnitHelper {

	/**
	 * Constant, URL for file with testing values.
	 */
	public static final String URL_FOR_TESTING_FILE = "src/test/resources/testingData.xml";
	/**
	 * Constant, URL for property file.
	 */
	public static final String URL_FOR_PROPERTY_FILE_TESTING_DB = "properties/dbconfig.properties";

	private static Logger log = LoggerFactory.getLogger(DBUnitHelper.class);

	private static DBUnitHelper dbUnitHelper = null;
	private Connection jdbcConnection;
	private IDatabaseConnection connection;
	private IDataSet dataSet;
	private Properties props = new Properties();

	/**
	 * Private constructor.
	 */
	public DBUnitHelper() {
		loadData();
	}

	/**
	 * This method loads testing data from file into org.dbunit.dataset.IDataSet
	 * dataSet object. This method loads properties from file for connection to
	 * testing database.
	 */
	private void loadData() {
		try {
			dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(URL_FOR_TESTING_FILE));
			log.debug("File with testing data was successfully uploaded");
		} catch (DataSetException e) {
			log.error("Some mistake in DataSet file= {}", e, URL_FOR_TESTING_FILE);
		} catch (IOException e) {
			log.error("Can't upload testing Data File={}", e, URL_FOR_TESTING_FILE);
		}
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream(URL_FOR_PROPERTY_FILE_TESTING_DB));
			log.debug("Successfully uploaded property file= dbconfig.properties");
		} catch (IOException e) {
			log.error("Problem with uploading property file= dbconfig.properties", e);
		}
	}

	/**
	 * This method returns instance of class DBUnitHelper. Instance can be used
	 * to insert testing data and delete all records from tables of database.
	 * 
	 * @return DBUnitHelper object
	 */
	public static DBUnitHelper getInstance() {
		if (dbUnitHelper != null) {
			return dbUnitHelper;
		} else {
			return dbUnitHelper = new DBUnitHelper();
		}
	}

	/**
	 * Method creates connection to testing database.
	 */
	private void setConnection() {
		try {
			jdbcConnection = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.username"),
					props.getProperty("db.password"));
			connection = new DatabaseConnection(jdbcConnection);
			log.debug("Successfully connected with database");
		} catch (DatabaseUnitException e) {
			log.error("Can't execute dataset", e);
		} catch (SQLException e) {
			log.error("Can't create connection with database", e);
		}
	}

	/**
	 * This method used to insert values for testing into database.
	 */
	public void insertTestingData() {
		setConnection();
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
			log.debug("Testing data were successfully inserted to database");
		} catch (DatabaseUnitException | SQLException e) {
			log.error("Can't insert testing data to database", e);
		} finally {
			try {
				if (!connection.getConnection().isClosed()) {
					connection.close();
					log.debug("Connection was successfully closed");
				}
			} catch (SQLException e) {
				log.error("Can't close connection", e);
			}
		}
	}

	/**
	 * This method used to delete all values from database (used after tests).
	 */
	public void clearTestingDatabase() {
		setConnection();
		try {
			DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
			log.debug("Testing data were successfully deleted from database");
		} catch (DatabaseUnitException | SQLException e) {
			log.error("Can't delete testing data to database", e);
		} finally {
			try {
				if (!connection.getConnection().isClosed()) {
					connection.close();
					log.debug("Connection was successfully closed");
				}
			} catch (SQLException e) {
				log.error("Can't close connection", e);
			}
		}
	}

}
