package sk.kosickaakademia.maven;

import sk.kosickaakademia.maven.database.Database;

public class App{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Database db=new Database();
        db.getConn();

    }
}
