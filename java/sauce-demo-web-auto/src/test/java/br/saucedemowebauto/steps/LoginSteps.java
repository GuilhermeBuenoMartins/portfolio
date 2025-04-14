package br.saucedemowebauto.steps;

import java.util.Properties;

import org.junit.Assert;

import br.saucedemowebauto.dto.LoginDto;
import br.saucedemowebauto.pages.LoginPage;
import br.saucedemowebauto.selenium.SeConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private final LoginPage page = new LoginPage();

    @Given("usuario acessa o site Swag Labs")
    public void usuarioAcessaOSiteSwagLabs() {
        String swagLabsUrlKey = "selenium.application.test";
        Properties properties = SeConfig.getSeConfig().getProperties();
        SeConfig.getSeConfig().getWebDriver().get(properties.getProperty(swagLabsUrlKey));
    }

    @When("usuario faz login com os seguintes dados")
    public void usuarioFazLoginComOsSeguintesDados(LoginDto dto) {
        page.login(dto);
    }

    @Then("a mensagem {string} e apresentada na pagina Login")
    public void aMensagemEApresentadaNaPaginaLogin(String messageExpected) {
        Assert.assertEquals(messageExpected, page.getErrorText());
    }

}
