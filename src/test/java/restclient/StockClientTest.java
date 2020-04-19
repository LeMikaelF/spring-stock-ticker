package restclient;

import com.mikaelfrancoeur.demostockticker.DemoStockTickerApplication;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(classes = {DemoStockTickerApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(value = "classpath:test-keys.properties")
class StockClientTest {

    @Autowired
    private StockClient stockClient;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;

    @BeforeAll
    void beforeAll() {
        mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void shortCircuitsToEmptyOptionalOnEmptyString() {
        mockRestServiceServer.expect(ExpectedCount.never(), requestTo(MatchesPattern.matchesPattern(".*")));
        stockClient.getStockInfo("");
        Assertions.assertDoesNotThrow(mockRestServiceServer::verify);
    }

    @Test
    void shortCircuitsToEmptyOptionalIfNonAlphaCharsInSymbol() {
        mockRestServiceServer.expect(ExpectedCount.never(), requestTo(MatchesPattern.matchesPattern(".*")));
        stockClient.getStockInfo("12ABC");
        Assertions.assertDoesNotThrow(mockRestServiceServer::verify);
    }

    @Test
    void queriesCorrectBaseUrlAndParameters() {
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        mockRestServiceServer.expect(requestTo(MatchesPattern.matchesPattern(".+\\/api\\/v1\\/quote\\?symbol=.*&token=.*"))).andRespond(withSuccess());
        stockClient.getStockInfo("SYMBOL");
        Assertions.assertDoesNotThrow(mockRestServiceServer::verify);
    }
}
