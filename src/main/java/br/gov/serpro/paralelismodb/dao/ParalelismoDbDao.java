/**
 * 
 */
package br.gov.serpro.paralelismodb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.serpro.paralelismodb.common.ServiceLocator;

/**
 * @author 01456231650
 *
 */
public class ParalelismoDbDao {
	private static final Logger LOG = LoggerFactory.getLogger(ParalelismoDbDao.class);
	
	private static final String QUERY_ITERACOES = 
		"select generate_series as id " + 
		"from generate_series(1, 20)";
	
	private static final String QUERY_EXECUCAO_PROCEDURE = 
		"select now " + 
		"from now(), pg_sleep((mod(cast(random()*(10^9) as integer),10)+5))";
	
	/**
	 * Construtor padrão.
	 * @param id
	 */
	public ParalelismoDbDao() { }
	
	/**
	 * Executa Workload Procedures
	 */
	public void executarTesteProcedure(int id) {
		Connection conn = ServiceLocator.getInstance().createConnection();
		QueryRunner runner = new QueryRunner();
		
		try {
			Timestamp result = runner.query(conn, 
				QUERY_EXECUCAO_PROCEDURE, 
				new ScalarHandler<Timestamp>());
			LOG.info("ID: " + id + " - Resultado: " + result.toString());
		} catch (SQLException e) {
			LOG.error("Erro de conexão/SQL", e);
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
	
	/**
	 * Obtém iterações.
	 * @return
	 */
	public List<Integer> obterIteracoes() {
		Connection conn = ServiceLocator.getInstance().createConnection();
		QueryRunner runner = new QueryRunner();
		List<Integer> result = null;
		try {
			result = runner.query(conn,  
				QUERY_ITERACOES, 
				new ColumnListHandler<Integer>());
			LOG.info("Número de tasks a iterar: " + result.size());
		} catch (SQLException e) {
			LOG.error("Erro de conexão/SQL", e);
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return result;
	}
	
	/**
	 * @author 01456231650
	 */
	public class TaskExecutarTesteProcedure implements Runnable {
		private int id;
		public TaskExecutarTesteProcedure(int id) { this.id = id; }
		@Override public void run() { ParalelismoDbDao.this.executarTesteProcedure(id); }
	}
}
