package pl.lodz.p.it.zzpj.dogs;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Slf4j
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTestsIT {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        InputStream inputStream = this.getClass().getResourceAsStream("/test.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        String url = properties.getProperty("react.app.api").replace("/api", "");

        WebDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        wait = new WebDriverWait(driver, 30);
    }

    @AfterEach
    void tearDown() {
        driver.close();
        driver.quit();
    }

    private void login() {
        driver.findElement(By.id("dropdown")).click();
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("selenium");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("submit")).click();
        wait.until(ExpectedConditions.textToBe(By.id("dropdown"), "Welcome, selenium"));
    }

    private void logout() {
        driver.findElement(By.id("dropdown")).click();
        driver.findElement(By.id("logout")).click();
        wait.until(ExpectedConditions.textToBe(By.id("dropdown"), "Welcome, Guest"));
    }

    @Test
    @Order(1)
    void registerTest() {
        driver.findElement(By.id("dropdown")).click();
        driver.findElement(By.id("register")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("selenium");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("confirmPassword")).sendKeys("password");
        driver.findElement(By.id("firstName")).sendKeys("integration");
        driver.findElement(By.id("lastName")).sendKeys("test");
        driver.findElement(By.id("submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal-title")));
        assertEquals("Registered successfully", driver.findElement(By.cssSelector(".swal-title")).getText());
        driver.findElement(By.cssSelector(".swal-button")).click();
    }

    @Test
    @Order(2)
    void loginTest() {
        login();
        assertEquals("Welcome, selenium", driver.findElement(By.id("dropdown")).getText());
        assertNotNull(driver.manage().getCookieNamed("jwt"));
        logout();
    }

    @Test
    @Order(3)
    void accountDetailsTest() {
        login();
        driver.findElement(By.id("dropdown")).click();
        driver.findElement(By.id("account")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
        assertEquals("integration", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals("test", driver.findElement(By.id("lastName")).getAttribute("value"));
        logout();
    }

    @Test
    @Order(4)
    void logoutTest() {
        login();
        logout();
        assertEquals("Welcome, Guest", driver.findElement(By.id("dropdown")).getText());
        assertNull(driver.manage().getCookieNamed("jwt"));
    }
}
