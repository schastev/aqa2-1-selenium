package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class orderTest {
    private static ChromeDriver driver;

    @BeforeAll
    public static void setUpCommon(){
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
    }
    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void cleanUp() {
        driver.quit();
        driver = null;
    }
    @Test
    public void correctInputTestSelenium(){
        driver.get("http://localhost:9999");
        List<WebElement> results = driver.findElements(By.className("input__control"));
        results.get(0).sendKeys("Аа-бБ вВ");
        results.get(1).sendKeys("+00000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявкj успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", alert.trim());
    }

}
