package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class MoodTrackerResource {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date moodTrackerDate;


    private  String mood;


    private  String message;
}
