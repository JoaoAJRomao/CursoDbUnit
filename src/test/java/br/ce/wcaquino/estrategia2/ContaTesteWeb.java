package br.ce.wcaquino.estrategia2;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
// Estratégia 2

import com.github.javafaker.Faker;

public class ContaTesteWeb {
	private ChromeDriver driver;
	private Faker faker = new Faker();
	private String nomeContaAlterada = " Alterada";
	private String msgSucesso = "Conta adicionada com sucesso!";
	private String msgAlterada = "Conta alterada com sucesso!";
	private String msgRemovida = "Conta removida com sucesso!";

	@Before
	public void login() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://seubarriga.wcaquino.me/");
		driver.findElement(By.id("email")).sendKeys("exemplo@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("arthur@046");
		driver.findElement(By.tagName("button")).click();
	}

	@Test
	public void inserir() {
		String conta = inserirConta();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgSucesso, msg);
		excluirConta(conta);
	}

	@Test
	public void consultar() {
		String conta = inserirConta();
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElementByXPath("//td[contains(text(), '" + conta + "')]/..//a").click();
		String nomeAtual = driver.findElementById("nome").getAttribute("value");
		Assert.assertEquals(conta, nomeAtual);
		excluirConta(conta);
	}

	@Test
	public void alterar() {
		String conta = inserirConta();
		driver.findElementByLinkText("Contas").click();
		driver.findElementByLinkText("Listar").click();
		driver.findElementByXPath("//td[contains(text(), '" + conta + "')]/..//a").click();
		driver.findElementById("nome").sendKeys(nomeContaAlterada);
		driver.findElementByTagName("button").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgAlterada, msg);
		excluirConta(conta);
	}

	@Test
	public void excluir() {
		String conta = inserirConta();
		excluirConta(conta);
	}

	@After
	public void fechar() {
		driver.quit();
	}

	private void excluirConta(String conta) {
		driver.findElementByLinkText("Contas").click();
		driver.findElementByLinkText("Listar").click();
		driver.findElementByXPath("//td[contains(text(), '" + conta + "')]/..//a[2]").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgRemovida, msg);
	}

	private String inserirConta() {
		String registro = faker.harryPotter().character();
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).sendKeys(registro);
		driver.findElementByTagName("button").click();
		return registro;
	}

}
