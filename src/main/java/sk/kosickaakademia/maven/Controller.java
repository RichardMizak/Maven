package sk.kosickaakademia.maven;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.maven.Users.User;
import sk.kosickaakademia.maven.database.Database;
import sk.kosickaakademia.maven.log.Log;
import sk.kosickaakademia.maven.util.Util;

import java.util.List;

@RestController
public class Controller {
    Log log=new Log();
    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody String input) {
        try {
            JSONObject o=(JSONObject) new JSONParser().parse(input);
            String fname=(String.valueOf(o.get("Firstname")));
            String lname=(String.valueOf(o.get("Lastname")));
            String gender=(String.valueOf(o.get("Gender")));
            int age= Integer.parseInt(String.valueOf(o.get("Age")));
            if (fname==null || lname==null || lname.trim().length()==0 || fname.trim().length()==0 || age<=0) {
                log.error("Missing Firstname or Lastname or Age or Gender.");
                return ResponseEntity.status(404).body("ERROR");
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
         User user=new User(fname,lname,g.getValue(),age);
            new Database().insertNewUser(user);
            new DatabaseMONGO().insertUser(user);
        } catch (ParseException e){
            return ResponseEntity.status(404).body("Wrong input.");
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(null);

}
//----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users")
    public ResponseEntity<String> users(){
        List<User> list=new Database().getAllUsers();
        String json=new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
//----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users/{gender}")
    public ResponseEntity<String> usersByGender(@PathVariable String gender) {
        if (gender == "male") {
            List<User> list = new Database().getMales();
            String json = new Util().getJSON(list);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
        } else if (gender == "female") {
            List<User> list = new Database().getFemales();
            String json = new Util().getJSON(list);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
        } else if (gender == "other"){
            List<User> list = new Database().getFemales();
            String json = new Util().getJSON(list);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
        }else
        return ResponseEntity.status(400).body("ERROR");
    }
//----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/users/age")
    public ResponseEntity<String> usersByAge(@RequestParam(value="from")int a,@RequestParam(value="to")int b){
    if (a>b){
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
    }
    List<User> list=new Database().getUsersByAge(a, b);
    String json=new Util().getJSON(list);
    return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
}
//----------------------------------------------------------------------------------------------------------------------
    @PutMapping("/user/{id}")
    public ResponseEntity<String> changeUserAge(@PathVariable Integer id,@RequestBody String body) {
        JSONObject o=new JSONObject();
        try {
             o= (JSONObject) new JSONParser().parse(body);
             return ResponseEntity.status(400).body("ERROR");
        }catch(ParseException e) {
            e.printStackTrace();
        }
        String data=String.valueOf(o.get("newAge"));
        if(data.equalsIgnoreCase("null")){
            return ResponseEntity.status(400).body("ERROR");
        }
        int newAge=Integer.parseInt(data);
        if(newAge<1){
            return ResponseEntity.status(400).body("ERROR");
        }
        boolean result = new Database().changeAge(id,newAge);
        if(result){
            return ResponseEntity.status(200).body("Success");
            }else{
                return ResponseEntity.status(404).body("ERROR");
                }
    }
//----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/")
    public ResponseEntity<String> getOverview(){
        List<User> list=new Database().getAllUsers();
        String json=new Util().getOverview(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json.toString());
    }
    //----------------------------------------------------------------------------------------------------------------------    
    @GetMapping(value = "/users", params = "type")
    public ResponseEntity<String> getUsersXML(@RequestParam(value = "type") String type){
        if(type == null || !type.equals("xml")) {
            return ResponseEntity.status(400).body("Wrong type");
        }else{
            Database database=new Database();
            List<User> list = database.getAllUsers();
            String json=new Util().getJSON(list);
            String xml=new Util().convertToXML(json);
            System.out.println(xml);
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_XML).body(getUsersXML("xml").toString());
    }
}
