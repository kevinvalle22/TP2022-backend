package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SleepRecordResource {


    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endDate;

    private String duration;


}
