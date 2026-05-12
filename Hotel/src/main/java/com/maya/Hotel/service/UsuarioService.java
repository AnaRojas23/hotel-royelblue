package com.maya.Hotel.service;

import com.maya.Hotel.entity.Usuario;
import com.maya.Hotel.repository.UsuarioRepository;
import com.maya.Hotel.config.EncryptUtil;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder passwordEncoder){
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    
    public Usuario crearUsuario(Usuario u){

        if(repo.findByUsuario(u.getUsuario()) != null){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El usuario ya existe"
            );
        }

        
        if (u.getCorreo() == null || !u.getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Correo inválido"
            );
        }

        
        u.setContrasena(passwordEncoder.encode(u.getContrasena()));

        
        u.setCorreo(EncryptUtil.encrypt(u.getCorreo()));

        return repo.save(u);
    }

   
    public List<Usuario> listar(){

        List<Usuario> usuarios = repo.findAll();

        for(Usuario u : usuarios){
            try {
                u.setCorreo(EncryptUtil.decrypt(u.getCorreo()));
            } catch(Exception e){
                // por si hay datos viejos sin cifrar
            }
        }

        return usuarios;
    }

    
    public Usuario obtenerUsuario(Integer id){

        Usuario u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"
                ));

        try {
            u.setCorreo(EncryptUtil.decrypt(u.getCorreo()));
        } catch(Exception e){}

        return u;
    }

   
    public Usuario login(String usuario, String contrasena){

        Usuario user = repo.findByUsuario(usuario);

        if(user == null){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"
            );
        }

        if(!passwordEncoder.matches(contrasena, user.getContrasena())){
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Contraseña incorrecta"
            );
        }

        try {
            user.setCorreo(EncryptUtil.decrypt(user.getCorreo()));
        } catch(Exception e){}

        return user;
    }

    
    public Usuario buscarPorDocumento(String documentoId) {

        Usuario u = repo.findByDocumentoId(documentoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró usuario con ese documento"
                ));

        try {
            u.setCorreo(EncryptUtil.decrypt(u.getCorreo()));
        } catch(Exception e){}

        return u;
    }
}

