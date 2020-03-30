package ru.lanit.atschool.steps;

import io.cucumber.java.ru.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.lanit.atschool.webdriver.WebDriverManager;
import java.util.Random;


public class MainPageSteps {
   private WebDriver driver = WebDriverManager.getDriver();

    @Пусть("открыт браузер и введен адрес \"(.*)\"$")
    public void openedBrowserAndEnteredUrl(String url) throws InterruptedException {
        driver = WebDriverManager.getDriver();
        driver.get(url);
        String title = driver.getTitle();
        Assert.assertEquals(title, "Lanit education", "Test not complited");
        System.out.println("Test openedBrowserAndEnteredUrl complited");
        Thread.sleep(2000);
    }

    @И("переходим в раздел Категории")
    public void goToCategories() throws InterruptedException {
        WebElement webElement = driver.findElement(By.xpath("//a[contains(text(),'Категории')]"));
        webElement.click();
        String title = driver.getTitle();
        Assert.assertEquals(title, "Категории | Lanit education", "Test goToCategories not complited");
        System.out.println("Test goToCategories complited");
        Thread.sleep(2000);
    }

    @И("переходим в раздел Пользователи")
    public void goToUsers() throws InterruptedException {
        WebElement webElement = driver.findElement(By.xpath("//a[contains(text(),'Пользователи')]"));
        webElement.click();
        String title = driver.getTitle();
        Assert.assertEquals(title, "Top posters | Пользователи | Lanit education", "Test goToUsers not complited");
        System.out.println("Test goToUsers complited");
        Thread.sleep(2000);

    }

    @И("выполняем поиск пользователя из предусловия")
    public void searchForUserFromPrecondition() throws InterruptedException {
        WebElement webElement = driver.findElement(By.xpath("//*[@href=\"/search/\"]"));
        webElement.click();
        WebElement webElement1 = driver.findElement(By.xpath("//*[@class=\"form-control\"]"));
        webElement1.sendKeys("kuvaldinvitaly");
        WebElement webElement3 =  driver.findElement(By.xpath("//*[@class=\"dropdown-search-footer\"]"));
        webElement3.click();
        String title = driver.getTitle();
        Assert.assertEquals(title, "Поиск по сайту | Lanit education", "Test searchForUserFromPrecondition not complited");
        System.out.println("Test searchForUserFromPrecondition complited");
        Thread.sleep(2000);
    }

    private void checkFieldsZero(String id){
        WebElement field = driver.findElement(By.id(id));
        WebElement fieldParrent = field.findElement(By.xpath(".."));
        String fielsError = fieldParrent.findElement(By.cssSelector("p")).getText();
        Assert.assertEquals(fielsError, "Это поле обязательно.", "Test registratedZeroField not complited");

    }

    @Тогда("пробуем выполнить регистрацию с пустыми полями")
    public void registratedZeroField() throws InterruptedException {
        WebElement webElement = driver.findElement(By.xpath("//*[@class=\"btn navbar-btn btn-primary btn-register\"]"));
        webElement.click();
        Thread.sleep(1000);
        WebElement webElement1 = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]"));
        webElement1.click();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class=\"help-block errors\"]")));
        checkFieldsZero("id_username");
        checkFieldsZero("id_email");
        checkFieldsZero("id_password");
        System.out.println("Test registratedZeroField complited ");
    }


    private void fillFields(String id, String text){
        WebElement field = driver.findElement(By.id(id));
        field.sendKeys(text);
    }

    private void checkFields(String id)  {
        WebElement field = driver.findElement(By.id(id));
        WebElement fieldParrent = field.findElement(By.xpath(".."));
        String fielsError = fieldParrent.findElement(By.cssSelector("p")).getText();
        Assert.assertEquals(fielsError, "Данное имя пользователя недоступно.", "Test registratedExistingUser not complited");
    }

    @Тогда("пробуем выполнить регистрацию существующем пользователем")
    public void registratedExistingUser() throws InterruptedException {
        fillFields("id_username","kuvaldinvitaly");
        Thread.sleep(2000);
        Random random = new Random();
        int n = random.nextInt(100) + 1;
        String email = "kuvaldinvitaly" + n + "@gmail.com";
        fillFields("id_email",email);
        fillFields("id_password", n + "veryhardpassword");
        WebElement webElement1 = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]"));
        webElement1.click();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class=\"help-block errors\"]")));
        checkFields("id_username");
        WebElement webElement3 = driver.findElement(By.xpath("//button[contains(text(),'Отмена')]"));
        webElement3.click();
        System.out.println("Test registratedExistingUser complited");
    }

    @И("пробуем зарегестрировать нового пользователя")
    public void redistratedNewUser() throws InterruptedException {
        WebElement webElement = driver.findElement(By.xpath("//*[@class=\"btn navbar-btn btn-primary btn-register\"]"));
        webElement.click();
        Thread.sleep(1000);
        Random random = new Random();
        int n = random.nextInt(10000) - 1;
        fillFields("id_username","TestUser" + n);
        String email = "newTestUser" + n + "@gmail.com";
        fillFields("id_email",email);
        fillFields("id_password", n + "veryhardpassword");
        WebElement webElement1 = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]"));
        webElement1.click();
        Thread.sleep(2000);
        String reloadPage = driver.findElement(By.xpath("//button[contains(text(),'Обновите страницу')]")).getText();
        Assert.assertEquals("Обновите страницу", reloadPage, "Test redistratedNewUser not complited");
        WebElement webElement3 = driver.findElement(By.xpath("//button[contains(text(),'Обновите страницу')]"));
        webElement3.click();
        System.out.println("Test redistratedNewUser complited");

    }

    @Тогда("завершаем тест")
    public void theEnd(){
    driver.quit();
        System.out.println("Test close");
    }


}
