package com.mikaelfrancoeur.demostockticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource(value = "keys.properties")
public class DemoStockTickerApplication {

    final Environment env;
    Logger logger = LoggerFactory.getLogger(DemoStockTickerApplication.class);

    public DemoStockTickerApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoStockTickerApplication.class, args);
    }


    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }


    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext applicationContext, StockClient stockClient) {
        return (args) -> {
            logger.info(stockClient.getStockInfo("TSLA").toString());
            SpringApplication.exit(applicationContext);
        };

    }

}
