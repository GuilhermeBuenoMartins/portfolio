package br.saucedemowebauto.selenium;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeWindow {
    
    /**
     * Maximiza a janela do navegador.
     */
    public static void maximize() {
        SeConfig.getSeConfig().getWebDriver().manage().window().maximize();
    }

    /**
     * Anexa um print screen ao relatório do cenario.
     */
    public static void takeScreenshot() {
        SeConfig seConfig = SeConfig.getSeConfig();
        TakesScreenshot takesScreenshot = ((TakesScreenshot) seConfig.getWebDriver());
        byte[] image = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        String imageName = String.valueOf(Instant.now());
        seConfig.getScenario().attach(image, "image/png", imageName);
    }

    /**
     * Encontra um elemento mapeado em pelo objeto <code>By</code>.
     * @param by mapeamento do elemento Web.
     * @return elemento encontrado.
     */
    public static WebElement findElement(By by) {
        return SeConfig.getSeConfig().getWebDriver().findElement(by);
    }

    /**
     * Encontra múltiplos elemento mapeado em pelo objeto <code>By</code>.
     * @param by mapeamento do elemento Web.
     * @return uma lista de elementos encontrados.
     */
    public static List<WebElement> findElements(By by) {
        return SeConfig.getSeConfig().getWebDriver().findElements(by);
    }

    /**
     * Esperatempo de timeout especificado em segundos até que o elemento 
     * mapeado pelo objeto <code>By</code> esteja presente.
     * @param by mapeamento do elemento Web.
     * @param seconds tempo de timeout especificado em segundos.
     * @return elemento encontrado.
     */
    public static WebElement waitToBeDisplayed(By by, int seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        WebDriver webDriver = SeConfig.getSeConfig().getWebDriver();
        Wait<WebDriver> wait = new WebDriverWait(webDriver, duration);
        WebElement webElement = findElement(by);
        wait.until(f -> webElement.isDisplayed());
        return webElement;
    }

    /**
     * Esperatempo de timeout especificado em segundos até que o elemento 
     * mapeado pelo objeto <code>By</code> esteja habilitado.
     * @param by mapeamento do elemento Web.
     * @param seconds tempo de timeout especificado em segundos.
     * @return elemento encontrado.
     */
    public static WebElement waitToBeEnabled(By by, int seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        WebDriver webDriver = SeConfig.getSeConfig().getWebDriver();
        Wait<WebDriver> wait = new WebDriverWait(webDriver, duration);
        WebElement webElement = findElement(by);
        wait.until(f -> webElement.isEnabled());
        return webElement;
    }

    /**
     * Esperatempo de timeout especificado em segundos até que o elemento 
     * mapeado pelo objeto <code>By</code> esteja selecionado.
     * @param by mapeamento do elemento Web.
     * @param seconds tempo de timeout especificado em segundos.
     * @return elemento encontrado.
     */
    public static WebElement waitToBeSelected(By by, int seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        WebDriver webDriver = SeConfig.getSeConfig().getWebDriver();
        Wait<WebDriver> wait = new WebDriverWait(webDriver, duration);
        WebElement webElement = findElement(by);
        wait.until(f -> findElement(by).isSelected());
        return webElement;
    }

}
