package db;

public class Authentication {
    private static String userName = "root";
    private static String password = "root";
    private static String conURL = "jdbc:mysql://localhost:3306/todoliste";

    public Authentication() {
    }

    protected static String getConURL() {
        return conURL;
    }

    protected static String getPassword() {
        return password;
    }

    protected static String getUserName() {
        return userName;
    }
}
