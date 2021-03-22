package sk.kosickaakademia.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.maven.database.Database;
import sk.kosickaakademia.maven.util.Util;

@SpringBootApplication
public class App{
    public static void main( String[] args )
    {
        Database database=new Database();
        SpringApplication.run((App.class));
        Util util=new Util();
        util.generateToken();

    }
}
