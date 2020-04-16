package com.mikaelfrancoeur.demostockticker;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import restclient.StockClient;
import ui.JavaFxApplication;
import ui.UiController;

@SpringBootApplication(scanBasePackageClasses = {StockClient.class, UiController.class, JavaFxApplication.class})
@PropertySource(value = "keys.properties")
public class DemoStockTickerApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }

}
