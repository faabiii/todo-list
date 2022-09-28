public class Item {
    private int id;
    private boolean aktiv;
    private String content;
    private String datum;

    public Item() {
    }

    public Item(int id, boolean aktiv, String content, String datum) {
        this.id = id;
        this.aktiv = aktiv;
        this.content = content;
        this.datum = datum;
    }

    public String getDatum() {
        return this.datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAktiv() {
        return this.aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
