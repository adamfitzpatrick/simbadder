package net.muneris.simbadder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
public class ConfiguratorTestIT extends SpringAwareContextIT {

    @Autowired
    RestTemplate restTemplate;
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        assertThat(restTemplate, is(instanceOf(RestTemplate.class)));
    }

}
