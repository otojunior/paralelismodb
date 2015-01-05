/**
 * 
 */
package br.gov.serpro.paralelismodb.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 01456231650
 *
 */
public class ServiceLocator {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceLocator.class);
	
	private static ServiceLocator instance;
	
	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPassword;
	
	public synchronized static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}
	
	/**
	 * 
	 */
	private ServiceLocator() {
		this.jdbcDriver = Config.JDBC_DRIVER;
		this.jdbcUrl = Config.JDBC_URL;
		this.jdbcUser = Config.JDBC_USER;
		this.jdbcPassword = Config.JDBC_PASSWORD;
		
		DbUtils.loadDriver(jdbcDriver);
		LOG.info("Loaded driver: " + jdbcDriver);
	}
	
	/**
	 * 
	 * @return
	 */
	public Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			if (LOG.isDebugEnabled()) {
				LOG.debug("JDBC URL: " + jdbcUrl);
				LOG.debug("JDBC User: " + jdbcUser);
			}
		} catch (SQLException e) {
			LOG.error("Erro ao obter conexão", e);
		}
		return connection;
	}
}
