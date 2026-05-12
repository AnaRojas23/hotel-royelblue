package com.maya.Hotel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario") 
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido")
    private String apellido;

    @NotNull(message = "El teléfono es obligatorio")
    @Column(name = "telefono")
    private Long telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "correo")
    private String correo;

    @NotNull(message = "El documento es obligatorio")
    @JsonProperty("documento_id") // mapea JSON -> atributo
    @Column(name = "documento_id")
    private Integer documentoId;

    @NotBlank(message = "El tipo de usuario es obligatorio")
    @JsonProperty("tipo_usuario")
    @Column(name = "tipo_usuario")
    private String tipoUsuario;

    @NotBlank(message = "El país es obligatorio")
    @JsonProperty("pais_origen")
    @Column(name = "pais_origen")
    private String paisOrigen;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @JsonProperty("fecha_nac")
    @Column(name = "fecha_nac")
    private LocalDate fechaNac;

    @NotBlank(message = "El usuario es obligatorio")
    @Column(name = "usuario")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    @Column(name = "contrasena")
    private String contrasena;

    public Usuario() {}

    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Long getTelefono() { return telefono; }
    public void setTelefono(Long telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Integer getDocumentoId() { return documentoId; }
    public void setDocumentoId(Integer documentoId) { this.documentoId = documentoId; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }

    public LocalDate getFechaNac() { return fechaNac; }
    public void setFechaNac(LocalDate fechaNac) { this.fechaNac = fechaNac; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}


