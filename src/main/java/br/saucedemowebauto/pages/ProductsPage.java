package br.saucedemowebauto.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.saucedemowebauto.dto.ProductDto;
import br.saucedemowebauto.selenium.SeWindow;

public class ProductsPage {

    private final By productsLabel = By.xpath("//*[@class='title']");

    private final By productDivs = By.xpath("//*[@data-test='inventory-item']");

    private final By productNameLabel = By.xpath(".//*[@data-test='inventory-item-name']");

    private final By productDescriptionText = By.xpath(".//*[@data-test='inventory-item-desc']");

    private final By productPriceLabel = By.xpath(".//*[@data-test='inventory-item-price']");

    private final By removableProductDivs = By.xpath("//*[contains(@data-test, 'remove')]/../../..");

    private final By addableProductDivs = By.xpath("//*[contains(@data-test, 'add-to-cart')]/../../..");

    private final By addToCartButton = By.xpath(".//*[contains(@data-test, 'add-to-cart')]");

    private final By removeButton = By.xpath(".//*[contains(@data-test, 'remove')]");
    
    /**
     * Retorna o texto do título da página "Products".
     * 
     * @return texto do título da página.
     */
    public String getTitle() {
        return SeWindow.findElement(productsLabel).getText();
    }

    /**
     * @return a quantidade de produtos na página "Products".
     */
    public int getQuantityOfProducts() {
        return SeWindow.findElements(productDivs).size();
    }

    /**
     * Retorna um produto da página "Products" a partir do seu índice no DOM.
     * 
     * @param index índice DOM.
     * @return produto da página.
     */
    public ProductDto getProductByIndex(int index) {
        List<WebElement> productList = SeWindow.findElements(productDivs);
        return convertToDto(productList.get(index));
    }

    /**
     * Retorna um produto da página "Products" a partir do seu nome.
     * 
     * @param name nome do produto.
     * @return produto da página.
     */
    public ProductDto getProductByName(String name) {
        WebElement product = searchProdutByName(name);
        return convertToDto(product);
    }

    /**
     * Retorna a lista de produtos que foram adicionados ao carrinho de compras.
     * Esses produtos são identificados na página "Products" pelo botão "Remove",
     * indicando que já estão no carrinho.
     *
     * @return lista de produtos atualmente no carrinho.
     */
    public List<ProductDto> getRemovableProducts() {
        List<ProductDto> dtos = new ArrayList<>();
        List<WebElement> removableProducts = SeWindow.findElements(removableProductDivs);
        removableProducts.stream().forEach(product -> dtos.add(convertToDto(product)));
        return dtos;
    }

    /**
     * Retorna a lista de produtos que não estão adicionados ao carrinho.
     * Esses produtos são identificados na página "Products" pelo botão
     * "Add to cart".
     *
     * @return lista de produtos disponíveis para adição ao carrinho.
     */
    public List<ProductDto> getAddableProducts() {
        List<ProductDto> dtos = new ArrayList<>();
        List<WebElement> addableProducts = SeWindow.findElements(addableProductDivs);
        addableProducts.stream().forEach(product -> dtos.add(convertToDto(product)));
        return dtos;
    }

    private ProductDto convertToDto(WebElement webElement) {
        ProductDto dto = new ProductDto();
        dto.setName(webElement.findElement(productNameLabel).getText());
        dto.setDescription(webElement.findElement(productDescriptionText).getText());
        dto.setPrice(webElement.findElement(productPriceLabel).getText());
        return dto;
    }

    /**
     * Acessa um produto da página "Products" a partir de seu nome.
     * 
     * @param name nome do produto.
     */
    public void accessProductByName(String name) {
        WebElement product = searchProdutByName(name);
        product.findElement(productNameLabel).click();
        SeWindow.takeScreenshot();
    }

    /**
     * Adiciona um produto ao carrinho de compras a partir de seu nome.
     * 
     * @param name nome do produto.
     */
    public void addProductByName(String name) {
        WebElement product = searchProdutByName(name);
        product.findElement(addToCartButton).click();
        SeWindow.takeScreenshot();
    }

    /**
     * Remove um produto ao carrinho de compras a partir de seu nome.
     * 
     * @param name nome do produto.
     */
    public void removeProductByName(String name) {
        WebElement product = searchProdutByName(name);
        SeWindow.wait(1);
        product.findElement(removeButton).click();
        SeWindow.takeScreenshot();
    }

    private WebElement searchProdutByName(String name) {
        List<WebElement> productList = SeWindow.findElements(productDivs);
        Optional<WebElement> optional = productList.stream()
            .filter(p -> p.findElement(productNameLabel).getText().equals(name))
            .findFirst();
        if (optional.isPresent()) { return optional.get(); }
        String message = "The product \"" + name + "\" could not be found.";
        throw new IllegalArgumentException(message);
    }

}
