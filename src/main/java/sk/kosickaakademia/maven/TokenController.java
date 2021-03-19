package sk.kosickaakademia.maven;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.maven.log.Log;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {
    private final String PWD="Kosice2021";
    Map<String,String> map=new HashMap<>();
    Log log=new Log();
    @GetMapping("/secret")
    public String secret(@RequestHeader("token") String h){
        String token=h.substring(7);
        for (Map.Entry<String,String> entry:map.entrySet()){
            if (entry.getValue().equalsIgnoreCase(token)){
                return "secret";
            }
        }
        return "401";
    }
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
}
