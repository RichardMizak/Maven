package sk.kosickaakademia.maven.database;

import sk.kosickaakademia.maven.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String url = "";
    private String username = "";
    private String password = "";

    Log log=new Log();
    public Connection getConn(){
        try {
            Connection conn= DriverManager.getConnection(url,username,password);
            log.print("Connection success.");
            return conn;
        } catch (SQLException throwables) {
            log.error(throwables.toString());

        }
    }
    public void closeConn(Connection conn){
        if(conn==null){

        }
    }
}
