package br.ce.wcaquino.estrategia2;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.dao.SaldoDAO;
import br.ce.wcaquino.dao.impl.SaldoDAOImpl;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.TipoTransacao;
import br.ce.wcaquino.entidades.Transacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.TransacaoService;
import br.ce.wcaquino.service.UsuarioService;

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
		UsuarioService usuarioService = new UsuarioService();
		ContaService contaService = new ContaService();
		TransacaoService transacaoService = new TransacaoService();

		// usuarios
		Usuario usuario = usuarioService.salvar(new Usuario("Usuário", "email@email.com", "123"));
		Usuario usuarioAlternativo = usuarioService
				.salvar(new Usuario("Usuário Alternativo", "email2@qualquer.com", "123"));

		// conta
		Conta conta = contaService.salvar(new Conta("Conta principal", usuario.getId()));
		Conta contaSecundaria = contaService.salvar(new Conta("Conta Secundária", usuario.getId()));
		Conta contaUsuarioAlternativo = contaService
				.salvar(new Conta("Conta Usuário Alternativo", usuarioAlternativo.getId()));

		// transacoes
		// 2

		// transacao correta / saldo = 2
		transacaoService.salvar(new Transacao("Transação Inicial", "Envolvido", TipoTransacao.RECEITA, new Date(), 2d, true, conta, usuario));

		// transacao usuario alternativo / saldo = 2
		transacaoService.salvar(new Transacao("Transação outro usuário", "Envolvido", TipoTransacao.RECEITA, new Date(), 4d, true, contaUsuarioAlternativo, usuarioAlternativo));

		// transacao de outra conta / saldo = 2
		transacaoService.salvar(new Transacao("Transação outro conta", "Envolvido", TipoTransacao.RECEITA, new Date(), 8d, true, contaSecundaria, usuario));

		// transacao pendente / saldo = 2
		transacaoService.salvar(new Transacao("Transação pendente", "Envolvido", TipoTransacao.RECEITA, new Date(), 16d, false, conta, usuario));

		// transacao passada / saldo= 34
		transacaoService.salvar(new Transacao("Transação passada", "Envolvido", TipoTransacao.RECEITA, obterDataComDiferencaDias(-1), 32d, true, conta, usuario));

		// transacao futura / saldo = 34
		transacaoService.salvar(new Transacao("Transação futura", "Envolvido", TipoTransacao.RECEITA, obterDataComDiferencaDias(1), 64d, true, conta, usuario));

		// transacao despesa / saldo = -94
		transacaoService.salvar(new Transacao("Transação despesa", "Envolvido", TipoTransacao.DESPESA, new Date(), 128d, true, conta, usuario));

		// testes de saldo com valor negativo dá azar / saldo = 162
		transacaoService.salvar(new Transacao("Transação da sorte", "Envolvido", TipoTransacao.RECEITA, new Date(), 256d, true, conta, usuario));

		SaldoDAO saldoDAO = new SaldoDAOImpl();
		Assert.assertEquals(new Double(162d), saldoDAO.getSaldoConta(conta.getId()));
		Assert.assertEquals(new Double(8d), saldoDAO.getSaldoConta(contaSecundaria.getId()));
		Assert.assertEquals(new Double(4d), saldoDAO.getSaldoConta(contaUsuarioAlternativo.getId()));
	}

	public Date obterDataComDiferencaDias(int dias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, dias);
		return cal.getTime();
	}
}
