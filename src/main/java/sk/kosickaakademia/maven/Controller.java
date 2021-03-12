package sk.kosickaakademia.maven;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaakademia.maven.Users.User;
import sk.kosickaakademia.maven.log.Log;

@RestController
public class Controller {
    Log log=new Log();
    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody String input) {
        try {
            JSONObject o = (JSONObject) new JSONParser().parse(input);
            String fname = (String.valueOf(o.get("Firstname")));
            String lname = (String.valueOf(o.get("Lastname")));
            String gender = (String.valueOf(o.get("Gender")));
            int age = Integer.parseInt((String) o.get(fname));
            if (fname == null || lname == null || lname.trim().length() == 0 || fname.trim().length() == 0 || age <=0) {
                log.error("Missing Firstname or Lastname or incorrect age.");
                JSONObject object = new JSONObject();
                object.put("ERROR", "Missing Firstname or Lastname");
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(o.toJSONString());
            }
            Gender g;
            if(gender==null){
                g=Gender.OTHER;
            }else{
                if (gender.equalsIgnoreCase("male")){
                    g=Gender.MALE;
                }else{
                    if (gender.equalsIgnoreCase("female")) {
                        g=Gender.FEMALE;
                    }else{
                        g=Gender.OTHER;
                    }
                }
            }
            User user=new User(fname,lname,age,g.getValue());
        } catch (ParseException e){
        log.error("ERROR");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(null);

        }
        return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(null);

}
}



