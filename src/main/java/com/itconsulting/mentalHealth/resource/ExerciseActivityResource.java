package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ExerciseActivityResource {

    private String duration;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date exerciseDate;
}
