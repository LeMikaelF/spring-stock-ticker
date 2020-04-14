package com.mikaelfrancoeur.demostockticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class StockClient {

    private final Environment env;
    private final RestOperations restOperations;
    private Logger logger = LoggerFactory.getLogger(StockClient.class);

    public StockClient(Environment env, RestOperations restOperations) {
        this.env = env;
        this.restOperations = restOperations;
    }


    StockResponse getStockInfo(String symbol) {
        // Documentation de l'API:
        // https://finnhub.io/docs/api#quote

        String url = String.format("https://finnhub.io/api/v1/quote?symbol=%s&token=%s", symbol,
                env.getProperty("FinnHubKey"));
        return restOperations.getForEntity(url, StockResponse.class).getBody();
    }

}
