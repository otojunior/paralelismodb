package br.gov.serpro.paralelismodb.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.serpro.paralelismodb.common.Config;
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
	
	/**
	 * Construtor padrão.
	 */
	public ParalelismoDbService() {
		proceduresDao = new ParalelismoDbDao();
		executorService = Executors.newFixedThreadPool(Config.PARAM_PROCEDURES_THREADS);
		LOG.info("Threads simultâneas: " + Config.PARAM_PROCEDURES_THREADS);
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
