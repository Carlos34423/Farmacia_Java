package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/farmacia?useSSL=false";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    private static ConnectionFactory instance;

    private ConnectionFactory() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do banco de dados nao encontrado", e);
        }
    }

    public static ConnectionFactory getIntance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnetion() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection con = getConnetion();
        return con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }
}
