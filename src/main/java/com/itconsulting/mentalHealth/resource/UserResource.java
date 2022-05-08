package com.itconsulting.mentalHealth.resource;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserResource {


    private String userName;


    private String email;


    private  String phone;


    private String password;


    private String university;


    private String province;


    private String district;
}
