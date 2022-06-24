package com.itconsulting.mentalHealth.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "afirmations")
@Data
public class Affirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "mondayActive", nullable = false,length = 256)
    private  Boolean mondayActive;
    @Column(name = "tuesdayActive", nullable = false,length = 256)
    private  Boolean tuesdayActive;
    @Column(name = "wednesdayActive", nullable = false,length = 256)
    private  Boolean wednesdayActive;
    @Column(name = "thursdayActive", nullable = false,length = 256)
    private  Boolean thursdayActive;
    @Column(name = "fridayActive", nullable = false,length = 256)
    private  Boolean fridayActive;
    @Column(name = "saturdayActive", nullable = false,length = 256)
    private  Boolean saturdayActive;
    @Column(name = "sundayActive", nullable = false,length = 256)
    private  Boolean sundayActive;
    @Column(name = "dayOfTheWeek")
    @JsonIgnore
    private String dayOfTheWeek;
    @Column(name = "affirmationDate", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date affirmationDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser user;
}
