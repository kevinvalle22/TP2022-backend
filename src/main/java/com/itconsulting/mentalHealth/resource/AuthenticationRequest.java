package com.itconsulting.mentalHealth.resource;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String userName;
    private String password;


    public AuthenticationRequest(String username, String password) {
        super();
        this.userName = username;
        this.password = password;
    }

    public AuthenticationRequest()
    {

    }

}
