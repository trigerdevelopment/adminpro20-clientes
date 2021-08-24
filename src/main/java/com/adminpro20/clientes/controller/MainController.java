package com.adminpro20.clientes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MainController {


    @RequestMapping("/message")
    public ResponseEntity<String> Message()
    {
        return new ResponseEntity<String>("Hello", HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin/message")
    public ResponseEntity<String> MessageAuth()
    {
        return new ResponseEntity<String>("Hello this is ADMIN", HttpStatus.OK);

    }
    @RequestMapping("/user/message")
    public ResponseEntity<String> MessageUser()
    {
        return new ResponseEntity<String>("Hello this is User", HttpStatus.OK);

    }

    @PreAuthorize("hasRole('DEPOT')")
    @RequestMapping("/depot/message")
    public ResponseEntity<String> MessageDepot()
    {
        return new ResponseEntity<String>("Hello this is User Depot", HttpStatus.OK);

    }
}
