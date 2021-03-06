package sk.kosickaakademia.maven.util;

import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import sk.kosickaakademia.maven.Gender;
import sk.kosickaakademia.maven.Users.User;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class Util {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


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
    //-----------------------------------------------------------------------------
    public String getOverview(List<User> list) {
    int count=list.size();
    int male=0;
    int female=0;
    int sumage=0;
    int other=0;
    int min=count>0?list.get(0).getAge():0;
    int max=count>0?list.get(0).getAge():0;
    for(User u:list){
        if (u.getGender()== Gender.FEMALE) female++;
        else if (u.getGender()== Gender.MALE) male++;
        else if (u.getGender()== Gender.OTHER) other++;
        sumage=sumage+u.getAge();
        if(min>u.getAge()){
            min=u.getAge();
        }
        if(max<u.getAge()){
            max=u.getAge();
        }
        double avarage=(double)sumage/count;
        JSONObject o=new JSONObject();
        o.put("count",count);
        o.put("male",male);
        o.put("female",female);
        o.put("other",other);
        o.put("sumage",sumage);
        o.put("avarage",avarage);
        return o.toString();
        }
    return null;
}
//---------------------------------------------------------------------------------------------
    public String generateToken(){
        byte[] token = new byte[28];
        secureRandom.nextBytes(token);
        System.out.println(base64Encoder.encodeToString(token));
        return base64Encoder.encodeToString(token);
        }
//-----------------------------------------------------------------------------------------------    
    public static String convertToXML(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String xml =XML.toString(jsonObject);
        return xml;
    }    
}
