package edu.nuaa;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author brain
 * @version 1.0
 * @date 2024/1/28 21:32
 */
public class ThreadLocalDemo {
    private static ThreadLocal<Connection> connectionHolder =
            new ThreadLocal<Connection>() {
                @SneakyThrows
                public Connection initialValue() {
                    return DriverManager.getConnection(
                            "jdbc:mysql:127.0.0.1:3306/test?user=root＆password=root");
                }
            };



    public static Connection getConnection() throws SQLException {
        return connectionHolder.get();
    }

    public static void main(String[] args) {

    }

}
