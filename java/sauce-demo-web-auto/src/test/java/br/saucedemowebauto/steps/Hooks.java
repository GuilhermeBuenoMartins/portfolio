package br.saucedemowebauto.steps;

import java.lang.reflect.Type;
import java.util.Properties;
import java.util.Set;

import br.saucedemowebauto.dto.LoginDto;
import br.saucedemowebauto.dto.ProductDto;
import br.saucedemowebauto.dto.enums.SideMenu;
import br.saucedemowebauto.pages.LoginPage;
import br.saucedemowebauto.pages.TopMenu;
import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.Scenario;

public class Hooks {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    protected static ProductDto productDto;

    protected static Set<ProductDto> productDtos;

    @DataTableType
    public String stringType(String cell) {
        return cell == null ? "" : cell;
    }

    @DefaultParameterTransformer()
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @Before(order = 1)
    public static void before(Scenario scenario) {
        SeConfig.instantiate(scenario);
        SeWindow.maximize();
    }

    @Before(order = 2, value = "@reset_app_state")
    public static void resetApp() {
        final LoginDto loginDto = new LoginDto("standard_user", "secret_sauce");
        final TopMenu topMenu = new TopMenu();
        final String swagLabsUrlKey = "selenium.application.test";
        Properties properties = SeConfig.getSeConfig().getProperties();
        SeConfig.getSeConfig().getWebDriver().get(properties.getProperty(swagLabsUrlKey));
        new LoginPage().login(loginDto);
        topMenu.toggleSideMenu(true);
        topMenu.clickInOption(SideMenu.RESET_APP_STATE);
        topMenu.clickInOption(SideMenu.LOGOUT);
    }

    @AfterAll
    public static void After() {
        if (SeConfig.getSeConfig() != null) {
            SeConfig.getSeConfig().closeWebDriver();
        }
    }

}
