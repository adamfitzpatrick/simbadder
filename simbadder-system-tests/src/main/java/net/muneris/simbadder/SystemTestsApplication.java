package net.muneris.simbadder;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "html:build/reports/cucumber-html/" },
features = {"classpath:features" }, snippets = SnippetType.CAMELCASE)
public class SystemTestsApplication {

    public static void main(String[] args) {
        JUnitCore.main("net.muneris.simbadder.SystemTestsApplication");
    }

}
