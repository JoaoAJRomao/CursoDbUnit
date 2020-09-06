package br.ce.wcaquino.estrategia5;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
// Estratégia 2

//estrategia 5
public class ContaTesteWeb {
	private ChromeDriver driver;
	private String nomeContaAlterada = " Alterada";
	private String msgSucesso = "Conta adicionada com sucesso!";
	private String msgAlterada = "Conta alterada com sucesso!";
	private String msgRemovida = "Conta removida com sucesso!";

	@BeforeClass
	public static void reset() {
		ChromeDriver driverLogin = new ChromeDriver();
		driverLogin = new ChromeDriver();
		driverLogin.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driverLogin.manage().window().maximize();
		driverLogin.get("http://seubarriga.wcaquino.me/");
		driverLogin.findElement(By.id("email")).sendKeys("exemplo@gmail.com");
		driverLogin.findElement(By.id("senha")).sendKeys("arthur@046");
		driverLogin.findElement(By.tagName("button")).click();
		driverLogin.findElement(By.linkText("reset")).click();
		driverLogin.quit();
	}

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
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).sendKeys("Conta estratégia #5");
		driver.findElementByTagName("button").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgSucesso, msg);
	}

	@Test
	public void consultar() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElementByXPath("//td[contains(text(), 'Conta para saldo')]/..//a").click();
		String nomeAtual = driver.findElementById("nome").getAttribute("value");
		Assert.assertEquals("Conta para saldo", nomeAtual);
	}

	@Test
	public void alterar() throws ClassNotFoundException, SQLException {
		driver.findElementByLinkText("Contas").click();
		driver.findElementByLinkText("Listar").click();
		driver.findElementByXPath("//td[contains(text(), 'Conta para alterar')]/..//a").click();
		driver.findElementById("nome").sendKeys(nomeContaAlterada);
		driver.findElementByTagName("button").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgAlterada, msg);
	}

	@Test
	public void excluir() throws ClassNotFoundException, SQLException {
		driver.findElementByLinkText("Contas").click();
		driver.findElementByLinkText("Listar").click();
		driver.findElementByXPath("//td[contains(text(), 'Conta mesmo nome')]/..//a[2]").click();
		String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();
		Assert.assertEquals(msgRemovida, msg);
	}

	@After
	public void fechar() {
		driver.quit();
	}
}
