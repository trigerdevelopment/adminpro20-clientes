package com.adminpro20.clientes.security.controller;


import com.adminpro20.clientes.security.dto.NuevoUsuario;
import com.adminpro20.clientes.security.entity.Usuario;
import com.adminpro20.clientes.security.repository.UsuarioRepository;
import com.adminpro20.clientes.security.service.IPhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;

//@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuariosController {

    public final UsuarioRepository usuarioRepository;
    public final PasswordEncoder passwordEncoder;
    public final IPhotoService iPhotoService;

    public UsuariosController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, IPhotoService iPhotoService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.iPhotoService = iPhotoService;
    }


    // PETICION DE TODOS LOS SUPPLIERS
    @RequestMapping(value = "/get-usuarios", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> getAllSuppliers() {
        try {
            List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

            return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
        } catch (Error e) {
            List<Usuario> usuarios = null;
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update-usuario", method = RequestMethod.PUT)
    public ResponseEntity<String> updateSupplier(@RequestBody Usuario usuario) {

        Optional<Usuario> bdUsuario = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
        bdUsuario.get().setEnabled(usuario.getEnabled());
        usuarioRepository.save(bdUsuario.get());
        String message = "El Usuario se actualizo en la base de datos";
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/update-profile", method = RequestMethod.PUT)
    public ResponseEntity<String> guardar(
            @Valid NuevoUsuario nuevoUsuario,
            BindingResult result,
            //  @PathVariable String filename,
            SessionStatus status,
            @RequestParam("file") MultipartFile file)
            throws IOException {

        System.out.println("USER " + nuevoUsuario.getNombre() + "Foto : " + nuevoUsuario.getPhotoName());
        System.out.println("USER ID " + nuevoUsuario.getId());
        System.out.println("File " + file);
        System.out.println("Original Image Byte Size - " + file.getBytes().length);

        String uniqueFilename = null;
        try {
            uniqueFilename = iPhotoService.copy(file);
            nuevoUsuario.setPhotoName(uniqueFilename);

        } catch (IOException e) {
            e.printStackTrace();
       }
//        ImageModel imageModel =
//                new ImageModel(uniqueFilename, file.getContentType(), compressBytes(file.getBytes()));

        nuevoUsuario.setPhotoName(uniqueFilename);

        Usuario usuarioBD = usuarioRepository.findByNombreUsuario(nuevoUsuario.getNombreUsuario()).get();
       usuarioBD.setApellido(nuevoUsuario.getApellido());
       usuarioBD.setEmail(nuevoUsuario.getEmail());
       usuarioBD.setNombre(nuevoUsuario.getNombre());
       usuarioBD.setPhotoName(nuevoUsuario.getPhotoName());

        usuarioRepository.save(usuarioBD);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    // PETICION DE BUSQUEDA DE SUPPLIER BY ID
    @RequestMapping(value = "/get-usuario-by-name/{username}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getUsuarioByUserName(@PathVariable(value = "username") String username) {
        try {

            Usuario usuario = usuarioRepository.findByNombreUsuario(username).get();
            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
        } catch (Error e) {
            Usuario usuario = null;
            return new ResponseEntity<Usuario>(usuario, HttpStatus.CONFLICT);
        }
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }
}
