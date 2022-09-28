import db.Verbindung;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AnmeldungController {
    @FXML
    private TextField benutzername;
    @FXML
    private PasswordField passwort;
    @FXML
    private Button registrieren;
    @FXML
    private Button anmelden;
    @FXML
    private Label error;
    private main main;
    private Stage primaryStage = new Stage();
    private FXMLLoader loader;

    public AnmeldungController() {
    }

    public void setMain(main main) {
        this.main = main;
    }

    @FXML
    private void registrierenClick() throws IOException {
        this.load("Registrieren", "registrieren.fxml");
        RegistrierungController controller = (RegistrierungController)this.loader.getController();
        controller.setAnmelden(this);
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    private void load(String title, String fxml) throws IOException {
        this.primaryStage = new Stage();
        this.primaryStage.setTitle(title);
        this.loader = new FXMLLoader(this.getClass().getResource(fxml));
        Parent root = (Parent)this.loader.load();
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }

    @FXML
    private void pressedEnter(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.ENTER) {
            if (this.passwort.isFocused()) {
                this.anmeldenClick();
            } else {
                this.passwort.requestFocus();
            }
        }

    }

    @FXML
    private void anmeldenClick() throws IOException {
        Verbindung verb = new Verbindung();
        verb.executeQuery("SELECT b.Name, p.PASSWORT FROM BENUTZER b JOIN PASSWORT p ON b.PID=p.PID WHERE b.NAME='" + this.benutzername.getText() + "' AND p.PASSWORT='" + this.passwort.getText() + "';");
        if (verb.getErgebnis().size() > 0) {
            this.load("ToDo-Liste", "main.fxml");
            mainController mainC = (mainController)this.loader.getController();
            mainC.setBenutzername(this.benutzername.getText());
            mainC.init();
            this.main.getPrimaryStage().close();
        } else {
            this.error.setText("Bitte überptüfe nochmals deine Daten!");
        }

        verb.trennen();
    }
}
