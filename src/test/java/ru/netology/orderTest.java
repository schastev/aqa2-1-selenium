package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class orderTest {
    private static ChromeDriver driver;

    @BeforeAll
    public static void setUpCommon(){
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver-87");
    }
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.setBinary("./driver/chromedriver-90");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void cleanUp() {
        driver.quit();
        driver = null;
    }
    @Test
    public void correctInputTest(){

        driver.get("http://localhost:9999");
        List<WebElement> results = driver.findElements(By.className("input__control"));
        results.get(0).sendKeys("Аа-бБ вВ");
        results.get(1).sendKeys("+00000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", alert.trim());
    }
    @Test
    public void emptyNameTest() {
        driver.get("http://localhost:9999");
        List<WebElement> results = driver.findElements(By.className("input__control"));
        results.get(1).sendKeys("+00000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("input_invalid")).getText().trim();
        WebElement abc = driver.findElement(By.className("input_invalid"));
        assertEquals("Фамилия и имя\n" +
                "Поле обязательно для заполнения", alert);
    }

    @Test
    public void incorrectNameTest() {
        driver.get("http://localhost:9999");
        List<WebElement> results = driver.findElements(By.className("input__control"));
        results.get(0).sendKeys("123");
        results.get(1).sendKeys("+00000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("input_invalid")).getText().trim();
        assertEquals("Фамилия и имя\n" +
                "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", alert);
    }
    @Test
    public void emptyPhoneTest() {
        driver.get("http://localhost:9999");
        List<WebElement> results = driver.findElements(By.className("input__control"));
        results.get(0).sendKeys("Аа-бБ вВ");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("input_invalid")).getText().trim();
        assertEquals("Мобильный телефон\n" +
                "Поле обязательно для заполнения", alert);
    }
    @Test
    public void incorrectPhoneTest() {
        driver.get("http://localhost:9999");
        List<WebElement> results = driver.findElements(By.className("input__control"));
        results.get(0).sendKeys("Аа-бБ вВ");
        results.get(1).sendKeys("00000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("input_invalid")).getText().trim();
        assertEquals("Мобильный телефон\n" +
                "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", alert);
    }
    @Test
    public void noAgreementTest() {
        driver.get("http://localhost:9999");
        List<WebElement> results = driver.findElements(By.className("input__control"));
        results.get(0).sendKeys("Аа-бБ вВ");
        results.get(1).sendKeys("+00000000000");
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("input_invalid")).getText().trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", alert);
    }
    @Test
    public void twoIncorrectFieldsFirstTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.className("button")).click();
        String alert = driver.findElement(By.className("input_invalid")).getText().trim();
        List<WebElement> results = driver.findElements(By.className("input_invalid"));
        assertEquals("Фамилия и имя\n" +
                "Поле обязательно для заполнения", alert);
        assertEquals(1, results.size());
    }
}
