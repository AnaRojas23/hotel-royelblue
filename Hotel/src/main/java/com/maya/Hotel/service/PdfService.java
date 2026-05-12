package com.maya.Hotel.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.io.image.ImageDataFactory;
import com.maya.Hotel.entity.*;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generarFacturaPDF(Reserva reserva, Facturacion factura) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            
            float[] headerCols = {400f, 200f};
            Table headerTable = new Table(headerCols);

            headerTable.addCell(new Paragraph("🏨 HOTEL ROYAL BLUE\nFactura de Reserva"));
            
            
            Image logo = new Image(ImageDataFactory.create("src/main/resources/static/LogoHotel.png"))
                            .setAutoScale(true);
            headerTable.addCell(logo);

            document.add(headerTable);
            document.add(new Paragraph("\n"));

            
            float[] colWidths = {150f, 300f};
            Table dataTable = new Table(colWidths);

            dataTable.addCell(new Paragraph("Cliente"));
            dataTable.addCell(new Paragraph(reserva.getUsuario().getNombre()));

            dataTable.addCell(new Paragraph("Habitación"));
            dataTable.addCell(new Paragraph(String.valueOf(reserva.getHabitacion().getNumHabitacion())));

            dataTable.addCell(new Paragraph("Fecha ingreso"));
            dataTable.addCell(new Paragraph(reserva.getFechaIngreso().toString()));

            dataTable.addCell(new Paragraph("Fecha salida"));
            dataTable.addCell(new Paragraph(reserva.getFechaSalida().toString()));

            dataTable.addCell(new Paragraph("Total a pagar"));
            dataTable.addCell(new Paragraph("$" + factura.getMonto()));

            dataTable.addCell(new Paragraph("Método de pago"));
            dataTable.addCell(new Paragraph(factura.getMetodoPago()));

            dataTable.addCell(new Paragraph("Estado del pago"));
            dataTable.addCell(new Paragraph(factura.getEstadoPago()));

            document.add(dataTable);

            document.add(new Paragraph("\nGracias por su visita 🙌"));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
    
    public byte[] generarFacturaCancelacionPDF(Reserva reserva, double multa) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            
            float[] headerCols = {400f, 200f};
            Table headerTable = new Table(headerCols);

            headerTable.addCell(new Paragraph("🏨 HOTEL ROYAL BLUE\nFactura de Cancelación"));
            Image logo = new Image(ImageDataFactory.create("src/main/resources/static/LogoHotel.png"))
                            .setAutoScale(true);
            headerTable.addCell(logo);

            document.add(headerTable);
            document.add(new Paragraph("\n"));

            
            float[] colWidths = {150f, 300f};
            Table dataTable = new Table(colWidths);

            dataTable.addCell(new Paragraph("Cliente"));
            dataTable.addCell(new Paragraph(reserva.getUsuario().getNombre()));

            dataTable.addCell(new Paragraph("Habitación"));
            dataTable.addCell(new Paragraph(String.valueOf(reserva.getHabitacion().getNumHabitacion())));

            dataTable.addCell(new Paragraph("Fecha ingreso"));
            dataTable.addCell(new Paragraph(reserva.getFechaIngreso().toString()));

            dataTable.addCell(new Paragraph("Fecha salida"));
            dataTable.addCell(new Paragraph(reserva.getFechaSalida().toString()));

            
            dataTable.addCell(new Paragraph("Penalización por cancelación"));
            dataTable.addCell(new Paragraph("$" + multa));

            document.add(dataTable);
            document.add(new Paragraph("\nEsperamos verle pronto nuevamente 🙌"));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
