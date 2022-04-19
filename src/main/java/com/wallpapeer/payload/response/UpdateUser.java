package com.wallpapeer.payload.response;

public class UpdateUser {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UpdateUser(String username) {
        this.username = username;
    }
}
