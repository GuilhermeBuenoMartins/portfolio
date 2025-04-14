package br.saucedemowebauto.steps;

import java.util.Random;

import org.junit.Assert;

import br.saucedemowebauto.dto.ProductDto;
import br.saucedemowebauto.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductsSteps {

    private final ProductsPage page = new ProductsPage();

    @Given("usuario escolhe produto {string} na pagina Products")
    public void usuarioEscolheProdutoNaPaginaProducts(String name) {
        if (name.isEmpty()) {
            int index = new Random().nextInt(page.getQuantityOfProducts());
            Hooks.productDto = page.getProductByIndex(index);
        } else {
            Hooks.productDto = page.getProductByName(name);
        }
    }

    @When("usuario acessa o produto escolhido na pagina Products")
    public void usuarioAcessaOProdutoEscolhidoNaPaginaProducts() {
        page.accessProductByName(Hooks.productDto.getName());
    }

    @Then("usuario e direcionado a pagina Products")
    public void usuarioEDirecionadoAPaginaProducts() {
        Assert.assertEquals("Products", page.getTitle());
    }

    @Then("os detalhes do produto persistem ao serem apresentados individualmente")
    public void osDetalhesDoProdutoPersistemAoSeremApresentadosIndividualmente() {
        int quantityOfProducts = page.getQuantityOfProducts();
        Assert.assertEquals(1, quantityOfProducts);
        Assert.assertEquals(Hooks.productDto, page.getProductByIndex(0));
    }

}
