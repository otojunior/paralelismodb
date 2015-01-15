/**
 * 
 */
package br.gov.serpro.paralelismodb.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration Builder.
 * @author 01456231650
 *
 */
public class ConfigurationBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationBuilder.class);
	private static PropertiesConfiguration configuration;
	
	public static Configuration getConfiguration() {
		try {
			if (configuration == null) {
				String configfile = System.getProperty("paralelismodb.configfile");
				String fileName = (configfile != null) ? configfile : "config.properties";
				configuration = new PropertiesConfiguration(fileName);
				configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
			}
		} catch (ConfigurationException e) {
			LOG.error("Erro na carga da configuração.", e);
		}
		return configuration;
	}
}
