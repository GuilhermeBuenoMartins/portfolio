package br.saucedemowebauto.pages;

import org.openqa.selenium.By;

import br.saucedemowebauto.selenium.SeWindow;

public class ProductsPage {

    private final By productsLabel = By.xpath("//*[@class='title']");

    /**
     * Retorna o texto do título da página "Products".
     * 
     * @return texto do título da página.
     */
    public String getTitle() {
        return SeWindow.findElement(productsLabel).getText();
    }
    
}
