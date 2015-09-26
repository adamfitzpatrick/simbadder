package net.muneris.simbadder;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.webAppContextSetup;

import com.jayway.restassured.RestAssured;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, Configurator.class })
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Ignore
public class SpringAwareContextIT {

    @Value("${local.server.port}")
    int port;

    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Before
    public void makeContext() {
        RestAssured.port = port;
        webAppContextSetup(webApplicationContext);
    }
}
