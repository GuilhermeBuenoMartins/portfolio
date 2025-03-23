package br.saucedemowebauto.pages;

import org.openqa.selenium.By;

import br.saucedemowebauto.selenium.SeWindow;

public class ProductsPage {

    private final By productsTitle = By.xpath("//*[@data-test='title']");

    public ProductsPage() {
    }

    public String getProductsTitle() {
        return SeWindow.waitToBeDisplayed(productsTitle, 3).getText();
    }
    
}
