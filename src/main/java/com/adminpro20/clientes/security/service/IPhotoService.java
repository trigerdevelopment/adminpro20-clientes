package com.adminpro20.clientes.security.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IPhotoService {
    public Resource load(String fileName) throws MalformedURLException;
    public String copy(MultipartFile file) throws IOException;
    public boolean delete(String fileName);
}
