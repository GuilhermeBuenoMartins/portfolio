package br.saucedemowebauto.pages;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.saucedemowebauto.dto.enums.SideMenu;
import br.saucedemowebauto.selenium.SeWindow;

public class TopMenu {

    private final By cartIcon = By.xpath("//*[@data-test='shopping-cart-link']");

    private final By cartItemCountLabel = By.xpath("//*[@data-test='shopping-cart-badge']");

    private final By sideMenuButton = By.xpath("//*[@id='react-burger-menu-btn']");

    private final By sideMenuOptionLinks = By.xpath("//*[contains(@data-test, 'sidebar-link')]");

    private final By closeSideMenu = By.xpath("//*[@id='react-burger-cross-btn']");

    /**
     * Retorna o número de itens exibido no ícone do carrinho de compras.
     * Se o número não estiver presente, retorna "0".
     *
     * @return número de itens como {@code String}, ou "0" se não existir.
     */
    public String getCartItemCountLabel() {
        if (SeWindow.exist(cartItemCountLabel)) {
            return SeWindow.findElement(cartItemCountLabel).getText();
        }
        return "0";
    }

    /**
     * Clica no ícone do carrinho de compras no menu superior para ser direcionado
     * a página "Your Cart".
     */
    public void accessYourCart() {
        SeWindow.findElement(cartIcon).click();
        SeWindow.takeScreenshot();
    }

    /**
     * Abre ou fecha o menu lateral conforme o valor do parâmetro {@code open}.
     * O comando é ignorado se o estado atual do menu já corresponder ao desejado.
     *
     * @param open {@code true} para abrir, {@code false} para fechar.
     */
    public void toggleSideMenu(boolean open) {
        if (open && !SeWindow.findElement(closeSideMenu).isDisplayed()) {
            SeWindow.findElement(sideMenuButton).click();
        }
        if (!open && SeWindow.findElement(closeSideMenu).isDisplayed()) {
            SeWindow.findElement(closeSideMenu).click();
        }
        SeWindow.takeScreenshot();
    }

    /**
     * Clica em um link das opções do menu lateral.
     * 
     * @param option 
     */
    public void clickInOption(SideMenu option) {
        List<WebElement> optionLinks = SeWindow.findElements(sideMenuOptionLinks);
        Optional<WebElement> optional = optionLinks.stream()
            .filter(link -> link.getText().equals(option.getText())).findFirst();
        if (optional.isEmpty()) {
            String message = "The option \"" + option + "\" could not be found.";
            throw new IllegalArgumentException(message);
        }
        optional.get().click();
        SeWindow.takeScreenshot();
    }

}
