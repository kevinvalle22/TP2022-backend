package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class GoalResource {

    private String type;

    private String message;


    private String actionPlan1;

    private String actionPlan2;

    private String actionPlan3;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startDate;

    private String status;
}
