package br.saucedemowebauto.steps;

import org.junit.Assert;

import br.saucedemowebauto.pages.ProductsPage;
import io.cucumber.java.en.Then;

public class ProductsSteps {

    private final ProductsPage page = new ProductsPage();

    @Then("usuario e direcionado a pagina Products")
    public void usuarioEDirecionadoAPaginaProducts() {
        Assert.assertEquals("Products", page.getTitle());
    }

}
