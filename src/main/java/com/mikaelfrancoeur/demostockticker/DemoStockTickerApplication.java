package com.mikaelfrancoeur.demostockticker;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import restclient.StockClient;
import ui.JavaFxApplication;
import ui.UiController;

@SpringBootApplication(scanBasePackageClasses = {StockClient.class, UiController.class, JavaFxApplication.class})
@PropertySource(value = "classpath:keys.properties")
public class DemoStockTickerApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }

}
