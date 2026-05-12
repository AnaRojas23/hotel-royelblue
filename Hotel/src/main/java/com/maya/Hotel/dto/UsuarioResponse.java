package com.maya.Hotel.dto;

public class UsuarioResponse {
    private Integer idUsuario;
    private String nombre;
    private String usuario;
    private String tipoUsuario;

    public UsuarioResponse(Integer idUsuario, String nombre, String usuario, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
    }

    
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}