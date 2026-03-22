package org.example.visitme.view.responses;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private String fullname;
    private String email;
    private SimpleLoginResponse login;

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public SimpleLoginResponse getLogin() {
        return login;
    }
    public void setLogin(SimpleLoginResponse login) {
        this.login = login;
    }
}