package com.adminpro20.clientes.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class IPhotoServiceImp implements IPhotoService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOADS_FOLDER = "src/main/resources/static/upload";
//    private final static String UPLOADS_FOLDER = "src/main/resources/img";

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        Path pathfoto = getPath(fileName);

        log.info(("pathfoto: " + pathfoto));
        Resource recurso = null;

        recurso = new UrlResource(pathfoto.toUri());
        if(!recurso.exists() || !recurso.isReadable())
            throw new RuntimeException("Error: no se puede cargar la imagen: "+
                    pathfoto.toString());

        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(uniqueFileName);
        //   Path rootAbsolutPath = rootPath.toAbsolutePath();
        log.info("rootPath: " + rootPath);
        //  log.info("rootAbsolutePath: " + rootAbsolutPath);
        Files.copy(file.getInputStream(), rootPath);

        return uniqueFileName;
    }

    @Override
    public boolean delete(String fileName) {
        Path rootPath = getPath(fileName);
        File archivo = rootPath.toFile();
        if(archivo.exists() && archivo.canRead()){
            if(archivo.delete()){
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename){
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
