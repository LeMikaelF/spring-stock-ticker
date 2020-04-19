package com.mikaelfrancoeur.demostockticker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import ui.JavaFxApplication;
import ui.UiController;

@SpringBootTest(classes = {DemoStockTickerApplication.class, JavaFxApplication.class, UiController.class})
class DemoStockInfoTickerApplicationTests {

    @Test
    void contextLoads() {
    }

}
