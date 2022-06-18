package com.itconsulting.mentalHealth.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "exercise_activity")
@Data
public class ExerciseActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duration", nullable = false)
    @JsonIgnore
    private String duration;

    @Column(name = "startDate", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endDate;
    @Column(name = "dayOfTheWeek")
    @JsonIgnore
    private String dayOfTheWeek;
    @Column(name = "message", nullable = false)
    private String message;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser user;
}
