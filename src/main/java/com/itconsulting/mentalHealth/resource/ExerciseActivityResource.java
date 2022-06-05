package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class ExerciseActivityResource {
    private Long id;
    private String duration;

    private String dayOfTheWeek;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date exerciseDate;


}
