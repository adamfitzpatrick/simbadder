package net.muneris.simbadder;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.web.client.RestTemplate;

public class Application implements CommandLineRunner {

	final static Logger log = Logger.getLogger(Application.class);
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class);
	}
	
    @Override
    public void run(String... strings) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);
        log.info(quote);
    }

}
