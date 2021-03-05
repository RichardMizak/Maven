package sk.kosickaakademia.maven.util;

import sk.kosickaakademia.maven.Users.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Util {
    public String getJSON(List<User> list){

        return null;
    }
    //-----------------------------------------------------------------------------
    public String getJSON(User user){
        return null;
    }
    //-----------------------------------------------------------------------------
    public String getTimeAndDate(){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return date.format(now);
    }
    //-----------------------------------------------------------------------------
    public String nameNormalizer(String name){
        if(name==null || name==""){
            return null;
        }
        return name.substring(0, 1).toUpperCase()+name.substring(1);
    }
}
