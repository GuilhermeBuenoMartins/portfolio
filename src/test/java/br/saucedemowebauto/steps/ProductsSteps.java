package br.saucedemowebauto.steps;

import static org.junit.Assert.assertEquals;

import br.saucedemowebauto.pages.ProductsPage;
import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.java.en.Then;

public class ProductsSteps {

    private ProductsPage productsPage = new ProductsPage();

    @Then("usuario e direcionado a pagina Products")
    public void usuarioEDirecionadoAPaginaProducts() {
        final String titlePageExpected = "Products";
        final String urlPageExpected = "https://www.saucedemo.com/inventory.html";
        SeWindow.takeScreenshot();
        assertEquals(titlePageExpected, productsPage.getProductsTitle());
        assertEquals(urlPageExpected, SeConfig.getSeConfig().getWebDriver().getCurrentUrl());
    }

}
