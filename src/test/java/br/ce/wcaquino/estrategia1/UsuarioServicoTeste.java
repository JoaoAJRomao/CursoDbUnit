package br.ce.wcaquino.estrategia1;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.UsuarioService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioServicoTeste {
	private UsuarioService servico = new UsuarioService();
	private static Usuario usuarioGlobal;
	private String nomeUsuario = "Usuario estratégia #1";
	private String nomeUsuarioAlterado = "Usuário alterado";

	@Test
	public void teste1_inserir() throws Exception {
		Usuario usuario = new Usuario(nomeUsuario, "user@email.com", "passwd");
		usuarioGlobal = servico.salvar(usuario);
		Assert.assertNotNull(usuarioGlobal.getId());
	}

	@Test
	public void teste2_consultar() throws Exception {
		Usuario usuario = servico.findById(usuarioGlobal.getId());
		Assert.assertEquals(nomeUsuario, usuario.getNome());
	}

	@Test
	public void teste3_alterar() throws Exception {
		Usuario usuario = servico.findById(usuarioGlobal.getId());
		usuario.setNome(nomeUsuarioAlterado);
		Usuario usuarioAlterado = servico.salvar(usuario);
		Assert.assertEquals(nomeUsuarioAlterado, usuarioAlterado.getNome());
	}

	@Test
	public void teste4_excluir() throws Exception {
		servico.delete(usuarioGlobal);
		Usuario usuarioRemovido = servico.findById(usuarioGlobal.getId());
		Assert.assertNull(usuarioRemovido);
	}

}
