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

    @When("usuario remove o produto {string} na pagina Products")
    public void usuarioRemoveOProdutoNaPaginaProducts(String name) {
        if (name.isEmpty()) {
            int index = new Random().nextInt(Hooks.productDtos.size());
            Hooks.productDto = (ProductDto) Hooks.productDtos.toArray()[index];
        } else {
            Hooks.productDto = page.getProductByName(name);
        }
        page.removeProductByName(Hooks.productDto.getName());
        Hooks.productDtos.removeIf(product -> product.getName().equals(Hooks.productDto.getName()));
    }

    @When("usuario remove o produto {string} na pagina Products apos acessar Produto")
    public void usuarioRemoveOProdutoNaPaginaProductsAposAcessarProduto(String name) {
        if (name.isEmpty()) {
            int index = new Random().nextInt(Hooks.productDtos.size());
            Hooks.productDto = (ProductDto) Hooks.productDtos.toArray()[index];
        } else {
            Hooks.productDto = page.getProductByName(name);
        }
        page.accessProductByName(Hooks.productDto.getName());
        page.removeProductByName(Hooks.productDto.getName());
        Hooks.productDtos.removeIf(product -> product.getName().equals(Hooks.productDto.getName()));
    }

    @When("usuario remove o produtos na pagina Products")
    public void usuarioRemoveOProdutosNaPaginaProducts(DataTable productNames) {
        productNames.asList().stream().forEach(name -> {
            page.removeProductByName(name);
            Hooks.productDtos.removeIf(product -> product.getName().equals(name));
        });
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

    @Then("o produto removido tem o botao Remove substituido pelo botao Add to Cart")
    public void oProdutoRemovidoTemOBotaoRemoveSubstituidoPeloBotaoAddToCart() {
        List<ProductDto> addableProducts = page.getAddableProducts();
        Assert.assertTrue(addableProducts.contains(Hooks.productDto));
    }

    @Then("os demais produtos mantem o botao Remove")
    public void osDemaisProdutosMantemOBotaoRemove() {
        List<ProductDto> removableProducts = page.getRemovableProducts();
        Assert.assertArrayEquals(Hooks.productDtos.toArray(), removableProducts.toArray());
    }

    @Then("o botao Add to Cart substitui o botao Remove")
    public void oBotaoAddToCartSubstituiOBotaoRemove() {
        List<ProductDto> addableProducts = page.getAddableProducts();
        Assert.assertEquals(1, addableProducts.size());
        Assert.assertEquals(Hooks.productDto, addableProducts.get(0));
    }

    // #endregion

}
