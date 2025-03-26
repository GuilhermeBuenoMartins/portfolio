package br.saucedemowebauto.pages;

import org.openqa.selenium.By;

import br.saucedemowebauto.dto.LoginDto;
import br.saucedemowebauto.selenium.SeWindow;

public class LoginPage {

    private final By swagLabsTitle = By.xpath("//*[@class='login_logo']");

    private final By usernameField = By.xpath("//*[@id='user-name']");

    private final By passwordFiled = By.xpath("//*[@id='password']");

    private final By loginButton = By.xpath("//*[@id='login-button']");

    private final By erroLabel = By.xpath("//*[@data-test='error']");

    public LoginPage() {
    }

    /**
     * @return título da página <i>SwagLabsTitle</i> (Login).
     */
    public String getSwagLabsTitle() {
        return SeWindow.waitToBeDisplayed(swagLabsTitle, 3).getText();
    }

    /**
     * Faz login com os dados do objeto de transferência.
     * @param dto objeto de transferência de dados.
     */
    public void login(LoginDto dto) {
        SeWindow.findElement(usernameField).sendKeys(dto.getUsername());
        SeWindow.findElement(passwordFiled).sendKeys(dto.getPassword());
        SeWindow.takeScreenshot();
        SeWindow.findElement(loginButton).click();
    }

    /**
     * @return texto do erro presente em tela.
     */
    public String getErrorText() {
        return SeWindow.waitToBeDisplayed(erroLabel, 3).getText();
    }
    
}
