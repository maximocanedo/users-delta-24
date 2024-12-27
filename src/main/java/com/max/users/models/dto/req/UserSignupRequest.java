package com.max.users.models.dto.req;

public class UserSignupRequest {

    private String username;
    private String name;
    private String surname;
    private String password;

    public UserSignupRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
