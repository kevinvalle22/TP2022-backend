package com.itconsulting.mentalHealth.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class ThoughtRecordResource {


    private String message;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonIgnore
    private Date createdAt;
}
