package com.maya.Hotel.service;

import com.maya.Hotel.entity.CheckIn;
import com.maya.Hotel.entity.CheckOut;
import com.maya.Hotel.entity.Facturacion;
import com.maya.Hotel.entity.Habitacion;
import com.maya.Hotel.entity.Reserva;
import com.maya.Hotel.repository.CheckInRepository;
import com.maya.Hotel.repository.CheckOutRepository;
import com.maya.Hotel.repository.FacturacionRepository;
import com.maya.Hotel.repository.HabitacionRepository;
import com.maya.Hotel.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

    private final ReservaRepository reservaRepo;
    private final HabitacionRepository habitacionRepo;
    private final CheckInRepository checkInRepo;
    private final CheckOutRepository checkOutRepo;
    private final FacturacionRepository facturacionRepo;
    private final PdfService pdfService;

    public CheckService(ReservaRepository r,
                        HabitacionRepository h,
                        CheckInRepository ci,
                        CheckOutRepository co,
                        FacturacionRepository f,
                        PdfService p) {
        this.reservaRepo = r;
        this.habitacionRepo = h;
        this.checkInRepo = ci;
        this.checkOutRepo = co;
        this.facturacionRepo = f;
        this.pdfService = p;
    }

    
    public byte[] hacerCheckIn(Integer idReserva, String metodoPago) {
        Reserva reserva = reservaRepo.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getCheckIn() != null) {
            throw new RuntimeException("Ya se realizó check-in");
        }

     
        CheckIn check = new CheckIn();
        check.setReserva(reserva);
        check.setFechaCheckIn(LocalDate.now());
        checkInRepo.save(check);

       
        reserva.setCheckIn(check);

      
        reserva.setEstado("En curso");
        reservaRepo.save(reserva);

       
        Facturacion factura = new Facturacion();
        factura.setReserva(reserva);
        factura.setMonto(reserva.getTotal());
        factura.setMetodoPago(metodoPago);
        factura.setFechaPago(LocalDate.now());
        factura.setEstadoPago("Pago");
        facturacionRepo.save(factura);

        
        return pdfService.generarFacturaPDF(reserva, factura);
    }
     @Transactional
    
    public Reserva hacerCheckOut(Integer idReserva) {
        Reserva reserva = reservaRepo.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getCheckIn() == null) {
            throw new RuntimeException("Debe hacer check-in primero");
        }

        if (reserva.getCheckOut() != null) {
            throw new RuntimeException("Ya se realizó check-out");
        }

        
        Habitacion hab = reserva.getHabitacion();
        hab.setEstado("Libre");
        habitacionRepo.save(hab);

        
        CheckOut check = new CheckOut();
        check.setReserva(reserva);
        check.setFechaCheckOut(LocalDate.now());
        checkOutRepo.save(check);

      
        reserva.setCheckOut(check);

        
        reserva.setEstado("Finalizado");
        reservaRepo.save(reserva);

        return reserva;
    }
}


