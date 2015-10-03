package net.muneris.simbadder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class ConfiguratorTest {

    Configurator configurator;
    @Before
    public void setUp() throws Exception {
        configurator = new Configurator();
    }

    @Test
    public void testRestTemplate() {
        assertThat(configurator.restTemplate(), is(instanceOf(RestTemplate.class)));
    }

}
