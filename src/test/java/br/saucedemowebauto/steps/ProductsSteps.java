package br.saucedemowebauto.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import br.saucedemowebauto.dto.ProdutoDto;
import br.saucedemowebauto.dto.enums.MenuLateral;
import br.saucedemowebauto.pages.ProductsPage;
import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductsSteps {

    private ProductsPage productsPage = new ProductsPage();

    private ProdutoDto produtoDto;

    @Given("usuario adiciona produto {string} ao carrinho de compras na pagina `Products`")
    public void usuarioAdicionaProdutoAoCarrinhoDeComprasNaPaginaProducts(String nomeProduto) {
        List<ProdutoDto> produtos  = productsPage.getProdutoDtos();
        if (nomeProduto.equals("")) {
            int randomIndex = new Random().nextInt(produtos.size());
            produtoDto = produtos.get(randomIndex);
            nomeProduto = produtoDto.getNome();
        } else {
            produtoDto = productsPage.getProdutoDto(nomeProduto);
        }
        productsPage.clickOnAddToCart(nomeProduto);
    }

    @Given("usuario adiciona produtos ao carrinho de compras na pagina `Products`")
    public void usuarioAdicionaProdutosAoCarrinhoDeComprasNaPaginaProducts(DataTable nomeProdutosTable) {
        List<String> nomeProdutos = nomeProdutosTable.asList();
        for (String nomeProduto: nomeProdutos) {
            productsPage.clickOnAddToCart(nomeProduto);
        }
    }

    @When("usuario acessa a opcao {string} na pagina Products")
    public void usuarioAcessaAOpcaoNaPaginaProducts(String opcao) {
        MenuLateral menuLateral = MenuLateral.getMenuLateral(opcao);
        productsPage.selectInMenuLateral(menuLateral);
    }

    @When("usuario adicionar produto {string} ao carrinho de compras na pagina `Products`")
    public void usuarioAdicionarProdutoAoCarrinhoDeComprasNaPaginaProducts(String nomeProduto) {
        List<ProdutoDto> produtos  = productsPage.getProdutoDtos();
        if (nomeProduto.equals("")) {
            int randomIndex = new Random().nextInt(produtos.size());
            nomeProduto = produtos.get(randomIndex).getNome();
        }
        produtoDto = productsPage.getProdutoDto(nomeProduto);
        productsPage.clickOnAddToCart(nomeProduto);
    }

    @When("usuario adicionar produtos ao carrinho de compras na pagina `Products`")
    public void usuarioAdicionarProdutosAoCarrinhoDeComprasNaPaginaProducts(DataTable nomeProdutosTable) {
        List<String> nomeProdutos = nomeProdutosTable.asList();
        for (String nomeProduto: nomeProdutos) {
            productsPage.clickOnAddToCart(nomeProduto);
        }
    }

    @When("usuario remove produto do carrinho de compras na pagina `Products`")
    public void usuarioRemoveProdutoDoCarrinhoDeComprasNaPáginaProducts() {
        productsPage.clickOnRemove(produtoDto.getNome());
    }

    @When("usuario remove produtos do carrinho de compras na pagina `Products`")
    public void usuarioRemoveProdutosDoCarrinhoDeComprasNaPáginaProducts(DataTable nomeProdutosTable) {
        List<String> nomeProdutos = nomeProdutosTable.asList();
        for (String nomeProduto: nomeProdutos) {
            productsPage.clickOnRemove(nomeProduto);
        }
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

    @Then("o produto e adicionado ao carrinho de compras que apresenta {int} produto")
    public void oProdutoEAdicionadoAoCarrinhoDeComprasQueApresentaProduto(Integer qtdProdutoEsperado) {
        String nomeProduto = produtoDto.getNome();
        Integer qtdProdutoAtual = Integer.parseInt(productsPage.getNumeroCarrinhoLabel());
        assertTrue(productsPage.produtoAdicionado(nomeProduto));
        assertEquals(qtdProdutoEsperado, qtdProdutoAtual);
    }

    @Then("os produtos sao adicionados ao carrinho de compras que apresenta {int} produto")
    public void osProdutosSaoAdicionadosAoCarrinhoDeComprasQueApresentaProduto(Integer qtdProdutoEsperado) {
        Integer qtdProdutoAtual = Integer.parseInt(productsPage.getNumeroCarrinhoLabel());
        assertEquals(qtdProdutoEsperado, qtdProdutoAtual);
    }

    @Then("o produto e removido do carrinho de compras")
    public void oProdutoERemovidoDoCarrinhoDeCompras() {
        String nomeProduto = produtoDto.getNome();
        assertTrue(productsPage.produtoRemovido(nomeProduto));
    }

    @Then("os produtos sao removidos do carrinho de compras que apresenta {int} produto")
    public void osProdutosSaoRemovidosDoCarrinhoDeComprasQueApresentaProduto(Integer qtdProdutoEsperado) {
        Integer qtdProdutoAtual = Integer.parseInt(productsPage.getNumeroCarrinhoLabel());
        assertEquals(qtdProdutoEsperado, qtdProdutoAtual);
    }

}
