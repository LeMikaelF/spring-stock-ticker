package ui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import restclient.StockClient;

import java.util.ArrayList;

@Component
@FxmlView("ui.fxml")
public class UiController {

    private final StockClient stockClient;
    private final Logger logger = LoggerFactory.getLogger(UiController.class);
    private final ObservableList<SingleStockController> singleStockControllers = FXCollections.observableList(new ArrayList<>());
    @FXML
    Pane vBoxOuter;
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @FXML
    private Pane stockAreaPane;

    public UiController(StockClient stockClient) {
        this.stockClient = stockClient;
    }

    @FXML
    void initialize() {
        singleStockControllers.addListener((ListChangeListener<? super SingleStockController>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(controller -> {
                        controller.addToParent(stockAreaPane);
                        controller.setRemoveRunnable(() -> singleStockControllers.remove(controller));
                    });
                }
            }
        });
    }

    @FXML
    public void ajouterTitre(ActionEvent event) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        FxControllerAndView<SingleStockController, Node> fxControllerAndView =
                fxWeaver.load(SingleStockController.class);
        fxControllerAndView.getView().ifPresent(node -> {
            SingleStockController controller = fxControllerAndView.getController();
            singleStockControllers.add(controller);
        });
    }

    public void updateStocks() {
        logger.info(String.valueOf(singleStockControllers.size()));
        singleStockControllers.forEach(singleStockController -> {
            stockClient.getStockInfo(singleStockController.getSymbol())
                    .ifPresent(stockInfo -> singleStockController
                            .setCurrentPrice(Double.valueOf(String.valueOf(stockInfo.getCurrent()))));
        });
    }
}
