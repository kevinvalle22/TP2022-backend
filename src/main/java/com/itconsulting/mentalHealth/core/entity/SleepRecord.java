package com.itconsulting.mentalHealth.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sleep_record")
@Data
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "startDate", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startDate;
    @Column(name = "endDate", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endDate;

    @Column(name = "duration", nullable = false)
    @JsonIgnore
    private String duration;
    @Column(name = "dayOfTheWeek")
    @JsonIgnore
    private String dayOfTheWeek;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser user;
}
