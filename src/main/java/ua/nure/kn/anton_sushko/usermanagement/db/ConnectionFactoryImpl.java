package ua.nure.kn.anton_sushko.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl(final String driver, final String url, final String user, final String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection createConnection() throws DatabaseException {

        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException(e);
        }

        try {
            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}