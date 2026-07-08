package com.healthcare.admin.response;

public class AdminJWTResponse {
    private String token;
    private long id;
    private String username;
    private String email;

    public AdminJWTResponse(
        String token,
        long id,
        String username,
        String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }   
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
