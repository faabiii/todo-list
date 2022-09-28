import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ItemController {
    @FXML
    private TextField TextField;
    @FXML
    private CheckBox box;
    private mainController mainController = new mainController();
    private Item item = new Item();
    @FXML
    private Label datum;
    private boolean newItem = false;
    @FXML
    private Button loeschen;

    public ItemController() {
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        try {
            this.item = item;
            this.box.setSelected(item.isAktiv());
            this.TextField.setText(item.getContent());
            this.datum.setText(item.getDatum());
        } catch (Exception var3) {
            this.newItem = true;
            this.TextField.setVisible(true);
        }

    }

    @FXML
    private void hover() {
        try {
            if (this.item.isAktiv()) {
                this.loeschen.getStyleClass().remove(this.loeschen.getStyleClass().size() - 1);
                this.loeschen.getStyleClass().add("buttonXHover");
            }
        } catch (Exception var2) {
        }

    }

    @FXML
    private void hoverExit() {
        try {
            if (this.item.isAktiv()) {
                this.loeschen.getStyleClass().remove(this.loeschen.getStyleClass().size() - 1);
                this.loeschen.getStyleClass().add("buttonX");
            }
        } catch (Exception var2) {
        }

    }

    @FXML
    private void pressEnter(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                if (this.newItem) {
                    this.mainController.newItem(this.TextField.getText());
                }

                this.TextField.setEditable(false);
                this.item.setContent(this.TextField.getText());
                this.mainController.editItem(this.item);
                this.TextField.getStyleClass().remove(this.TextField.getStyleClass().size() - 1);
                this.TextField.getStyleClass().add("textfield");
            } catch (Exception var3) {
            }
        }

    }

    @FXML
    private void clickCheck() throws IOException {
        if (this.item != null) {
            this.mainController.akt(this.item);
        } else {
            this.box.setSelected(false);
        }

    }

    @FXML
    private void doublepress(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            this.TextField.setEditable(true);
            this.TextField.getStyleClass().remove(this.TextField.getStyleClass().size() - 1);
            this.TextField.getStyleClass().add("textfieldfocused");
        }

    }

    public mainController getMainController() {
        return this.mainController;
    }

    public void setMainController(mainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void buttonClick() throws IOException {
        this.mainController.delete(this.item);
    }
}

