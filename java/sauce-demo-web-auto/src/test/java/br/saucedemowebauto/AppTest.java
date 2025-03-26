package br.saucedemowebauto;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

/**
 * Cucumber runner.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    tags = "@menu_lateral and @products",
    snippets = SnippetType.CAMELCASE,
    features = "src/test/resources",
    plugin = {"pretty", "html:target/cucumber/report.html"}
)
public class AppTest {

    
}
