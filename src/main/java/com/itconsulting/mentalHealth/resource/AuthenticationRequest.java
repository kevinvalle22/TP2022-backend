package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @JsonIgnore
    private Long id;
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
