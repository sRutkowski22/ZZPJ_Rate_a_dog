package pl.lodz.p.it.zzpj.dogs;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Slf4j
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class SeleniumTestsIT {

    private WebDriver driver;
    private WebDriverWait wait;
    private String url;

    @BeforeEach
    public void setUp() {
        InputStream inputStream = this.getClass().getResourceAsStream("/test.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        url = properties.getProperty("react.app.api").replace("/api", "");

        if (url.contains("localhost")) {
            WebDriverManager.getInstance(FirefoxDriver.class).setup();
        }
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, 30);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void registerTest() {
        //register
        driver.get(url);
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
