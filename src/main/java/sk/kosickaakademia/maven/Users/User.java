package sk.kosickaakademia.maven.Users;

import sk.kosickaakademia.maven.Gender;

public class User {
    private int id;
    private String fname;
    private String lname;
    private Gender gender;
    private int age;

    public User(int id, String fname, String lname, int gender, int age) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender==0 ? Gender.MALE  : gender==1 ? Gender.FEMALE : Gender.OTHER;
        this.age = age;
    }

    public User(String fname, String lname, int gender, int age) {
        this.fname = fname;
        this.lname = lname;
        this.gender = gender==0 ? Gender.MALE  : gender==1 ? Gender.FEMALE : Gender.OTHER;
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

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
