package com.example.foodorderapp;

public class Login_Register_Credentials {
    private String client_username, client_password, agent_id, agent_password, response;
    private String firstname, lastname, email, password, username, registery_response;
    private static String client_id;

    public static String getClient_id() { return client_id; }

    public void setClient_username(String client_username) { this.client_username = client_username; }

    public void setClient_password(String client_password) { this.client_password = client_password; }

    public String getRegistery_response() { return registery_response; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public void setLastname(String lastname) { this.lastname = lastname;    }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setUsername(String username) { this.username = username; }

    public String getAgent_id() {
        return agent_id;
    }

    public String getResponse() { return response; }

    public String getAgent_password() {
        return agent_password;
    }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getUsername() { return username; }
}
