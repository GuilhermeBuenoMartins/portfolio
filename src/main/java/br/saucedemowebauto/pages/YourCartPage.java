package br.saucedemowebauto.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.saucedemowebauto.dto.ProductDto;
import br.saucedemowebauto.selenium.SeWindow;

public class YourCartPage {

    private final By youCartLabel = By.xpath("//*[@data-test='title']");

    private final By productDivs = By.xpath("//*[@data-test='inventory-item']");

    private final By productNameLabel = By.xpath(".//*[@data-test='inventory-item-name']");

    private final By productDescriptionText = By.xpath(".//*[@data-test='inventory-item-desc']");

    private final By productPriceLabel = By.xpath(".//*[@data-test='inventory-item-price']");

    /**
     * Retorna o texto do título da página "Your Cart".
     * 
     * @return texto do título da página.
     */
    public String getTiltle() {
        return SeWindow.findElement(youCartLabel).getText();
    }

    /**
     * @return a quantidade de produtos na página "Products".
     */
    public int getQuantityOfProducts() {
        return SeWindow.findElements(productDivs).size();
    }

    /**
     * @return os produtos do carrinho.
     */
    public List<ProductDto> getProductDtos() {
        List<WebElement> products = SeWindow.findElements(productDivs);
        List<ProductDto> dtos = new ArrayList<>();
        products.stream().forEach(product -> dtos.add(convertToDto(product)));
        return dtos;
    }

    private ProductDto convertToDto(WebElement webElement) {
        ProductDto dto = new ProductDto();
        dto.setName(webElement.findElement(productNameLabel).getText());
        dto.setDescription(webElement.findElement(productDescriptionText).getText());
        dto.setPrice(webElement.findElement(productPriceLabel).getText());
        return dto;
    }
}