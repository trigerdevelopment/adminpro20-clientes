package com.adminpro20.clientes.security.repository;

import com.adminpro20.clientes.security.entity.Rol;
import com.adminpro20.clientes.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
