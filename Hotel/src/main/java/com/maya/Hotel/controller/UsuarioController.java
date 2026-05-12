package com.maya.Hotel.controller;

import com.maya.Hotel.entity.Usuario;
import com.maya.Hotel.service.UsuarioService;
import com.maya.Hotel.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    
    @PostMapping("/registrar")
    public Usuario crear(@Valid @RequestBody Usuario u){
        return service.crearUsuario(u);
    }

    
    @GetMapping
    public List<Usuario> listar(){
        return service.listar();
    }

    
    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Integer id){
        return service.obtenerUsuario(id);
    }

   
    @PostMapping("/login-user")
    public Usuario login(@RequestBody LoginRequest req){
        return service.login(req.getUsuario(), req.getContrasena());
    }
    @GetMapping("/documento/{doc}")
    public Usuario buscarPorDocumento(@PathVariable String doc) {
        return service.buscarPorDocumento(doc);
    }
}