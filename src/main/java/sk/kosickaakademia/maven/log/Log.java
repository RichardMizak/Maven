package sk.kosickaakademia.maven.log;

public class Log {

    public void error(String msg){
        System.out.println("Error: "+msg );
    }
    public void print(String msg){
        System.out.println("Success: "+msg);
    }
}