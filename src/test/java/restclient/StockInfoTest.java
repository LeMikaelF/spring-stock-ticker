package restclient;

import com.mikaelfrancoeur.demostockticker.DemoStockTickerApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DemoStockTickerApplication.class})
class StockInfoTest {

    @Test
    void constructorThrowsOnNegativeCurrentPrice() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StockInfo("ABCD", -123D));
    }

    @Test
    void construcorThrowsOnEmptySymbol() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StockInfo("", 123D));
    }

    @Test
    void constructorThrowsOnNullPrice() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StockInfo("ABCD", null));
    }

    @Test
    void constructorThrowsOnNullSymbol() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StockInfo(null, 123D));
    }

    @Test
    void constructorThrowsOnNanPrince() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StockInfo("ABCD", Double.NaN));
    }

    @Test
    void constructorThrowsOnInfinityPrice() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StockInfo("ABCD", Double.POSITIVE_INFINITY));
    }

    @Test
    void fieldsInitializeCorrectly() {
        StockInfo stockInfo = new StockInfo("ABCD", 123D);
        Assertions.assertEquals("ABCD", stockInfo.getSymbol());
        Assertions.assertEquals(123D, stockInfo.getCurrent());
    }

}
