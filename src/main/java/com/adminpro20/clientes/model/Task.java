package com.adminpro20.clientes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String task;
    @NotNull
    private String asignedTo;
    @NotNull
    private String status;
    @NotNull
    private String priority;
    @NotNull
    private String requestby;
    @NotNull
    @JsonFormat(pattern="MM/dd/yyyy")
    private Date date;
    @NotNull
    @JsonFormat(pattern="MM/dd/yyyy")
    private Date completedDate;
    @NotNull
    private boolean completed;

    public Task() {
    }

    public Task(Long id, @NotNull String task, @NotNull String asignedTo, @NotNull String status,
                @NotNull String priority, @NotNull String requestby, @NotNull Date date, @NotNull Date completedDate, @NotNull boolean completed) {
        this.id = id;
        this.task = task;
        this.asignedTo = asignedTo;
        this.status = status;
        this.priority = priority;
        this.requestby = requestby;
        this.date = date;
        this.completedDate = completedDate;
        this.completed = completed;
    }
}
