package sk.kosickaakademia.maven.database;

import sk.kosickaakademia.maven.Users.User;
import sk.kosickaakademia.maven.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String username = "mysqluser";
    private String password = "Kosice2021!";
//-----------------------------------------------------------------------
    Log log=new Log();
    private final String query="INSERT INTO user (fname, lname, age, gender) " +
                                " VALUES ( ?, ?, ?, ?)";
    private final String female="SELECT * FROM user WHERE gender = 1";
    private final String male="SELECT * FROM user WHERE gender = 0";
    private final String other="SELECT * FROM user WHERE gender = 2";
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
    private List<User> executeSelect(PreparedStatement ps) {
        List<User> userList=new ArrayList<>();
        int result = 0;
        try {
            ResultSet rs = ps.executeQuery();
            if(rs!=null) {
                while(rs.next()) {
                    result++;
                    int id=rs.getInt("id");
                    String fName=rs.getString("fName");
                    String lName=rs.getString("lName");
                    int age=rs.getInt("age");
                    int gender=rs.getInt("gender");
                    userList.add(new User(id,fName,lName,age,gender));
                    System.out.println("Number of results: "+result);
                    System.out.println(id+" "+fName+" "+lName+" "+age+" "+gender);
                }
            } else {
                System.out.println("No users found.");
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if(userList.size() != 0) {
            return userList;
        } else {
            System.out.println("No users found.");
            return null;
        }
    }
    //---------------------------------------------------------------------------
    public List<User> getFemales() {

        try {
            PreparedStatement ps = getConn().prepareStatement(female);
            return executeSelect(ps);
        } catch (Exception ex) {
        }
        return null;
    }
    //---------------------------------------------------------------------------
    public List<User> getMales() {
        try {
            PreparedStatement ps = getConn().prepareStatement(male);
            return executeSelect(ps);
        }catch(Exception ex) {
        }
        return null;
    }
    //---------------------------------------------------------------------------
    public List<User> getOther() {
        try {
            PreparedStatement ps = getConn().prepareStatement(other);
            return executeSelect(ps);
        }catch(Exception ex) {
        }
        return null;
    }
//---------------------------------------------------------------------------
    public boolean changeAge(int id,int newAge){
        return false;
    }
//---------------------------------------------------------------------------
    public User getUserById(int id){
        return null;
    }
//---------------------------------------------------------------------------
    public User getUser(String pattern){
        return null;
    }
//---------------------------------------------------------------------------
    public List<User> getAllUsers(){
        return null;
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