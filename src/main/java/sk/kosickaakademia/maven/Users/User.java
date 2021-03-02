package sk.kosickaakademia.maven.Users;

public class User {
    private int id;
    private String fname;
    private String lname;
    private boolean gendre;
    private int age;

    public User(int id, String fname, String lname, boolean gendre, int age) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gendre = gendre;
        this.age = age;
    }

    public User(String fname, String lname, boolean gendre, int age) {
        this.fname = fname;
        this.lname = lname;
        this.gendre = gendre;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public boolean isGendre() {
        return gendre;
    }

    public int getAge() {
        return age;
    }
}
