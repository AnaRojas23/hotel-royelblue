package com.maya.Hotel.repository;

import com.maya.Hotel.entity.Facturacion;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Integer> {

    
    @Query("SELECT u.nombre, u.apellido, r.fechaIngreso, r.fechaSalida, h.numHabitacion, f.monto " +
           "FROM Facturacion f " +
           "JOIN f.reserva r " +
           "JOIN r.usuario u " +
           "JOIN r.habitacion h " +
           "WHERE f.estadoPago = 'Pago' " + 
           "AND f.fechaPago BETWEEN :inicio AND :fin")
    List<Object[]> reporteIngresosDetalle(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    
    @Query("SELECT COALESCE(SUM(f.monto),0) " +
           "FROM Facturacion f " +
           "WHERE f.estadoPago = 'Pago' " + 
           "AND f.fechaPago BETWEEN :inicio AND :fin")
    Double reporteIngresosTotal(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);


    
    @Query("SELECT FUNCTION('MONTH', f.fechaPago), SUM(f.monto) " +
           "FROM Facturacion f " +
           "WHERE f.estadoPago = 'Pago' " +
           "AND FUNCTION('YEAR', f.fechaPago) = :anio " +
           "GROUP BY FUNCTION('MONTH', f.fechaPago) " +
           "ORDER BY FUNCTION('MONTH', f.fechaPago)")
    List<Object[]> reporteIngresosMensual(@Param("anio") int anio);

    
    @Query("SELECT COALESCE(SUM(f.monto),0) " +
           "FROM Facturacion f " +
           "WHERE f.estadoPago = 'Pago' " +
           "AND FUNCTION('YEAR', f.fechaPago) = :anio")
    Double reporteIngresosAnual(@Param("anio") int anio);
}

