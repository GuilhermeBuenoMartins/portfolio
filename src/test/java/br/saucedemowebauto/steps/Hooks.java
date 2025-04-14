package br.saucedemowebauto.steps;

import java.lang.reflect.Type;

import br.saucedemowebauto.dto.ProductDto;
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

    @AfterAll
    public static void After() {
        if (SeConfig.getSeConfig() != null) {
            SeConfig.getSeConfig().closeWebDriver();
        }
    }

}
