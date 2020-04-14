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
@PropertySource(value="keys.properties")
public class DemoStockTickerApplication {

    Logger logger = LoggerFactory.getLogger(DemoStockTickerApplication.class);

    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(DemoStockTickerApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    CommandLineRunner commandLineRunner(RestTemplate restTemplate, ApplicationContext applicationContext) {
        return (args) -> {
            //String url = String.format("https://finnhub.io/api/v1/quote?symbol=TSLA&token=%s", env.getProperty("FinnHubKey=bqag7d7rh5r8t7qn9gb0"));
            //TODO change secret for property
            String url = "https://finnhub.io/api/v1/quote?symbol=TSLA&token=bqag7d7rh5r8t7qn9gb0";
            StockResponse stockResponse = restTemplate.getForEntity(url, StockResponse.class).getBody();
            if (stockResponse == null) {
                return;
            }
            logger.info(stockResponse.toString());
            SpringApplication.exit(applicationContext);
        };

    }

}
