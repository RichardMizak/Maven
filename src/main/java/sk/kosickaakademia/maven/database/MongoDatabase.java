package sk.kosickaakademia.maven.database;


import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.MongoClient;
import sk.kosickaakademia.maven.Users.User;

public class DatabaseMONGO {
    private static final MongoClient mongoClient = new MongoClient();
    private static MongoDatabase database;
    private static Document docs;


    public void insertUser(User user){
            database=mongoClient.getDatabase("myFirstDB");
            docs.append("fname", user.getFname());
            docs.append("lname", user.getLname());
            docs.append("age", user.getAge());
            docs.append("gender", user.getGender().getValue());
            database.getCollection("users").insertOne(docs);


    }
    }

