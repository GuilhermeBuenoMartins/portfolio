package br.saucedemowebauto.steps;

import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginSteps {

    @Given("acesso ao site {string}")
    public void acessoAoSite(String url) {
        SeConfig.getSeConfig().getWebDriver().get(url);
        SeWindow.takeScreenshot();
    }

    @When("usuario faz login com dados validos")
    public void usuarioFazLoginComDadosValidos(DataTable dataTable) {
    }
    
}
