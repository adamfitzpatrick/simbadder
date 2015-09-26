package net.muneris.simbadder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author adam.fitzpatrick
 */
@Configuration
public class Configurator {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
