package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@FxmlView("singlestock.fxml")
@Scope("prototype")
public class SingleStockController {

    @FXML
    private Pane root;

    @FXML
    private TextField textFieldSymbol;

    @FXML
    private Label labelPrice;

    private Pane parent;
    private Runnable onRemoveAction = () -> {};

    @FXML
    void removeStock() {
        parent.getChildren().remove(root);
        onRemoveAction.run();
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

