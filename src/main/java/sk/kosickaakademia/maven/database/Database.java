package sk.kosickaakademia.maven.database;

import sk.kosickaakademia.maven.Users.User;
import sk.kosickaakademia.maven.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String username = "mysqluser";
    private String password = "Kosice2021!";
//-----------------------------------------------------------------------
    Log log=new Log();
    private final String query="INSERT INTO user (fname, lname, age, gender) " +
            " VALUES ( ?, ?, ?, ?)";
//-----------------------------------------------------------------------
    public Connection getConn(){
        try {
            Properties props=new Properties();
            InputStream loader=getClass().getClassLoader().getResourceAsStream("database.properties");
            props.load(loader);
            String url=props.getProperty("url");
            String username=props.getProperty("username");
            String password=props.getProperty("password");
            Connection conn=DriverManager.getConnection(url,username,password);
            log.print("Connection success.");
            return conn;
        } catch (SQLException | IOException throwables) {
            log.error(throwables.toString());

        }
        return null;
    }
//-----------------------------------------------------------------------------
public boolean insertNewUser(User user){
    Connection conn=getConn();
    if(conn!=null){
        try{
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1,user.getFname());
            ps.setString(2,user.getLname());
            ps.setInt(3,user.getAge());
            ps.setInt(4,user.getGender().getValue());
            int result=ps.executeUpdate();
            closeConn(conn);
            log.print("New User was added to Database.");
            return result==1;
        }catch(SQLException ex){
            log.error(ex.toString());
        }
    }
    return false;
}
//---------------------------------------------------------------------------
    public void closeConn(Connection conn){
        if(conn!=null){
            try {
                conn.close();
                log.print("Connection closed.");
            }catch(SQLException e){
                log.error(e.toString());
        }
    }
}
}