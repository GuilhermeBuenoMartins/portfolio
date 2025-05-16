package br.saucedemowebauto.selenium;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class SeWindow {

    private SeWindow() throws InstantiationException {
        throw new InstantiationException("The class " + this.toString() + " is not instantiable.");
    }
    
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
     * Verifica a existência de um elemento Web na páigna.
     * @param by mapeamento do elemento Web.
     * @return <code>true</code> se o elemento existe, caso contrário, 
     *          <code>false</code>.
     */
    public static boolean exist(By by) {
        return findElement(by) != null;
    }

    /**
     * Espera empo de timeout especificado em segundos até que o elemento 
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
     * Espera tempo de timeout especificado em segundos até que o elemento 
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
     * Espera tempo de timeout especificado em segundos até que o elemento 
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

    /**
     * Espera segundos determinados para iniciar a próximo instrução.
     * @param seconds segundos determinados.
     */
    public static void wait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Retorna uma {@code String}. Se o parâmetro {@code value}
     * for {@code null}, é retornada uma string vazia ("").
     *
     * @param value o valor da {@code String}.
     * @return uma string vazia ("") se {@code value} for {@code null}; caso contrário, o próprio valor.
     */
    public static String defaultString(String value) {
        return Objects.isNull(value) ? "" : value;
    }
}
