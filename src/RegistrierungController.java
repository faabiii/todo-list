import db.Verbindung;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RegistrierungController {
    @FXML
    private TextField benutzername;
    @FXML
    private PasswordField passwort;
    @FXML
    private PasswordField passwortB;
    @FXML
    private Button registrieren;
    private AnmeldungController anmelden;
    @FXML
    private Label error;

    public RegistrierungController() {
    }

    public AnmeldungController getAnmelden() {
        return this.anmelden;
    }

    public void setAnmelden(AnmeldungController anmelden) {
        this.anmelden = anmelden;
    }

    @FXML
    private void registrierenClick() {
        if (this.passwort.getLength() > 6) {
            if (!this.passwort.getText().equals(this.passwortB.getText()) && this.passwort.getLength() > 5) {
                this.error.setText("Die Passwörter stimmen nicht überein!");
            } else {
                this.error.setText("");
                Verbindung verb = new Verbindung();
                verb.executeUpdate("INSERT INTO passwort(PID, PASSWORT) VALUES (NULL, '" + this.passwort.getText() + "');");
                verb.executeUpdate("INSERT INTO benutzer(BID, NAME, PID) VALUES (NULL,'" + this.benutzername.getText() + "', (SELECT MAX(PID) FROM passwort));");
                this.anmelden.getPrimaryStage().close();
            }
        } else {
            this.error.setText("Das Passwort braucht mindestens 6 Zeichen!");
        }

    }

    @FXML
    private void pressedEnter(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            if (this.benutzername.isFocused()) {
                this.passwort.requestFocus();
            } else if (this.passwort.isFocused()) {
                this.passwortB.requestFocus();
            } else if (this.passwortB.isFocused()) {
                this.registrierenClick();
            }
        }

    }
}
