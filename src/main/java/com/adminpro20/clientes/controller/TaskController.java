package com.adminpro20.clientes.controller;

import com.adminpro20.clientes.dto.Mensaje;
import com.adminpro20.clientes.repository.reports.SalesReportsRepository;
import com.adminpro20.clientes.model.NewTask;
import com.adminpro20.clientes.model.Task;
import com.adminpro20.clientes.service.task.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
public class TaskController {

    public final TaskService taskService;
    public final SalesReportsRepository salesReportsRepository;

    public TaskController(TaskService taskService, SalesReportsRepository salesReportsRepository) {
        this.taskService = taskService;
        this.salesReportsRepository = salesReportsRepository;
    }

  //  @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/task/new")
    public ResponseEntity<?> NewTask(@Valid @RequestBody NewTask newTask, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        Task task =
                new Task(newTask.getId(), newTask.getTask(), newTask.getAsignedTo(), newTask.getStatus(), newTask.getPriority(), newTask.getRequestby(), newTask.getDate(),
                        newTask.getCompletedDate(), newTask.isCompleted());
        taskService.save(task);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/task/delete")
    public ResponseEntity<?> DeleteTask(@Valid @RequestBody NewTask newTask, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        Task task =
                new Task(newTask.getId(), newTask.getTask(), newTask.getAsignedTo(), newTask.getStatus(), newTask.getPriority(),newTask.getRequestby(), newTask.getDate(),
                        newTask.getCompletedDate(), newTask.isCompleted());
       try {
           taskService.delete(task);
           return new ResponseEntity(new Mensaje("tarea borrada de la base de datos"), HttpStatus.CREATED);
       }catch (Error e) {
           return new ResponseEntity<>(new Mensaje("error al borrar en la base de datos"), HttpStatus.CONFLICT);
       }

    }

    // PETICION DE TODAS LAS TAREAS
    @RequestMapping(value = "/get-tasks", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllSuppliers() {

        Pageable paging = PageRequest.of(1, 10, Sort.by("fecha").ascending());;
        try {
            List<Task> tasks = taskService.findAll();

            return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
        } catch (Error e) {
            List<Task> tasks = null;
            return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
        }
    }



}
