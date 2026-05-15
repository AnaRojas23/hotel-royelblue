package com.maya.Hotel.controller;

import com.maya.Hotel.service.CheckService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/check")
public class CheckController {

    private final CheckService checkService;

    public CheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    
    @GetMapping("/checkin/{id}/pdf")
    public ResponseEntity<byte[]> hacerCheckInPDF(@PathVariable Integer id,
                                                  @RequestParam String metodoPago) {
        byte[] pdfBytes = checkService.hacerCheckIn(id, metodoPago);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura_checkin.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
