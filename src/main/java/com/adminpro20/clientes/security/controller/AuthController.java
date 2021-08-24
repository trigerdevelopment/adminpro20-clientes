package com.adminpro20.clientes.security.controller;

import com.adminpro20.clientes.dto.Mensaje;
import com.adminpro20.clientes.security.dto.JwtDto;
import com.adminpro20.clientes.security.dto.LoginUsuario;
import com.adminpro20.clientes.security.dto.NuevoUsuario;
import com.adminpro20.clientes.security.entity.Rol;
import com.adminpro20.clientes.security.entity.Usuario;
import com.adminpro20.clientes.security.enums.RolNombre;
import com.adminpro20.clientes.security.jwt.JwtProvider;
import com.adminpro20.clientes.security.repository.UsuarioRepository;
import com.adminpro20.clientes.security.service.RolService;
import com.adminpro20.clientes.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "https://triger.admin-pro20.com")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UsuarioRepository usuarioRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);

        if (nuevoUsuario.getId() == null) {
            if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
                return new ResponseEntity(new Mensaje("El nombre de Usuario ya existe"), HttpStatus.BAD_REQUEST);
            if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
                return new ResponseEntity(new Mensaje("El email ya existe"), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario =
                new Usuario(nuevoUsuario.getId(), nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        nuevoUsuario.getPhotoName(),passwordEncoder.encode(nuevoUsuario.getPassword()), nuevoUsuario.getEnabled());
        Set<Rol> roles = new HashSet<>();
//        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

        if (nuevoUsuario.getRoles().contains("contabilidad")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_CONTA).get());

        }
        if (nuevoUsuario.getRoles().contains("ventas")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_VENTAS).get());

        }
        if (nuevoUsuario.getRoles().contains("compras")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_COMPRAS).get());

        }

        if (nuevoUsuario.getRoles().contains("almacen")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ALMACEN).get());

        }

        if (nuevoUsuario.getRoles().contains("produccion")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_PRODUCCION).get());

        }

        if (nuevoUsuario.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_CONTA).get());
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_VENTAS).get());
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_PRODUCCION).get());
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ALMACEN).get());
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_COMPRAS).get());

        }

        if (nuevoUsuario.getId() != null) {
            Usuario usuarioBd = usuarioService.getByNombreUsuario(nuevoUsuario.getNombreUsuario()).get();
            usuario.setPassword(usuarioBd.getPassword());
        }


        usuario.setRoles(roles);
        usuarioService.save(usuario);
//        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

//        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){

            System.out.println("LOGIN " + loginUsuario.getNombreUsuario());
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        }

        if (!usuarioService.existsByNombreUsuario(loginUsuario.getNombreUsuario())) {
            System.out.println("LOGIN " + loginUsuario.getNombreUsuario());

            String message = new String("ERROR EN LOS DATOS DE LOGIN");
            return new ResponseEntity(message, HttpStatus.NOT_FOUND);

        }
//        Usuario usuario = usuarioService.getByNombreUsuario(loginUsuario.getNombreUsuario()).get();
//
//        /*--------------------Si el Usuario no esta habilitado manda un mensaje 403--------------------------------------------------*/
//        if (!usuario.getEnabled()) {
//            return new ResponseEntity(new Mensaje("El Usuario no esta activo, contacte con su administrador"), HttpStatus.FORBIDDEN);
//
//        }

       Usuario usuario = usuarioService.getByNombreUsuario(loginUsuario.getNombreUsuario()).get();
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(),usuario.getNombre(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}