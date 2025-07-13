package br.saucedemowebauto.pages;

import org.openqa.selenium.By;

import br.saucedemowebauto.dto.LoginDto;
import br.saucedemowebauto.selenium.SeWindow;

public class LoginPage {

    private final By usernameField = By.xpath("//*[@id='user-name']");

    private final By passwordFiled = By.xpath("//*[@id='password']");

    private final By loginButton = By.xpath("//*[@id='login-button']");

    private final By erroLabel = By.xpath("//*[@data-test='error']");

    /**
     * Faz login com os dados do objeto de transferência.
     * 
     * @param dto objeto de transferência de dados.
     */
    public void login(LoginDto dto) {
        SeWindow.takeScreenshot();
        SeWindow.findElement(usernameField).sendKeys(SeWindow.defaultString(dto.getUsername()));
        SeWindow.findElement(passwordFiled).sendKeys(SeWindow.defaultString(dto.getPassword()));
        SeWindow.takeScreenshot();
        SeWindow.findElement(loginButton).click();
        SeWindow.takeScreenshot();
    }

    /**
     * Retorna o texto do erro presenta na página "Login".
     * 
     * @return texto do erro na página.
     */
    public String getErrorText() {
        return SeWindow.waitToBeDisplayed(erroLabel, 3).getText();
    }
    
}
