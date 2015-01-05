/**
 * 
 */
package br.gov.serpro.paralelismodb.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 01456231650
 *
 */
public class Config {
	private static final Logger LOG = LoggerFactory.getLogger(Config.class);
	
	private static final String CONFIG_FILE_SYSTEM_PROPERTY = "procedures.configFile";
	private static final String CONFIG_FILE_NAME = "config.properties";
	
	private static final String KEY_JDBC_DRIVER = "jdbc.driver";
	private static final String KEY_JDBC_URL = "jdbc.url";
	private static final String KEY_JDBC_USER = "jdbc.user";
	private static final String KEY_JDBC_PASSWORD = "jdbc.password";
	private static final String KEY_PROCEDURES_THREADS = "procedures.threads";
	
	public static final String JDBC_DRIVER;
	public static final String JDBC_URL;
	public static final String JDBC_USER;
	public static final String JDBC_PASSWORD;
	
	public static final int PARAM_PROCEDURES_THREADS;

	private static Properties properties;

	static {
		String filePath = System.getProperty(CONFIG_FILE_SYSTEM_PROPERTY);
		properties = StringUtils.isBlank(filePath) ?
			loadFromResource() :
			loadFromAbsolutePath(filePath);
			
		JDBC_DRIVER = properties.getProperty(KEY_JDBC_DRIVER);
		JDBC_URL = properties.getProperty(KEY_JDBC_URL);
		JDBC_USER = properties.getProperty(KEY_JDBC_USER);
		JDBC_PASSWORD = properties.getProperty(KEY_JDBC_PASSWORD);
		
		PARAM_PROCEDURES_THREADS = Integer.parseInt(properties.getProperty(KEY_PROCEDURES_THREADS));
	}
	
	/**
	 * Construtor privado.
	 */
	private Config() { }
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	private static Properties loadFromAbsolutePath(String filePath) {
		LOG.info("Loading config from file: " + filePath);
		File file = new File(filePath);
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(file));
		} catch (IOException e) {
			String msg = (e.getClass().equals(FileNotFoundException.class)) ?
				"Arquivo não encontrado." :
				"Erro de leitura no arquivo: " + file.getPath();
			LOG.error(msg, e);
			properties = null;
		}
		
		return properties;
	}

	/**
	 * 
	 * @return
	 */
	private static Properties loadFromResource() {
		LOG.info("Loading default configuration");
		InputStream inputStream = Config.class.
			getClassLoader().
			getResourceAsStream(CONFIG_FILE_NAME);
		
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			LOG.error("Não foi possível carregar arquivo de configuração.", e);
			properties = null;
		}
		
		return properties;
	}
}
