package br.saucedemowebauto.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import br.saucedemowebauto.dto.ProdutoDto;
import br.saucedemowebauto.dto.enums.MenuLateral;
import br.saucedemowebauto.selenium.SeWindow;

public class ProductsPage {

    private final By productsTitle = By.xpath("//*[@data-test='title']");

    private final By abrirMenuLateralButton = By.xpath("//*[@id='react-burger-menu-btn']");

    private final By opcoesMenuLateralLinks = By.xpath("//*[@class='bm-item menu-item']");

    private final By fecharMenuLateralButton = By.xpath("//*[@id='react-burger-cross-btn']");

    private final By produtoDivs = By.xpath("//*[@class='inventory_item']");

    private final By nomeProdutoLabels = By.xpath(".//*[@class='inventory_item_name ']");

    private final By descricaoProdutoLabels = By.xpath(".//*[@class='inventory_item_desc']");

    private final By precoProdutoLabels = By.xpath(".//*[@class='inventory_item_price']");

    private final By addToCartButtons = By.xpath(".//*[@class='btn btn_primary btn_small btn_inventory ']");

    private final By removeButtons = By.xpath(".//*[@class='btn btn_secondary btn_small btn_inventory ']");

    private final By numeroCarrinhoLabel = By.xpath("//*[@class='shopping_cart_badge']");

    public ProductsPage() {
    }

    /**
     * @return título da página <i>Products</i>
     */
    public String getProductsTitle() {
        return SeWindow.waitToBeDisplayed(productsTitle, 3).getText();
    }

    /**
     * Seleciona uma das opções do Menu Lateral da página <i>Products</i>.
     * 
     * @param menuLateral opção do Menu Lateral da página <i>Products</i>.
     */
    public void selectInMenuLateral(MenuLateral menuLateral) {
        if (!isMenuLateralOpened()) {
            SeWindow.findElement(abrirMenuLateralButton).click();
            SeWindow.takeScreenshot();
        }
        List<WebElement> webElements = SeWindow.findElements(opcoesMenuLateralLinks);
        Optional<WebElement> optional = webElements.stream().filter(
                webElement -> webElement.getText().equals(menuLateral.getText())).findFirst();
        optional.get().click();
        SeWindow.takeScreenshot();
    }

    private boolean isMenuLateralOpened() {
        try {
            WebElement menuLateral = SeWindow.waitToBeDisplayed(fecharMenuLateralButton, 3);
            if (menuLateral != null) {
                return menuLateral.isEnabled();
            }
        } catch (TimeoutException exception) {
        }
        return false;
    }

    /**
     * @return Retorna uma lista de produtos da página Products.
     */
    public List<ProdutoDto> getProdutoDtos() {
        List<ProdutoDto> produtoDtos = new ArrayList<>();
        List<WebElement> produtos = SeWindow.findElements(produtoDivs);
        for (int i = 0; i < produtos.size(); i++) {
            ProdutoDto produtoDto = new ProdutoDto();
            produtoDto.setNome(produtos.get(i).findElement(nomeProdutoLabels).getText());
            produtoDto.setDescricao(produtos.get(i).findElement(descricaoProdutoLabels).getText());
            String price = produtos.get(i).findElement(precoProdutoLabels).getText().replace("$", "");
            produtoDto.setPreco(Double.parseDouble(price.trim()));
            produtoDtos.add(produtoDto);
        }
        return produtoDtos;
    }

    /**
     * Retorna informações do produto da página `Products` a parir do nome do
     * produto.
     * 
     * @param nomeProduto nome do produto.
     * @return informaçẽos do produto.
     */
    public ProdutoDto getProdutoDto(String nomeProduto) {
        WebElement produto = filterProdutoBy(nomeProduto);
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setNome(produto.findElement(nomeProdutoLabels).getText());
        produtoDto.setDescricao(produto.findElement(descricaoProdutoLabels).getText());
        String price = produto.findElement(precoProdutoLabels).getText().replace("$", "");
        produtoDto.setPreco(Double.parseDouble(price.trim()));
        return produtoDto;
    }

    private WebElement filterProdutoBy(String nomeProduto) {
        List<WebElement> produtos = SeWindow.findElements(produtoDivs);
        for (int i = 0; i < produtos.size(); i++) {
            String target = produtos.get(i).findElement(nomeProdutoLabels).getText();
            if (target.trim().toLowerCase().equals(nomeProduto.trim().toLowerCase())) {
                return produtos.get(i);
            }
        }
        return null;
    }

    /**
     * Clica no botão <i>Add to Cart</id> do produto na página Products.
     * 
     * @param nomeProduto nome do produto.
     */
    public void clickOnAddToCart(String nomeProduto) {
        WebElement produto = filterProdutoBy(nomeProduto);
        produto.findElement(addToCartButtons).click();
        SeWindow.takeScreenshot();
    }

    /**
     * Clica no botão <i>Remove</id> do produto na página Products.
     * 
     * @param nomeProduto nome do produto.
     */
    public void clickOnRemove(String nomeProduto) {
        WebElement produto = filterProdutoBy(nomeProduto);
        produto.findElement(removeButtons).click();
        SeWindow.takeScreenshot();
    }

    /**
     * Acessa detalhes do produto na página Products.
     * 
     * @param nomeProduto nome do produto.
     */
    public void accessTo(String nomeProduto) {
        WebElement produto = filterProdutoBy(nomeProduto);
        produto.findElement(nomeProdutoLabels).click();
        SeWindow.takeScreenshot();
    }

    /**
     * @return retorna o número de produtos no carrinho de compras.
     */
    public String getNumeroCarrinhoLabel() {
        return SeWindow.findElement(numeroCarrinhoLabel).getText();
    }

    /**
     * Retorna se o produto está adicionado no carrinho de compras.
     * 
     * @param nomeProduto web elemento do produto.
     * @return <code>true</code> se o produto está removido,
     *         caso contrário, <code>false</code>.
     */
    public boolean produtoAdicionado(String nomeProduto) {
        WebElement produto = filterProdutoBy(nomeProduto);
        try {
            produto.findElement(removeButtons);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Retorna se o produto está removido do carrinho de compras.
     * 
     * @param nomeProduto nome do produto.
     * @return <code>true</code> se o produto está removido,
     *         caso contrário, <code>false</code>.
     */
    public boolean produtoRemovido(String nomeProduto) {
        WebElement produto = filterProdutoBy(nomeProduto);
        try {
            produto.findElement(addToCartButtons);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
