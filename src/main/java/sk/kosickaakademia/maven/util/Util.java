package sk.kosickaakademia.maven.util;

import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import sk.kosickaakademia.maven.Users.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Util {
    public String getJSON(List<User> list){
        if(list.isEmpty())
            return "{}";
        JSONObject object=new JSONObject();
        object.put("datetime",getTimeAndDate());
        object.put("size",list.size());
        JSONArray jsonArray=new JSONArray();
        for(User u:list ){
            JSONObject userJson=new JSONObject();
            userJson.put("id", u.getId());
            userJson.put("fname", u.getFname());
            userJson.put("lname", u.getLname());
            userJson.put("age", u.getAge());
            userJson.put("gender", u.getGender().toString());
            jsonArray.add(userJson);
        }
        object.put("users",jsonArray);
        return object.toString();
    }


    //-----------------------------------------------------------------------------
    public String getJSON(User user){
        if(user==null)
            return "{}";
            JSONObject object = new JSONObject();
            object.put("datetime", getTimeAndDate());
            object.put("size", 1);
            JSONArray jsonArray = new JSONArray();
            JSONObject userJson = new JSONObject();
            userJson.put("id", user.getId());
            userJson.put("fname", user.getFname());
            userJson.put("lname", user.getLname());
            userJson.put("age", user.getAge());
            userJson.put("gender", user.getGender().toString());
            jsonArray.add(userJson);
        object.put("users",jsonArray);
        return object.toString();
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