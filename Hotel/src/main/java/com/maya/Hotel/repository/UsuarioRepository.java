package com.maya.Hotel.repository;

import com.maya.Hotel.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsuario(String usuario);
    Usuario findByUsuarioAndContrasena(String usuario, String contrasena);
    Optional<Usuario> findByDocumentoId(String documentoId);
    List<Usuario> findByTipoUsuario(String tipoUsuario);
   
}