package com.adminpro20.clientes.repository;

import com.adminpro20.clientes.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
