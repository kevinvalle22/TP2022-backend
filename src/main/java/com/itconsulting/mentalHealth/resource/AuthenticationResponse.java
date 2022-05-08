package com.itconsulting.mentalHealth.resource;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse()
    {

    }

    public AuthenticationResponse(String token) {
        super();
        this.token = token;
    }


}
