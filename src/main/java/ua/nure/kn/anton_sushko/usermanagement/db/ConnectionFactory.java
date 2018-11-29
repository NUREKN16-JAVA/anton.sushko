package ua.nure.kn.anton_sushko.usermanagement.db;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection() throws DatabaseException;
}