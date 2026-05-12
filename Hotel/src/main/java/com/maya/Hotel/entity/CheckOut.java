package com.maya.Hotel.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "check_out") 
public class CheckOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_check_out") 
    private Integer id;

    
    @OneToOne
    @JoinColumn(name = "id_reserva") 
    @JsonIgnore
    private Reserva reserva;

    @Column(name = "fecha_check_out") 
    private LocalDate fechaCheckOut;

    public CheckOut() {}

    
    public Integer getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public LocalDate getFechaCheckOut() {
        return fechaCheckOut;
    }

    public void setFechaCheckOut(LocalDate fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
    }
}
