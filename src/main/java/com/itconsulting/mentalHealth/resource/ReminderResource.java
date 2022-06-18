package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ReminderResource {

    private  String message;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date reminderDate;

    private  Boolean mondayActive;

    private  Boolean tuesdayActive;

    private  Boolean wednesdayActive;

    private  Boolean thursdayActive;

    private  Boolean fridayActive;

    private  Boolean saturdayActive;

    private  Boolean sundayActive;
}
