package br.ce.wcaquino.estrategia2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.javafaker.Faker;

import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;

public class ContaServiceTest {

	private static Faker faker = new Faker();
	private ContaService service = new ContaService();
	private UsuarioService userService = new UsuarioService();
	private static Usuario usuarioGlobal;
	private Conta contaTeste;

	@BeforeClass
	public static void criarUsuario() {
		usuarioGlobal = new Usuario(faker.name().fullName(), faker.internet().emailAddress(),
				faker.internet().password());
	}

	@Before
	public void inserirConta() throws Exception {
		Usuario usuarioSalvo = userService.salvar(usuarioGlobal);
		Conta conta = new Conta(faker.superhero().name(), usuarioSalvo);
		contaTeste = service.salvar(conta);
	}

	@Test
	public void testInserir() throws Exception {
		Assert.assertNotNull(contaTeste.getId());
		userService.printAll();
		service.printAll();
	}

	@Test
	public void testAlterar() throws Exception {
		String novoNome = faker.ancient().god();
		contaTeste.setNome(novoNome);
		Conta contaAlterada = service.salvar(contaTeste);
		Assert.assertEquals(novoNome, contaAlterada.getNome());
		service.printAll();
	}

	@Test
	public void testConsulta() throws Exception {
		Conta contaBuscada = service.findById(contaTeste.getId());
		Assert.assertEquals(contaTeste.getNome(), contaBuscada.getNome());
	}

	@Test
	public void testExcluir() throws Exception {
		service.printAll();
		service.delete(contaTeste);
		Conta contaBuscada = service.findById(contaTeste.getId());
		Assert.assertNull(contaBuscada);
		service.printAll();
	}
}
