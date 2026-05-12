package com.maya.Hotel.service;

import com.maya.Hotel.entity.Habitacion;
import com.maya.Hotel.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository repo;

    public HabitacionService(HabitacionRepository repo){
        this.repo = repo;
    }

    public List<Habitacion> listar(){
        return repo.findAll();
    }

    public Habitacion guardar(Habitacion h){
        return repo.save(h);
    }

    public Habitacion obtenerPorId(Integer id){
        return repo.findById(id).orElse(null);
    }

    public Habitacion actualizar(Integer id, Habitacion h){ 
        h.setIdHabitacion(id);
        return repo.save(h);
    }

    public void eliminar(Integer id){
        repo.deleteById(id);
    }
}