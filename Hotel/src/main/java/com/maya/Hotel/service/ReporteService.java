package com.maya.Hotel.service;

import com.maya.Hotel.config.EncryptUtil;
import com.maya.Hotel.repository.FacturacionRepository;
import com.maya.Hotel.repository.ReservaRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    private final ReservaRepository reservaRepository;
    private final FacturacionRepository facturacionRepository;

    public ReporteService(ReservaRepository reservaRepository,
                          FacturacionRepository facturacionRepository) {
        this.reservaRepository = reservaRepository;
        this.facturacionRepository = facturacionRepository;
    }

    public List<Object[]> ocupacionDiaria(LocalDate inicio, LocalDate fin) {
        return reservaRepository.reporteOcupacionDiaria(inicio, fin);
    }

    public List<Object[]> ocupacionSemanal(LocalDate inicio, LocalDate fin) {
        return reservaRepository.reporteOcupacionSemanal(inicio, fin);
    }

    
    public List<Object[]> listaHuespedes(LocalDate inicio, LocalDate fin) {

        List<Object[]> lista = reservaRepository.reporteListaHuespedes(inicio, fin);

        
        for (Object[] fila : lista) {
            try {
                if (fila[2] != null) {
                    String correoEncriptado = fila[2].toString();
                    fila[2] = EncryptUtil.decrypt(correoEncriptado);
                }
            } catch (Exception e) {
                fila[2] = "Error al desencriptar";
            }
        }

        return lista;
    }

  
    public List<Object[]> ingresosDetalle(LocalDate inicio, LocalDate fin) {
        return facturacionRepository.reporteIngresosDetalle(inicio, fin);
    }

    public Double ingresosTotal(LocalDate inicio, LocalDate fin) {
        Double total = facturacionRepository.reporteIngresosTotal(inicio, fin);
        return total != null ? total : 0.0;
    }

    
    public List<Object[]> ingresosMensual(int anio) {
        return facturacionRepository.reporteIngresosMensual(anio);
    }

    
    public Double ingresosAnual(int anio) {
        Double total = facturacionRepository.reporteIngresosAnual(anio);
        return total != null ? total : 0.0;
    }
}

