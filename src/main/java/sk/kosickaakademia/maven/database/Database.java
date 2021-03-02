package sk.kosickaakademia.maven.database;

import sk.kosickaakademia.maven.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String username = "mysqluser";
    private String password = "Kosice2021!";

    Log log=new Log();
    public Connection getConn(){
        try {
            Connection conn= DriverManager.getConnection(url,username,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void closeConn(Connection conn){
        if(conn==null){

        }
    }
}
