package com.maya.Hotel.controller;

import com.maya.Hotel.entity.Reserva;
import com.maya.Hotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> obtenerReservasPorUsuario(@PathVariable Integer usuarioId) {
        return reservaService.obtenerReservasPorUsuario(usuarioId);
    }

   
    @PostMapping
    public Reserva guardarReserva(@RequestBody Reserva reserva) {
        return reservaService.guardarReserva(reserva);
    }

    // Modificar reserva
    @PutMapping("/{id}")
    public Reserva modificarReserva(@PathVariable Integer id, @RequestBody Reserva reserva) {
        return reservaService.modificarReserva(id, reserva);
    }

    
    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Integer id) {
        reservaService.eliminarReserva(id);
    }

    
    @GetMapping("/fechas-ocupadas/{habitacionId}")
    public List<LocalDate> obtenerFechasOcupadas(@PathVariable Integer habitacionId) {
        List<LocalDate> fechasBase = reservaService.obtenerFechasOcupadas(habitacionId);
        List<LocalDate> fechasOcupadas = new ArrayList<>();

        
        for (int i = 0; i < fechasBase.size(); i += 2) {
            LocalDate inicio = fechasBase.get(i);
            LocalDate fin = (i + 1 < fechasBase.size()) ? fechasBase.get(i + 1) : inicio;

            LocalDate fecha = inicio;
            while (!fecha.isAfter(fin)) {
                fechasOcupadas.add(fecha);
                fecha = fecha.plusDays(1);
            }
        }

        return fechasOcupadas;
    }

    
    @GetMapping
    public List<Reserva> obtenerTodas() {
        return reservaService.obtenerTodas();
    }

    
    @PutMapping("/{id}/checkin")
    public Reserva checkIn(@PathVariable Integer id) {
        return reservaService.actualizarEstado(id, "En curso");
    }

    
    @PutMapping("/{id}/checkout")
    public Reserva checkOut(@PathVariable Integer id) {
        return reservaService.actualizarEstado(id, "Finalizado");
    }
}
