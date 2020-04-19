package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("singlestock.fxml")
@Scope("prototype")
public class SingleStockController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane root;

    @FXML
    private TextField textFieldSymbol;

    @FXML
    private Label labelPrice;

    @FXML
    private Button buttonRemove;

    private Pane parent;
    private Runnable onRemoveAction = () -> {};

    @FXML
    void removeStock(MouseEvent event) {
        parent.getChildren().remove(root);
        onRemoveAction.run();
    }

    @FXML
    void initialize() {
        assert textFieldSymbol != null : "fx:id=\"textFieldSymbol\" was not injected: check your FXML file 'singlestock.fxml'.";
        assert labelPrice != null : "fx:id=\"labelPrice\" was not injected: check your FXML file 'singlestock.fxml'.";
        assert buttonRemove != null : "fx:id=\"iconRemove\" was not injected: check your FXML file 'singlestock.fxml'.";
        System.out.println("In SingleStockController " + this.hashCode());
    }

    String getSymbol() {
        return textFieldSymbol.getText();
    }

    void setCurrentPrice(@Nullable Double price) {
        labelPrice.setText(Optional.ofNullable(price).map(d -> String.format("%.2f", d)).orElse("N/A"));
    }

    void addToParent(Pane parent) {
        parent.getChildren().add(root);
        this.parent = parent;
    }

    void setRemoveRunnable(Runnable runnable) {
        onRemoveAction = runnable;
    }
}

