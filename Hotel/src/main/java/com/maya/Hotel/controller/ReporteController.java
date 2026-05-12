
package com.maya.Hotel.controller;


import com.maya.Hotel.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    
    @GetMapping("/ocupacion-diaria")
    public ResponseEntity<List<Object[]>> ocupacionDiaria(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(reporteService.ocupacionDiaria(inicio, fin));
    }

   
    @GetMapping("/ocupacion-semanal")
    public ResponseEntity<List<Object[]>> ocupacionSemanal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(reporteService.ocupacionSemanal(inicio, fin));
    }

      
    @GetMapping("/ingresos-detalle")
    public ResponseEntity<List<Object[]>> ingresosDetalle(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(reporteService.ingresosDetalle(inicio, fin));
    }

    
    @GetMapping("/ingresos-total")
    public ResponseEntity<Double> ingresosTotal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(reporteService.ingresosTotal(inicio, fin));
    }

   
    @GetMapping("/huespedes")
    public ResponseEntity<List<Object[]>> listaHuespedes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(reporteService.listaHuespedes(inicio, fin));
    }
    
    
    @GetMapping("/ingresos-mensual")
    public List<Object[]> ingresosMensual(@RequestParam int anio) {
        return reporteService.ingresosMensual(anio);
    }

   
    @GetMapping("/ingresos-anual")
    public Double ingresosAnual(@RequestParam int anio) {
        return reporteService.ingresosAnual(anio);
    }
}
