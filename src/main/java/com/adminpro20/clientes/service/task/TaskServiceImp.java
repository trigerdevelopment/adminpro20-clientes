package com.adminpro20.clientes.service.task;

import com.adminpro20.clientes.model.Invoice;
import com.adminpro20.clientes.model.Task;
import com.adminpro20.clientes.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements TaskService {

    public final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }



    @Override
    public List<Task> findAll() {
        return (List<Task>) this.taskRepository.findAll();
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Task> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Task save(Task object) {
        return taskRepository.save(object);
    }

    @Override
    public void delete(Task object) {
        taskRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void saveAll(List<Task> object) {

    }

    @Override
    public Boolean exist(Task object) {
        return null;
    }
}
