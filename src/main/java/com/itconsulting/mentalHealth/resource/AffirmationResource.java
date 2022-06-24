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
public class AffirmationResource {

    private Long id;

    private String message;

    private  Boolean mondayActive;

    private  Boolean tuesdayActive;

    private  Boolean wednesdayActive;

    private  Boolean thursdayActive;

    private  Boolean fridayActive;

    private  Boolean saturdayActive;

    private  Boolean sundayActive;

    private String dayOfTheWeek;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date affirmationDate;
}
