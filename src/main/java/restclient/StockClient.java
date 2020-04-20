package restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Optional;

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

    public Optional<@Valid StockInfo> getStockInfo(String symbol) {
        if (symbol.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(restTemplate.getForEntity(getStockUrl, StockResponse.class, symbol, key).getBody())
                    .map(stockResponse -> new StockInfo(symbol, stockResponse.getC()));
        } catch (RestClientException | IllegalArgumentException e) {
            logger.error("Exception in StockClient::getStock — {}", e.getMessage());
            return Optional.empty();
        }

    }

}
