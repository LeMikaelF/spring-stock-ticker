package ui;

import com.mikaelfrancoeur.demostockticker.DemoStockTickerApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage primaryStage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Scene scene = new Scene(fxWeaver.loadView(UiController.class));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(DemoStockTickerApplication.class)
                .run(args);
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
