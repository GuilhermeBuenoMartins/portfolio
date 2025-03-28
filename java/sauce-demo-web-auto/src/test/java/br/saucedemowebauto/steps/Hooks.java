package br.saucedemowebauto.steps;

import br.saucedemowebauto.dto.LoginDto;
import br.saucedemowebauto.dto.enums.MenuLateral;
import br.saucedemowebauto.pages.LoginPage;
import br.saucedemowebauto.pages.ProductsPage;
import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before(order = 1)
    public static void before(Scenario scenario) {
        SeConfig.instantiate(scenario);
        SeWindow.maximize();
    }

    @Before(order = 2, value = "not @login or not @menu_lateral")
    public static void reset() {
        SeConfig.getSeConfig().getWebDriver().get("https://www.saucedemo.com/");
        LoginDto loginDto = new LoginDto("standard_user", "secret_sauce");
        new LoginPage().login(loginDto);
        ProductsPage productsPage = new ProductsPage();
        productsPage.selectInMenuLateral(MenuLateral.RESET_APP_STATE);
        productsPage.selectInMenuLateral(MenuLateral.LOGOUT);
    }

    @AfterAll
    public static void After() {
        if (SeConfig.getSeConfig() != null) {
            SeConfig.getSeConfig().closeWebDriver();
        }
    }

    @DataTableType
    public String stringType(String cell) {
        return cell == null ? "" : cell;
    }

}
