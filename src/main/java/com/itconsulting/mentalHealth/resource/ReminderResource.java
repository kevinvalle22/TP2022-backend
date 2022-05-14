package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReminderResource {

    private  String message;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date reminderDate;
}
