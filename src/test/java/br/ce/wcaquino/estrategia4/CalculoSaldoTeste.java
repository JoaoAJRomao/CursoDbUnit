package br.ce.wcaquino.estrategia4;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.dao.SaldoDAO;
import br.ce.wcaquino.dao.impl.SaldoDAOImpl;
import br.ce.wcaquino.dbunit.ImportExport;

public class CalculoSaldoTeste {
	// 1 usuario
	// 1 conta
	// 1 transacao

	// deve considerar transcacoes do mesmo usuario
	// deve considerar transcacoes da mesma conta
	// deve considerar transacaoes pagas
	// deve considerar transcacoes até a data corrente
	// passado / presente / futuro
	// deve subtrair despesas

	@Test
	public void deveCalcularSaldoCorreto() throws Exception {
		ImportExport.importarBanco("saldo.xml");
		SaldoDAO saldoDAO = new SaldoDAOImpl();
		Assert.assertEquals(new Double(162d), saldoDAO.getSaldoConta(1l));
		Assert.assertEquals(new Double(8d), saldoDAO.getSaldoConta(2L));
		Assert.assertEquals(new Double(4d), saldoDAO.getSaldoConta(3L));
	}
}
