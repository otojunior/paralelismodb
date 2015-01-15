package br.gov.serpro.paralelismodb.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.serpro.paralelismodb.common.ConfigurationBuilder;
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
		config = ConfigurationBuilder.getConfiguration();
		proceduresDao = new ParalelismoDbDao();
		int nThreads = config.getInt("procedures.threads");
		executorService = Executors.newFixedThreadPool(nThreads);
		LOG.info("Threads simultâneas: " + nThreads);
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
