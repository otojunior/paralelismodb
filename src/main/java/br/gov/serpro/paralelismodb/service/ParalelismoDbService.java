package br.gov.serpro.paralelismodb.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.serpro.paralelismodb.dao.ParalelismoDbDao;

/**
 * 
 * @author 01456231650
 *
 */
public class ParalelismoDbService {
	private static final Logger LOG = LoggerFactory.getLogger(ParalelismoDbService.class);
	
	private ParalelismoDbDao proceduresDao;
	private ExecutorService executorService;
	private Configuration config;
	
	/**
	 * Construtor padrão.
	 */
	public ParalelismoDbService() {
		try {
			config = new PropertiesConfiguration("config.properties");
			proceduresDao = new ParalelismoDbDao();
			int nThreads = config.getInt("procedures.threads");
			executorService = Executors.newFixedThreadPool(nThreads);
			LOG.info("Threads simultâneas: " + nThreads);
		} catch (ConfigurationException e) {
			LOG.error("Erro na carga da configuração", e);
		}
	}
	
	/**
	 * Executa as iterações.
	 */
	public void execute() {
		List<Integer> iteracoes = proceduresDao.obterIteracoes();
		for (Integer id : iteracoes) {
			executorService.execute(proceduresDao.new TaskExecutarTesteProcedure(id));
		}
		executorService.shutdown();
	}
}
