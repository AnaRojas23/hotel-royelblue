package com.maya.Hotel.controller;

import com.maya.Hotel.entity.Habitacion;
import com.maya.Hotel.entity.Reserva;
import com.maya.Hotel.service.HabitacionService;
import com.maya.Hotel.service.ReservaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

@RequestMapping("/habitaciones")
@CrossOrigin(origins = "http://localhost:3000")

public class HabitacionController {

    private final HabitacionService service;
    private final ReservaService serviceReserva;

    public HabitacionController(HabitacionService service,ReservaService serviceReserva){
        this.service = service;
        this.serviceReserva = serviceReserva;
    }
   

    @GetMapping
    public List<Habitacion> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public Habitacion obtener(@PathVariable Integer id){
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Habitacion guardar(@RequestBody Habitacion h){
        return service.guardar(h);
    }

    @PutMapping("/{id}")
    public Habitacion actualizar(@PathVariable Integer id, @RequestBody Habitacion h){
        return service.actualizar(id, h);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
    
   
}