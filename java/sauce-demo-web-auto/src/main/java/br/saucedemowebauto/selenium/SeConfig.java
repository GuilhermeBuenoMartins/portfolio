package br.saucedemowebauto.selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.saucedemowebauto.selenium.enums.Browser;
import io.cucumber.java.Scenario;

/**
 * Configurações do Selenium para o projeto.
 */
public class SeConfig {

    private final String PROPERTIES_PATH = "src/main/resources/selenium.properties";

    private final String DEFAULT_GRID_URL = "http://localhost:4444";

    private final String DEFAULT_IMPLICITY_WAIT = "1";

    private final String DEFAULT_GRID_BROWSER = "CHROME";

    private static SeConfig seConfig = null;

    private Scenario scenario;

    private Properties properties = null;

    private WebDriver webDriver;

    private SeConfig(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Criar e retorna uma nova instância de <code>SeConfig</code>.
     * 
     * @param scenario objeto <code>Scenario</code> do Cucumber.
     * @return uma nova instância do <code>SeConfig</code>.
     */
    public static SeConfig instantiate(Scenario scenario) {
        if (seConfig == null) {
            seConfig = new SeConfig(scenario);
        }
        return seConfig;
    }

    /**
     * @return uma instância já criada de <code>SeConfig</code> ou <code>null
     * </code>, caso contrário.
     */
    public static SeConfig getSeConfig() {
        return seConfig;
    }

    private Properties loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream(PROPERTIES_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * @return propriedades carregadas a partir do arquivo <code>
     * selenium.properties</code>
     */
    public Properties getProperties() {
        return properties == null ? loadProperties() : properties;
    }

    /**
     * @return uma nova instância padrão do tipo <code>ChromeOptions</code>.
     */
    public ChromeOptions getDefaultChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        String seconds = getProperties().getProperty("selenium.wait.implicity", DEFAULT_IMPLICITY_WAIT);
        Duration duration = Duration.ofSeconds(Long.parseLong(seconds));
        chromeOptions.setImplicitWaitTimeout(duration);
        chromeOptions.addArguments("--incognito");
        if (getProperties().getProperty("selenium.headless", "false").toLowerCase().equals("true")) {
            chromeOptions.addArguments("--headless");
        }
        return chromeOptions;
    }

    /**
     * @return uma nova instância padrão do tipo <code>EdgeOptions</code>..
     */
    public EdgeOptions getDefaultEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        String seconds = getProperties().getProperty("selenium.wait.implicity", DEFAULT_IMPLICITY_WAIT);
        Duration duration = Duration.ofSeconds(Long.parseLong(seconds));
        edgeOptions.setImplicitWaitTimeout(duration);
        edgeOptions.addArguments("--inprivate");
        if (getProperties().getProperty("selenium.headless", "false").toLowerCase().equals("true")) {
            edgeOptions.addArguments("--headless");
        }
        return edgeOptions;
    }

    /**
     * @return uma nova instância padrão do tipo <code>FirefoxOptions</code>.
     */
    public FirefoxOptions getDefaultFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        String seconds = getProperties().getProperty("selenium.wait.implicity", DEFAULT_IMPLICITY_WAIT);
        Duration duration = Duration.ofSeconds(Long.parseLong(seconds));
        firefoxOptions.setImplicitWaitTimeout(duration);
        firefoxOptions.addArguments("--private-window");
        if (getProperties().getProperty("selenium.headless", "false").toLowerCase().equals("true")) {
            firefoxOptions.addArguments("--headless");
        }
        return firefoxOptions;
    }

    /**
     * Devolve uma nova instância do objeto <code>WebDriver</code> a partir do
     * objeto <code>Capability</code>.
     * 
     * @param capability uma abstração do <code>ChromeOptions</code>, <code>
     *                   EdgeOptions</code> ou <code>FirefoxOptions</code>.
     * @return uma nova instância do objeto <code>WebDriver</code>.
     */
    public WebDriver instantiateWebDriver(Capabilities capability) {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
        String gridUrl = getProperties().getProperty("selenium.grid.url", DEFAULT_GRID_URL);
        try {
            webDriver = new RemoteWebDriver(new URL(gridUrl), capability);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return webDriver;
    }

    /**
     * Devolve uma nova instância do objeto <code>WebDriver</code> com a partir de
     * um <code>ChromeOptions</code>, <code>EdgeOptions</code> ou <code>
     * FirefoxOptions</code> padrão de acordo com o valor do Enum <code> Browser
     * </code>.
     * 
     * @param browser opções de navegadores do projeto.
     * @return uma nova instância do objeto <code>WebDriver</code>.
     */
    public WebDriver instantiateWebDriver(Browser browser) {
        switch (browser) {
            case CHROME:
                return instantiateWebDriver(getDefaultChromeOptions());
            case EDGE:
                return instantiateWebDriver(getDefaultEdgeOptions());
            case FIREFOX:
                return instantiateWebDriver(getDefaultFirefoxOptions());
        }
        return webDriver;
    }

    /**
     * @return uma nova instância do objeto <code>WebDriver</code>.
     */
    public WebDriver instantiateWebDriver() {
        String browser = getProperties().getProperty("selenium.grid.browser", DEFAULT_GRID_BROWSER);
        return instantiateWebDriver(Browser.valueOf(browser));
    }

    /**
     * @return uma nova instância padrão criada ou já existente do <code>
     * WebDriver</code>.
     */
    public WebDriver getWebDriver() {
        return webDriver == null ? instantiateWebDriver() : webDriver;
    }

    /**
     * @return objeto <code>Scenario</code> do Cucumber.
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Fecha janelas aberta e a conexão.
     */
    public void closeWebDriver() {
        if (webDriver != null) { 
            webDriver.quit();
            webDriver = null;
        }
    }
}