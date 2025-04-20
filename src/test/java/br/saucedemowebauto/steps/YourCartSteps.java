package br.saucedemowebauto.steps;

import java.util.List;

import org.junit.Assert;

import br.saucedemowebauto.dto.ProductDto;
import br.saucedemowebauto.pages.TopMenu;
import br.saucedemowebauto.pages.YourCartPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class YourCartSteps {

    @When("usuario acessar a pagina Your Cart no Menu Superior")
    public void usuarioAcessarAPaginaYourCartNoMenuSuperior() {
        new TopMenu().accessYourCart();
    }

    @Then("os produtos adicionados aparecem listados na pagina Your Cart")
    public void osProdutosAdicionadosAparecemListadosNaPaginaYourCart() {
        List<ProductDto> products = new YourCartPage().getProductDtos();
        Assert.assertArrayEquals(Hooks.productDtos.toArray(), products.toArray());
    }

}
