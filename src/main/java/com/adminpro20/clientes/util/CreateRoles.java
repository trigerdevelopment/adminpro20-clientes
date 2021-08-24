package com.adminpro20.clientes.util;

import com.adminpro20.clientes.security.dto.NuevoUsuario;
import com.adminpro20.clientes.security.entity.Rol;
import com.adminpro20.clientes.security.entity.Usuario;
import com.adminpro20.clientes.security.enums.RolNombre;
import com.adminpro20.clientes.security.service.RolService;
import com.adminpro20.clientes.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UsuarioService usuarioService;


    @Override
    public void run(String... args) throws Exception {
         Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
//         Rol rolUser = new Rol(RolNombre.ROLE_USER);
         Rol rolConta = new Rol(RolNombre.ROLE_CONTA);
        Rol rolSales = new Rol(RolNombre.ROLE_VENTAS);
        Rol rolProduction = new Rol(RolNombre.ROLE_PRODUCCION);
        Rol rolDepot = new Rol(RolNombre.ROLE_ALMACEN);
        Rol rolPurchase = new Rol(RolNombre.ROLE_COMPRAS);

//        CustomerType customerType = new CustomerType(1L, "Cadenas");
//
//        CustomerType customerType1 = new CustomerType(2L, "Distribuidores");
//
//        CustomerType customerType2 = new CustomerType(3L, "Maquilas");
//
//        CustomerType customerType3 = new CustomerType(4L, "USA");
//
//        CustomerType customerType4 = new CustomerType(5L, "Fruterias");
//
//        CustomerType customerType5 = new CustomerType(6L, "Minisuper");
//
//        CustomerType customerType6 = new CustomerType(7L, "Tiendas Naturistas");



        rolService.save(rolAdmin);
//         rolService.save(rolUser);
         rolService.save(rolSales);
         rolService.save(rolProduction);
         rolService.save(rolDepot);
         rolService.save(rolPurchase);
         rolService.save(rolConta);

        NuevoUsuario nuevoUsuario = new NuevoUsuario();

        Set<Rol> roles = new HashSet<>();
        nuevoUsuario.setNombre("Tannia");
        nuevoUsuario.setApellido("Ramos");
        nuevoUsuario.setNombreUsuario("admin");
        nuevoUsuario.setEmail("tannia@gmail.com");
        nuevoUsuario.setEnabled(true);
        nuevoUsuario.setPassword("12345");
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_CONTA).get());
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_VENTAS).get());
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_COMPRAS).get());
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_PRODUCCION).get());
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_ALMACEN).get());
        Usuario usuario =
                new Usuario(nuevoUsuario.getId(), nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        nuevoUsuario.getPhotoName(),passwordEncoder.encode(nuevoUsuario.getPassword()), nuevoUsuario.getEnabled());
        usuario.setRoles(roles);
        usuarioService.save(usuario);

//        customerTypeRepsitory.save(customerType);
//        customerTypeRepsitory.save(customerType1);
//        customerTypeRepsitory.save(customerType2);
//        customerTypeRepsitory.save(customerType3);
//        customerTypeRepsitory.save(customerType4);
//        customerTypeRepsitory.save(customerType5);
//        customerTypeRepsitory.save(customerType6);
    }
}