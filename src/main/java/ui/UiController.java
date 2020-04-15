package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import restclient.StockClient;
import restclient.StockInfo;

@Component
@FxmlView("ui.fxml")
public class UiController {

    private final StockClient stockClient;
    private final Logger logger = LoggerFactory.getLogger(UiController.class);

    @FXML
    private Label priceLabel;

    public UiController(StockClient stockClient) {
        this.stockClient = stockClient;
    }

    public void getStock(ActionEvent actionEvent) {
        StockInfo response = stockClient.getStockInfo("TSLA");
        setPriceOnLabel(priceLabel, response.getCurrent());
    }

    private void setPriceOnLabel(Label label, double price) {
        label.setText(String.format("%.2f", price) + " USD");
    }

}
