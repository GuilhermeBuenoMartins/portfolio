package br.saucedemowebauto.steps;

import static org.junit.Assert.assertEquals;

import br.saucedemowebauto.dto.enums.MenuLateral;
import br.saucedemowebauto.pages.ProductsPage;
import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductsSteps {

    private ProductsPage productsPage = new ProductsPage();

    @When("usuario acessa a opcao {string} na pagina Products")
    public void usuarioAcessaAOpcaoNaPaginaProducts(String opcao) {
        MenuLateral menuLateral = MenuLateral.getMenuLateral(opcao);
        productsPage.selectInMenuLateral(menuLateral);
    }

    @Then("usuario e direcionado a pagina Products")
    public void usuarioEDirecionadoAPaginaProducts() {
        final String titlePageExpected = "Products";
        final String urlPageExpected = "https://www.saucedemo.com/inventory.html";
        SeWindow.takeScreenshot();
        assertEquals(titlePageExpected, productsPage.getProductsTitle());
        assertEquals(urlPageExpected, SeConfig.getSeConfig().getWebDriver().getCurrentUrl());
    }

    @Then("usuario e direcionado a pagina SauceLabs")
    public void usuarioEDirecionadoAPaginaSauceLabs() {
        final String urlPageExpected = "https://saucelabs.com/";
        SeWindow.takeScreenshot();
        assertEquals(urlPageExpected, SeConfig.getSeConfig().getWebDriver().getCurrentUrl());
    }

}
