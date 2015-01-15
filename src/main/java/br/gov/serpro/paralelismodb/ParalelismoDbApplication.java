package br.gov.serpro.paralelismodb;

import org.apache.commons.configuration.ConfigurationException;

import br.gov.serpro.paralelismodb.service.ParalelismoDbService;

/**
 * Application Main Class.
 * @author <Author name>
 */
public final class ParalelismoDbApplication {
	/**
	 * Main method.
	 * @param args Command line arguments.
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws ConfigurationException {
		new ParalelismoDbService().execute();
	}
}
