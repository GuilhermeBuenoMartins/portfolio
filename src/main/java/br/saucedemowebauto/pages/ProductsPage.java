package br.saucedemowebauto.pages;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v120.page.Page.SetWebLifecycleStateState;

import br.saucedemowebauto.dto.ProductDto;
import br.saucedemowebauto.selenium.SeWindow;

public class ProductsPage {

    private final By productsLabel = By.xpath("//*[@class='title']");

    private final By productDivs = By.xpath("//*[@data-test='inventory-item']");

    private final By nameProductLabel = By.xpath(".//*[@data-test='inventory-item-name']");

    private final By descriptionProductText = By.xpath(".//*[@data-test='inventory-item-desc']");

    private final By priceProductLabel = By.xpath(".//*[@class='inventory-item-price']");

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
        ProductDto dto = new ProductDto();
        dto.setName(productList.get(index).findElement(nameProductLabel).getText());
        dto.setDescription(productList.get(index).findElement(nameProductLabel).getText());
        dto.setPrice(productList.get(index).findElement(nameProductLabel).getText());
        return dto;
    }

    /**
     * Retorna um produto da página "Products" a partir do seu nome.
     * 
     * @param name nome do produto.
     * @return produto da página.
     */
    public ProductDto getProductByName(String name) {
        List<WebElement> productList = SeWindow.findElements(productDivs);
        Optional<WebElement> optional = productList.stream().filter(
            p -> p.findElement(nameProductLabel).getText().equals(name)).findFirst();
        if (optional.isEmpty()) {
            String message = "The name product " + name + "could not be found.";
            throw new IllegalArgumentException(message);
        }
        ProductDto dto = new ProductDto();
        dto.setName(optional.get().findElement(nameProductLabel).getText());
        dto.setDescription(optional.get().findElement(descriptionProductText).getText());
        dto.setPrice(optional.get().findElement(priceProductLabel).getText());
        return dto;
    }

    /**
     * Acessa um produto da página "Products" a partir de seu nome.
     * @param name nome do produto.
     */
    public void accessProductByName(String name) {
        List<WebElement> productList = SeWindow.findElements(productDivs);
        Optional<WebElement> optional = productList.stream().filter(
            p -> p.findElement(nameProductLabel).getText().equals(name)).findFirst();
        if (optional.isEmpty()) {
            String message = "The name product " + name + "could not be found.";
            throw new IllegalArgumentException(message);
        }
        optional.get().findElement(nameProductLabel).click();
        SeWindow.takeScreenshot();
    }
    
}
