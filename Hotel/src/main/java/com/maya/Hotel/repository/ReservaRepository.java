package com.maya.Hotel.repository;

import com.maya.Hotel.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    
    List<Reserva> findByUsuario_Id(Integer usuarioId);

   
    List<Reserva> findByHabitacion_IdHabitacion(Integer idHabitacion);

  
    @Query("SELECT r.fechaIngreso FROM Reserva r WHERE r.habitacion.idHabitacion = :idHabitacion")
    List<LocalDate> obtenerFechasOcupadas(@Param("idHabitacion") Integer idHabitacion);

    
    @Query("SELECT r.fechaIngreso, COUNT(r) " +
           "FROM Reserva r " +
           "WHERE r.estado = 'En curso' AND r.fechaIngreso BETWEEN :inicio AND :fin " +
           "GROUP BY r.fechaIngreso")
    List<Object[]> reporteOcupacionDiaria(@Param("inicio") LocalDate inicio,
                                          @Param("fin") LocalDate fin);

  
    @Query("SELECT FUNCTION('WEEK', r.fechaIngreso), COUNT(r) " +
           "FROM Reserva r " +
           "WHERE r.estado = 'En curso' AND r.fechaIngreso BETWEEN :inicio AND :fin " +
           "GROUP BY FUNCTION('WEEK', r.fechaIngreso)")
    List<Object[]> reporteOcupacionSemanal(@Param("inicio") LocalDate inicio,
                                           @Param("fin") LocalDate fin);

    
    @Query("SELECT u.nombre, u.apellido, u.correo, r.fechaIngreso, r.fechaSalida, h.numHabitacion, r.estado " +
           "FROM Reserva r JOIN r.usuario u JOIN r.habitacion h " +
           "WHERE r.estado IN ('En curso','Finalizado') AND r.fechaIngreso BETWEEN :inicio AND :fin")
    List<Object[]> reporteListaHuespedes(@Param("inicio") LocalDate inicio,
                                         @Param("fin") LocalDate fin);
}

