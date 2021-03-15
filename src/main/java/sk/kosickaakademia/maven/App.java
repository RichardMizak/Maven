package sk.kosickaakademia.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.maven.database.Database;
@SpringBootApplication
public class App{
    public static void main( String[] args )
    {
        Database database=new Database();
        SpringApplication.run((App.class));
    }
}
