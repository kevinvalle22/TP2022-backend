package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class ThoughtRecordResource {

    private Long id;
    private String message;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonIgnore
    private Date createdAt;
}
