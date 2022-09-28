import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    private Stage primaryStage;

    public main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Anmelden");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("passwort-eingabe.fxml"));
        Parent root = (Parent)loader.load();
        AnmeldungController controller = (AnmeldungController)loader.getController();
        controller.setMain(this);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
