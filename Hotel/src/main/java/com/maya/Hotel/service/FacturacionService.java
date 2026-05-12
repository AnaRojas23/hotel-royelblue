package com.maya.Hotel.service;
import com.maya.Hotel.entity.Facturacion;
import com.maya.Hotel.entity.Reserva;
import com.maya.Hotel.repository.FacturacionRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class FacturacionService {

    private final FacturacionRepository facturaRepo;

    public FacturacionService(FacturacionRepository facturaRepo){
        this.facturaRepo = facturaRepo;
    }

    public Facturacion generarFactura(Reserva reserva, String metodoPago){
        
        double total = reserva.getTotal();

        Facturacion factura = new Facturacion();
        factura.setReserva(reserva);
        factura.setMonto(total);
        factura.setMetodoPago(metodoPago);
        factura.setFechaPago(LocalDate.now());
        factura.setEstadoPago("Pago");

        return facturaRepo.save(factura);
    }
    
}
