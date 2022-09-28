package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Verbindung {
    private boolean error = false;
    private Connection connection = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private ArrayList<ArrayList<String>> ergebnis;
    private ArrayList<String> collumnLabel;
    private ResultSetMetaData rsmd;
    private final ArrayList<Exception> exceptionArray = new ArrayList();

    public Verbindung() {
        this.verbinden();
    }

    private void createResultSet(String query) {
        try {
            this.rs = this.stmt.executeQuery(query);
        } catch (SQLException var5) {
            try {
                this.stmt.executeUpdate(query);
            } catch (Exception var4) {
                this.exceptionArray.add(var5);
                this.exceptionArray.add(var4);
                this.error = true;
            }
        }

    }

    private void createStatement() {
        try {
            this.stmt = this.connection.createStatement();
        } catch (SQLException var2) {
            this.exceptionArray.add(var2);
            this.error = true;
        }

    }

    public boolean executeQuery(String query) {
        this.exceptionArray.clear();
        this.error = false;
        this.createStatement();
        this.createResultSet(query);
        this.listeFuellen();
        this.rs_stmt_Schliessen();
        return this.error;
    }

    public boolean executeUpdate(String query) {
        System.out.println(query);
        this.exceptionArray.clear();
        this.error = false;
        this.createStatement();
        this.createResultSet(query);
        this.rs_stmt_Schliessen();
        return this.error;
    }

    private void fillCollLabel() {
        try {
            this.collumnLabel = new ArrayList();

            for(int i = 1; i <= this.rsmd.getColumnCount(); ++i) {
                this.collumnLabel.add(this.rsmd.getColumnLabel(i));
            }
        } catch (SQLException var2) {
            this.exceptionArray.add(var2);
            this.error = true;
        }

    }

    public ArrayList<String> getCollumnLabel() {
        return this.collumnLabel;
    }

    public ArrayList<ArrayList<String>> getErgebnis() {
        return this.ergebnis;
    }

    public ArrayList<Exception> getExceptionArray() {
        return this.exceptionArray;
    }

    public boolean isError() {
        return this.error;
    }

    private void listeFuellen() {
        try {
            if (!this.error) {
                this.rsmd = this.rs.getMetaData();
                this.fillCollLabel();
                this.ergebnis = new ArrayList();

                while(this.rs.next()) {
                    this.ergebnis.add(new ArrayList());

                    for(int i = 1; i <= this.rsmd.getColumnCount(); ++i) {
                        ((ArrayList)this.ergebnis.get(this.ergebnis.size() - 1)).add(this.rs.getString(i));
                    }
                }
            }
        } catch (SQLException var2) {
            this.exceptionArray.add(var2);
            this.error = true;
        }

    }

    private void rs_stmt_Schliessen() {
        try {
            if (!this.error) {
                try {
                    this.rs.close();
                } catch (Exception var2) {
                    this.exceptionArray.add(var2);
                }

                this.stmt.close();
            }
        } catch (SQLException var3) {
            this.exceptionArray.add(var3);
            this.error = true;
        }

    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void trennen() {
        this.exceptionArray.clear();
        this.error = false;

        try {
            if (!this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException var2) {
            this.exceptionArray.add(var2);
            this.error = true;
        }

    }

    public void verbinden() {
        this.exceptionArray.clear();
        this.error = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(Authentication.getConURL(), Authentication.getUserName(), Authentication.getPassword());
        } catch (Exception var5) {
            this.exceptionArray.add(var5);
            this.error = true;

            try {
                this.connection.close();
            } catch (SQLException var3) {
                this.exceptionArray.add(var3);
                this.error = true;
            } catch (NullPointerException var4) {
                this.exceptionArray.add(var4);
                this.error = true;
            }
        }

    }
}
