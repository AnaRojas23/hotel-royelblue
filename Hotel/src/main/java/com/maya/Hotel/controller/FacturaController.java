package com.maya.Hotel.controller;

import com.maya.Hotel.entity.*;
import com.maya.Hotel.repository.ReservaRepository;
import com.maya.Hotel.service.FacturacionService;
import com.maya.Hotel.service.PdfService;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/facturacion")
@CrossOrigin(origins = "http://localhost:3000")
public class FacturaController {

    private final PdfService pdfService;
    private final ReservaRepository reservaRepository;

    public FacturaController(PdfService pdfService, ReservaRepository reservaRepository) {
        this.pdfService = pdfService;
        this.reservaRepository = reservaRepository;
    }

    @PostMapping("/pdf-cancelacion")
    public ResponseEntity<byte[]> generarFacturaCancelacion(@RequestBody Map<String, Object> body) {
        Integer idReserva = (Integer) body.get("idReserva");
        Double multa = Double.valueOf(body.get("multa").toString());

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        byte[] pdfBytes = pdfService.generarFacturaCancelacionPDF(reserva, multa);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "factura_cancelacion_" + idReserva + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
