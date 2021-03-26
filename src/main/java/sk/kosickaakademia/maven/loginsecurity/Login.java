package sk.kosickaakademia.maven.loginsecurity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private static Map<String, Date> blocked ;
    private static Map<String, Integer> attempt;
    private final String PWD="heslo123";
//-------------------------------------------------------------------------------------------------
    public Login() {
        blocked=new HashMap<>();
        attempt=new HashMap<>();
    }
//-------------------------------------------------------------------------------------------------
    public String loginUser(String login,String pwd){
        if(blockedUsers(login)){
            return "User is blocked";
        }
        if(!(pwd.equals(PWD))){
            if(attempt.get(login)==null){
                attempt.put(login,1);
            }else{
                attempt.put(login,attempt.get(login)+1);
            }
            if(attempt.get(login)==3){
                attempt.put(login,attempt.get(login)-3);
                Date date=new Date();
                blocked.put(login,date);
                return "User is blocked";
            }
            return "Wrong password";
        }
        deleteAttempt(login);
        return "Password correct";
    }
    private boolean blockedUsers(String login){
        if(blocked.get(login)==null){
            return false;
        }
        Long dateNow=new Date().getTime();
        if(blocked.get(login).getTime()+60000>dateNow){
            return true;
        }else{
            blocked.remove(login);
            return false;
        }
    }
    private void deleteAttempt(String login){
        for(Map.Entry<String, Integer> entry : attempt.entrySet()) {
            if(entry.getValue().equals(login)){
                attempt.remove(entry.getKey());
            }
        }
    }
}
