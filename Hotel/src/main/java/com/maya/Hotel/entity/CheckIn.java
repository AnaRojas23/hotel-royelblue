package com.maya.Hotel.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CheckIn")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_checkIn")
    private Integer id;

    
    @OneToOne
    @JoinColumn(name = "id_reserva")
    @JsonIgnore
    private Reserva reserva;

    @Column(name = "fecha_CheckIn")
    private LocalDate fechaCheckIn;

    public CheckIn() {}

    public Integer getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public LocalDate getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(LocalDate fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }
}

