package com.adminpro20.clientes.model;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class NewTask {

    private Long id;
    @NotBlank
    private String task;
    @NotBlank
    private String asignedTo;
    @NotBlank
    private String status;
    @NotBlank
    private String priority;
    @NotBlank
    private String requestby;
    @NotBlank
    private Date date;
    @NotBlank
    private Date completedDate;
    @NotBlank
    private boolean completed;


}
