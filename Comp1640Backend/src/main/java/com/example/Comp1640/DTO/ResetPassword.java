package com.example.Comp1640.DTO;


public class ResetPassword {
    private String email;

    private String newPassword;

    private String username;

    public ResetPassword(String email, String newPassword, String username) {
        this.email = email;
        this.newPassword = newPassword;
        this.username = username;
    }

    public ResetPassword() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
