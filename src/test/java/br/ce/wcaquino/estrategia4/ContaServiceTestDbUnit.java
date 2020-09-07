package br.ce.wcaquino.estrategia4;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.dbunit.ImportExport;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;

//estrategia 4
public class ContaServiceTestDbUnit {

	private ContaService service = new ContaService();
	private UsuarioService userService = new UsuarioService();

	@Test
	public void testInserir() throws Exception {
		ImportExport.importarBanco("estrategia4_inserirConta.xml");
		Usuario usuario = userService.findById(1L);
		Conta conta = new Conta("Conta salva", usuario);
		Conta contaSalva = service.salvar(conta);
		Assert.assertNotNull(contaSalva.getId());
	}

	@Test
	public void testAlterar() throws Exception {
		ImportExport.importarBanco("estrategia4_umaConta.xml");
		Conta contaTeste = service.findByName("Conta para testes");
		service.printAll();
		contaTeste.setNome("Conta alterada");
		Conta contaAlterada = service.salvar(contaTeste);
		Assert.assertEquals("Conta alterada", contaAlterada.getNome());
		service.printAll();
	}

	@Test
	public void testConsulta() throws Exception {
		ImportExport.importarBanco("estrategia4_umaConta.xml");
		Conta contaBuscada = service.findById(1L);
		Assert.assertEquals("Conta para testes", contaBuscada.getNome());
	}

	@Test
	public void testExcluir() throws Exception {
		ImportExport.importarBanco("estrategia4_umaConta.xml");
		Conta contaTeste = service.findByName("Conta para testes");
		service.printAll();
		service.delete(contaTeste);
		Conta contaBuscada = service.findById(contaTeste.getId());
		Assert.assertNull(contaBuscada);
		service.printAll();
	}
}
