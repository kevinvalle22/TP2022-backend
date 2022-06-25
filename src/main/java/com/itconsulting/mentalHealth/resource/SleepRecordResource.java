package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class SleepRecordResource {
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endDate;

    private String duration;

    private String dayOfTheWeek;

    @Column(name = "message", nullable = false)
    private String message;
}
