import db.Verbindung;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class mainController {
    @FXML
    private VBox oben;
    @FXML
    private VBox unten;
    private ArrayList<Item> items = new ArrayList();
    private Verbindung verb;
    private String benutzername = "";

    public mainController() {
    }

    private void readListe() {
        this.items.clear();
        this.verb = new Verbindung();
        System.out.println("SELECT * FROM LISTE WHERE BID=(SELECT BID FROM BENUTZER WHERE NAME='" + this.benutzername + "');");
        this.verb.executeQuery("SELECT * FROM LISTE WHERE BID=(SELECT BID FROM BENUTZER WHERE NAME='" + this.benutzername + "');");

        for(int i = 0; i < this.verb.getErgebnis().size(); ++i) {
            int aktivInt = Integer.parseInt((String)((ArrayList)this.verb.getErgebnis().get(i)).get(this.getColIndex("AKTIV")));
            boolean aktivBool = aktivInt == 1;
            this.items.add(new Item(Integer.valueOf((String)((ArrayList)this.verb.getErgebnis().get(i)).get(this.getColIndex("LID"))), aktivBool, (String)((ArrayList)this.verb.getErgebnis().get(i)).get(this.getColIndex("TEXT")), (String)((ArrayList)this.verb.getErgebnis().get(i)).get(this.getColIndex("datum"))));
        }

        this.verb.trennen();
    }

    public void init() {
        this.readListe();

        try {
            this.akt();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    private int getColIndex(String colName) {
        for(int i = 0; i < this.verb.getCollumnLabel().size(); ++i) {
            if (((String)this.verb.getCollumnLabel().get(i)).equals(colName)) {
                return i;
            }
        }

        return 0;
    }

    public void akt(Item item) throws IOException {
        for(int i = 0; i < this.items.size(); ++i) {
            if (((Item)this.items.get(i)).getId() == item.getId()) {
                ((Item)this.items.get(i)).setAktiv(!((Item)this.items.get(i)).isAktiv());
                break;
            }
        }

        this.akt();
    }

    public void akt() throws IOException {
        this.oben.getChildren().clear();
        this.unten.getChildren().clear();
        this.addItem((Item)null);

        for(int i = this.items.size() - 1; i >= 0; --i) {
            this.addItem((Item)this.items.get(i));
        }

    }

    private void addItem(Item item) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Item.fxml"));
        Parent root = (Parent)loader.load();
        ItemController itemController = (ItemController)loader.getController();

        try {
            if (item.isAktiv()) {
                this.unten.getChildren().add(root);
                this.verb = new Verbindung();
                this.verb.executeUpdate("UPDATE liste SET AKTIV=1 WHERE LID=" + item.getId());
            } else {
                this.oben.getChildren().add(root);
                this.verb = new Verbindung();
                this.verb.executeUpdate("UPDATE liste SET AKTIV=0 WHERE LID=" + item.getId());
            }
        } catch (Exception var6) {
            this.oben.getChildren().add(root);
        }

        itemController.setItem(item);
        itemController.setMainController(this);
    }

    public String getBenutzername() {
        return this.benutzername;
    }

    public void setBenutzername(String benutzername) throws IOException {
        this.benutzername = benutzername;
        this.readListe();
        this.akt();
    }

    public void newItem(String content) throws IOException {
        this.verb = new Verbindung();
        this.verb.executeUpdate("INSERT INTO liste(LID, TEXT, BID, AKTIV, datum) VALUES(NULL, '" + content + "', (SELECT BID FROM BENUTZER WHERE NAME='" + this.benutzername + "'), 0, now())");
        this.verb.trennen();
        this.readListe();
        this.akt();
    }

    public void editItem(Item item) {
        this.verb = new Verbindung();
        this.verb.executeUpdate("UPDATE liste SET TEXT='" + item.getContent() + "' WHERE LID=" + item.getId());
        this.verb.trennen();
    }

    public void delete(Item item) throws IOException {
        this.verb = new Verbindung();
        this.verb.executeUpdate("DELETE FROM `liste` WHERE LID=" + item.getId());
        this.verb.trennen();
        this.readListe();
        this.akt();
    }
}
