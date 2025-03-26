package br.saucedemowebauto.pages;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.saucedemowebauto.dto.enums.MenuLateral;
import br.saucedemowebauto.selenium.SeWindow;

public class ProductsPage {

    private final By productsTitle = By.xpath("//*[@data-test='title']");

    private final By abrirMenuLateralButton = By.xpath("//*[@id='react-burger-menu-btn']");

    private final By opcoesMenuLateralLinks = By.xpath("//*[@class='bm-item menu-item']");

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
     * @param menuLateral opção do Menu Lateral da página <i>Products</i>.
     */
    public void selectInMenuLateral(MenuLateral menuLateral) {
        SeWindow.waitToBeDisplayed(abrirMenuLateralButton, 3).click();
        List<WebElement> webElements = SeWindow.findElements(opcoesMenuLateralLinks);
        Optional<WebElement> optional = webElements.stream().
            filter(webElement -> webElement.getText().equals(menuLateral.getText())).
            findFirst();
        optional.get().click();
    }
}
