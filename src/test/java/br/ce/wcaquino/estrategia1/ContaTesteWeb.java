package br.ce.wcaquino.estrategia1;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContaTesteWeb {
	private ChromeDriver driver;
	String nomeConta = "Conta estratégia #1";
	String nomeContaAlterada = "Conta estratégia #1 Alterada";
	String msgSucesso = "Conta adicionada com sucesso!";
	String msgAlterada = "Conta alterada com sucesso!";
	String msgRemovida = "Conta removida com sucesso!";

	@Before
	public void login() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://seubarriga.wcaquino.me/");
		driver.findElement(By.id("email")).sendKeys("exemplo@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("arthur@046");
		driver.findElement(By.tagName("button")).click();
	}

	@Test
	public void teste_1_inserir() {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).sendKeys(nomeConta);
		driver.findElementByTagName("button").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgSucesso, msg);
	}

	@Test
	public void teste_2_consultar() {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElementByXPath("//td[contains(text(), '" + nomeConta + "')]/..//a").click();
		String nomeAtual = driver.findElementById("nome").getAttribute("value");
		Assert.assertEquals(nomeConta, nomeAtual);
	}

	@Test
	public void teste_3_alterar() {
		driver.findElementByLinkText("Contas").click();
		driver.findElementByLinkText("Listar").click();
		driver.findElementByXPath("//td[contains(text(), '" + nomeConta + "')]/..//a").click();
		driver.findElementById("nome").clear();
		driver.findElementById("nome").sendKeys(nomeContaAlterada);
		driver.findElementByTagName("button").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgAlterada, msg);
	}

	@Test
	public void teste_4_excluir() {
		driver.findElementByLinkText("Contas").click();
		driver.findElementByLinkText("Listar").click();
		driver.findElementByXPath("//td[contains(text(), '" + nomeConta + "')]/..//a[2]").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgRemovida, msg);
	}

	@After
	public void fechar() {
		driver.quit();
	}

}
