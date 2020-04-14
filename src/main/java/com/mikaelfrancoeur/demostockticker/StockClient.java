package com.mikaelfrancoeur.demostockticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StockClient {
    @Autowired
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(StockClient.class);
    @Value("${stockapi.key}")
    private String key;
    @Value("${stockapi.getstockurl}")
    private String getStockUrl;


    @Autowired
    public StockClient() {
        logger.info(this.key);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    StockResponse getStockInfo(String symbol) {
        logger.info("URL from properties and injection: " + getStockUrl);
        return restTemplate.getForEntity(getStockUrl, StockResponse.class, symbol, key).getBody();
    }

}
