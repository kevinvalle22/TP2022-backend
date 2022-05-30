package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;

@Data
public class UserResource {


    private Long id;

    private String userName;


    private String email;


    private  String phone;


    private String password;


    private String university;


    private String province;


    private String district;
}
