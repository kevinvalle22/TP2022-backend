package com.itconsulting.mentalHealth.resource;

import lombok.Data;

@Data
public class TestResultResource {

    private Long id;
    private String type;

    private String score;
}
