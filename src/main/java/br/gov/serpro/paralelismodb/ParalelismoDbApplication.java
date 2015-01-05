package br.gov.serpro.paralelismodb;

import br.gov.serpro.paralelismodb.service.ParalelismoDbService;

/**
 * Application Main Class.
 * @author <Author name>
 */
public final class ParalelismoDbApplication {
	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		new ParalelismoDbService().execute();
	}
}
