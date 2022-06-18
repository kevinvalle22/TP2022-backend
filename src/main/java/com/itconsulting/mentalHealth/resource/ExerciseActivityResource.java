package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ExerciseActivityResource {

    private Long id;


    private String duration;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startDate;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endDate;


    private String dayOfTheWeek;

    private String message;

}
