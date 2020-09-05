package br.ce.wcaquino.estrategia3;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
// Estratégia 2

import com.github.javafaker.Faker;

//estrategia 3
public class ContaTesteWeb {
	private ChromeDriver driver;
	private Faker faker = new Faker();
	private String nomeContaAlterada = " Alterada";
	private String msgSucesso = "Conta adicionada com sucesso!";
	private String msgAlterada = "Conta alterada com sucesso!";
	private String msgRemovida = "Conta removida com sucesso!";

	@Before
	public void login() {
//		System.setProperty("webdriver.chrome.driver", "C:\\webDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://seubarriga.wcaquino.me/");
		driver.findElement(By.id("email")).sendKeys("exemplo@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("arthur@046");
		driver.findElement(By.tagName("button")).click();
	}

	@Test
	public void inserir() throws ClassNotFoundException, SQLException {
		inserirConta();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgSucesso, msg);
	}

	@Test
	public void consultar() throws ClassNotFoundException, SQLException {
		String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElementByXPath("//td[contains(text(), '" + conta + "')]/..//a").click();
		String nomeAtual = driver.findElementById("nome").getAttribute("value");
		Assert.assertEquals(conta, nomeAtual);
		new MassaDAOImpl().inserirMassa(GeradorMassas.CHAVE_CONTA_SB, conta);
	}

	@Test
	public void alterar() throws ClassNotFoundException, SQLException {
		String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);
		driver.findElementByLinkText("Contas").click();
		driver.findElementByLinkText("Listar").click();
		driver.findElementByXPath("//td[contains(text(), '" + conta + "')]/..//a").click();
		driver.findElementById("nome").sendKeys(nomeContaAlterada);
		driver.findElementByTagName("button").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgAlterada, msg);
	}

	@Test
	public void excluir() throws ClassNotFoundException, SQLException {
		String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);
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

	private String inserirConta() throws ClassNotFoundException, SQLException {
		String registro = faker.gameOfThrones().character() + " " + faker.gameOfThrones().dragon();
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).sendKeys(registro);
		driver.findElementByTagName("button").click();
		new MassaDAOImpl().inserirMassa(GeradorMassas.CHAVE_CONTA_SB, registro);
		return registro;
	}
}
