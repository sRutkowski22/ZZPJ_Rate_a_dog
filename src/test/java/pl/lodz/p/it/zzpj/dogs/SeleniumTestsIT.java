package pl.lodz.p.it.zzpj.dogs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class SeleniumTestsIT {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 30);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @Test
    public void registerTest() {
        //register
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.findElement(By.id("register")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("selenium");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("confirmPassword")).sendKeys("password");
        driver.findElement(By.id("firstName")).sendKeys("integration");
        driver.findElement(By.id("lastName")).sendKeys("test");
        driver.findElement(By.id("submit")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertEquals("Registered successfully.", alert.getText());
        alert.accept();

        //login
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("selenium");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        assertEquals("Welcome, selenium", driver.findElement(By.id("greeting")).getText());

        //accountDetails
        driver.findElement(By.id("account")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
        assertEquals("integration", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals("test", driver.findElement(By.id("lastName")).getAttribute("value"));

        //logout
        driver.findElement(By.id("logout")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        assertEquals("Welcome, guest", driver.findElement(By.id("greeting")).getText());
    }
}
