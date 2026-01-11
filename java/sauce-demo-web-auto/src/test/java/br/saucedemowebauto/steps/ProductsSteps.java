package br.saucedemowebauto.steps;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.Assert;

import br.saucedemowebauto.dto.ProductDto;
import br.saucedemowebauto.pages.ProductsPage;
import br.saucedemowebauto.pages.TopMenu;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.types.Hook;

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

    @Given("usuario escolhe produtos na pagina Products")
    public void usuarioEscolheProdutosNaPaginaProducts(DataTable productNames) {
        Hooks.productDtos = new HashSet<>();
        productNames.asList().stream().forEach(name -> {
            ProductDto dto = new ProductDto();
            dto.setName(name);
            Hooks.productDtos.add(dto);
        });
        Hooks.productDtos.stream().forEach(dto -> {
            ProductDto product = page.getProductByName(dto.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
        });
    }

    // #region When steps

    @When("usuario acessa o produto escolhido na pagina Products")
    public void usuarioAcessaOProdutoEscolhidoNaPaginaProducts() {
        page.accessProductByName(Hooks.productDto.getName());
    }

    @When("usuario adicionar produto na pagina Products ao carrinho")
    public void usuarioAdicionarProdutoNaPaginaProductsAoCarrinho() {
        page.addProductByName(Hooks.productDto.getName());
    }

    @When("usuario adicionar produto escolhido")
    public void usuarioAdicionarProdutoEscolhido() {
        page.addProductByName(Hooks.productDto.getName());
    }

    @When("usuario adicionar produtos na pagina Products ao carrinho")
    public void usuarioAdicionarProdutosNaPaginaProductsAoCarrinho() {
        Hooks.productDtos.stream().forEach(product -> page.addProductByName(product.getName()));
    }

    // #endregion
    // #region Then stpes

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

    @Then("o botao Remove substitui o botao Add to Cart")
    public void oBotaoRemoveSubstituiOBotaoAddToCart() {
        List<ProductDto> removableProducts = page.getRemovableProducts();
        Assert.assertEquals(1, removableProducts.size());
        Assert.assertEquals(Hooks.productDto, removableProducts.get(0));
    }

    @Then("o icone de carrinho apresenta a quantidade produtos adicionados")
    public void oIconeDeCarrinhoApresentaAQuantidadeProdutosAdicionados() {
        Assert.assertEquals(Hooks.productDtos.size(), Integer.parseInt(new TopMenu().getCartItemCountLabel()));
    }

    // #endregion

}
