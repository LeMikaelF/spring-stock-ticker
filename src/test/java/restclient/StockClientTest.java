package restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikaelfrancoeur.demostockticker.DemoStockTickerApplication;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(classes = {DemoStockTickerApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(value = "classpath:keys.properties")
class StockClientTest {

    @Autowired
    private StockClient stockClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;


    private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void beforeEach() {
        mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void shortCircuitsToEmptyOptionalOnEmptyString() {
        mockRestServiceServer.expect(ExpectedCount.never(), requestTo(MatchesPattern.matchesPattern(".*")));
        stockClient.getStockInfo("");
        Assertions.assertDoesNotThrow(mockRestServiceServer::verify);
    }

    @Test
    void returnsEmptyOptionalOnServerFailure() {
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(MatchesPattern.matchesPattern(".*"))).andRespond(withServerError());
        Optional<StockInfo> stockInfo = stockClient.getStockInfo("SYMBOL");
        Assertions.assertEquals(Optional.empty(), stockInfo);
        Assertions.assertDoesNotThrow(() -> mockRestServiceServer.verify());
    }

    @Test
    void queriesCorrectBaseUrlAndParameters() {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(MatchesPattern.matchesPattern(".+\\/api\\/v1\\/quote\\?symbol=.*&token=.*"))).andRespond(withSuccess());
        stockClient.getStockInfo("SYMBOL");
        Assertions.assertDoesNotThrow(mockRestServiceServer::verify);
    }

    @Test
    void insertsCorrectSymbolIntoRequestUrl() {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(MatchesPattern.matchesPattern(".*symbol=SYMBOL.*"))).andRespond(withSuccess());
        stockClient.getStockInfo("SYMBOL");
        Assertions.assertDoesNotThrow(mockRestServiceServer::verify);
    }

    @Test
    void insertsCorrectKeyPropertyIntoRequestUrl() {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        String pattern = ".*token=" + environment.getProperty("stockapi.key");
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(MatchesPattern.matchesPattern(pattern))).andRespond(withSuccess());
        stockClient.getStockInfo("SYMBOL");
        Assertions.assertDoesNotThrow(mockRestServiceServer::verify);
    }

}
