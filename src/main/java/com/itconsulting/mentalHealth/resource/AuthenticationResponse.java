package com.itconsulting.mentalHealth.resource;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private Long id;
    public AuthenticationResponse()
    {

    }

    public AuthenticationResponse(String token,Long id) {
        super();
        this.token = token;
        this.id=id;
    }


}
