package br.saucedemowebauto.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import br.saucedemowebauto.dto.LoginDto;
import br.saucedemowebauto.pages.LoginPage;
import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private LoginPage loginPage = new LoginPage();

    @Given("acesso ao site {string}")
    public void acessoAoSite(String url) {
        SeConfig.getSeConfig().getWebDriver().get(url);
        SeWindow.takeScreenshot();
    }

    @When("usuario faz login com os seguintes dados")
    public void usuarioFazLoginComOsSeguintesDados(DataTable dataTable) {
        ObjectMapper mapper = new ObjectMapper();
        LoginDto dto = mapper.convertValue(dataTable.transpose().asMap(), LoginDto.class);
        loginPage.login(dto);
    }

    @Then("a mensagem {string} e apresentada na pagina Login")
    public void aMensagemEApresentadaNaPaginaLogin(String messageErrorExpected) {
        SeWindow.takeScreenshot();
        final String urlPageNotExpected = "https://www.saucedemo.com/inventory.html";
        assertEquals(messageErrorExpected, loginPage.getErrorText());
        assertNotEquals(urlPageNotExpected, SeConfig.getSeConfig().getWebDriver().getCurrentUrl());
    }

    @Then("usuario e direcionado a pagina Login")
    public void usuarioEDirecionadoAPaginaLogin() {
        final String titlePageExpected = "Swag Labs";
        final String urlPageExpected = "https://www.saucedemo.com/";
        SeWindow.takeScreenshot();
        assertEquals(titlePageExpected, loginPage.getSwagLabsTitle());
        assertEquals(urlPageExpected, SeConfig.getSeConfig().getWebDriver().getCurrentUrl());
    }

}
