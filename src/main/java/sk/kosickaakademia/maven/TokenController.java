package sk.kosickaakademia.maven;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.maven.log.Log;
import sk.kosickaakademia.maven.util.Util;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {
    private final String PWD="heslo123";
    Map<String,String> map=new HashMap<>();
    Log log=new Log();
    //---------------------------------------------------------------------------------------
    @GetMapping("/secret")
    public String secret(@RequestHeader("token") String h){
        String token=h.substring(7);
        for (Map.Entry<String,String> entry:map.entrySet()){
            if (entry.getValue().equalsIgnoreCase(token)){
                return "token";
            }
        }
        return "401";
    }
    //--------------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String aut){
        JSONObject o = null;
        try {
            o = (JSONObject) new JSONParser().parse(aut);
            String login = (String.valueOf(o.get("Login")));
            String pwd = (String.valueOf(o.get("Password")));
            if (pwd.equals("null")||login.equals("null")){
                log.error("Missing login or password.");
                return ResponseEntity.status(400).body("");
            }else{
                if (pwd.equals(PWD)){
                    log.print("Logged.");
                    String token=new Util().generateToken();
                    map.put(login,token);
                    JSONObject o2=new JSONObject();
                    o2.put("login",login);
                    o2.put("token",token);
                    return ResponseEntity.status(200).body("");
                }else{
                    log.error("Wrong password");
                    return ResponseEntity.status(401).body("");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String data){
        JSONObject o = null;
        try {
            o = (JSONObject) new JSONParser().parse(data);
            String login=o.get("login").toString();
            if (!login.equals("null")){
                map.remove(login);
                System.out.println("Logout");
            }else{
                System.out.println("User don't exist");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("Success");
    }
        @GetMapping("/info")
    public ResponseEntity<String> getFavourites(@RequestBody String body) {
        JSONObject info = new JSONObject();
        info.put("Firstname", "Ricard");
        info.put("Lastname", "Mižák");
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(body);
            String token = String.valueOf(object.get("token"));
            if (!token.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet())
                    if (entry.getValue().equals(token)) {
                        info.put("Favourite car brand", "Mazda");
                        info.put("Favourite film", "Seven");
                        info.put("Favourite director", "Christopher Nolan");
                        info.put("Favourite game", "DOOM");
                        info.put("Favourite food", "Pancakes");
                        info.put("Favourite drink", "Beer");
                    }
            }
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(info.toJSONString());
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("ERROR");
        }
    }
}
