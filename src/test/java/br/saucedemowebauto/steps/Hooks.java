package br.saucedemowebauto.steps;

import br.saucedemowebauto.selenium.SeConfig;
import br.saucedemowebauto.selenium.SeWindow;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public static void before(Scenario scenario) {
        SeConfig.instantiate(scenario);
        SeWindow.maximize();
    }

    @AfterAll
    public static void AfterAll() {
        SeConfig.getSeConfig().closeWebDriver();
    }

    @DataTableType
    public String stringType(String cell) {
        return cell == null? "": cell;
    }
    
}
