/**
 * 
 */
package br.gov.serpro.paralelismodb.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 01456231650
 *
 */
public class ServiceLocator {
	public synchronized static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(ServiceLocator.class);
	private static ServiceLocator instance;
		
	private Configuration config;
	
	/**
	 * Construtor padrão.
	 */
	private ServiceLocator() {
		try {
			config = new PropertiesConfiguration("config.properties");
			loadJdbcDriver();
		} catch (ConfigurationException e) {
			LOG.error("Erro na carga do arquivo de configuração", e);
		}
	}

	/**
	 * Cria a conexão.
	 * @return Conexão Jdbc.
	 */
	public Connection createConnection() {
		Connection connection = null;
		try {
			String jdbcUrl = config.getString("jdbc.url");
			String jdbcUser = config.getString("jdbc.user");;
			String jdbcPassword = config.getString("jdbc.password");

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
	
	/**
	 * Carrega o driver JDBC.
	 */
	private void loadJdbcDriver() {
		String jdbcDriver = config.getString("jdbc.driver");
		DbUtils.loadDriver(jdbcDriver);
		LOG.info("Loaded driver: " + jdbcDriver);
	}
}
