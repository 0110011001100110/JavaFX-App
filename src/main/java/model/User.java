package model;

import java.time.LocalDate;

public class User {

    private int id; //primary key
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String gender;
    private LocalDate dateOfBirth;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String  getPassword() {
        return password;
    }

    public void setPassword(String  password) {
        this.password = password;
    }

    public String  getGender() {
        return gender;
    }

    public void setGender(String  gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
