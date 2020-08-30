package cursoMassa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class InicioSelenium {
	public static void main(String[] args) {
//		System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://seubarriga.wcaquino.me/");
		driver.findElement(By.id("email")).sendKeys("exemplo@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("arthur@046");
		driver.findElement(By.tagName("button")).click();
		driver.quit();
	}

}

