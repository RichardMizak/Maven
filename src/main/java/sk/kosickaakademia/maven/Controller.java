package sk.kosickaakademia.maven;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaakademia.maven.log.Log;

@RestController
public class Controller {
    Log log=new Log();
    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody String input) {
        try {
            JSONObject o=(JSONObject) new JSONParser().parse(input);
            String fname=(String.valueOf(o.get("Firstname"))).trim();
            String lname=(String.valueOf(o.get("Lastname"))).trim();
            String gender=(String.valueOf(o.get("Gender"))).trim();
            int age=Integer.parseInt((String)o.get(fname));
            if(fname==null || lname==null || lname.length()==0 || fname.length()==0 || age<=0){
                log.error("Missing Firstname or Lastname");
                JSONObject object=new JSONObject();
                object.put("ERROR","Missing Firstname or Lastname");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(o.toJSONString());
            }
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("{}");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body("{}");
    }
}
