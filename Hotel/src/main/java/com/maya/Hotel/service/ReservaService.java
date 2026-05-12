package com.maya.Hotel.service;

import com.maya.Hotel.entity.Reserva;
import com.maya.Hotel.entity.Usuario;
import com.maya.Hotel.repository.ReservaRepository;
import com.maya.Hotel.repository.UsuarioRepository;
import com.maya.Hotel.config.EncryptUtil; 
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> obtenerReservasPorUsuario(Integer usuarioId) {
        return reservaRepository.findAll().stream()
                .filter(r -> r.getUsuario() != null && r.getUsuario().getId().equals(usuarioId))
                .toList();
    }
    public List<LocalDate> obtenerFechasOcupadas(Integer idHabitacion) {
        List<Reserva> reservas = reservaRepository.findByHabitacion_IdHabitacion(idHabitacion);
        List<LocalDate> fechasOcupadas = new ArrayList<>();

        for (Reserva r : reservas) {
            LocalDate inicio = r.getFechaIngreso();
            LocalDate fin = r.getFechaSalida();


            for (LocalDate d = inicio; !d.isAfter(fin); d = d.plusDays(1)) {
                fechasOcupadas.add(d);
            }
        }

        return fechasOcupadas;
    }

    public Reserva modificarReserva(Integer id, Reserva nuevaReserva) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservaExistente.setFechaIngreso(nuevaReserva.getFechaIngreso());
        reservaExistente.setFechaSalida(nuevaReserva.getFechaSalida());
        reservaExistente.setTotal(nuevaReserva.getTotal());
        reservaExistente.setHabitacion(nuevaReserva.getHabitacion());

        Reserva reservaModificada = reservaRepository.save(reservaExistente);

        String asunto = "Reserva modificada - Hotel";
        String mensaje = "Se ha modificado una reserva:\n"
            + "Reserva ID: " + reservaModificada.getId() + "\n"
            + "Usuario ID: " + (reservaModificada.getUsuario() != null ? reservaModificada.getUsuario().getId() : "N/A") + "\n"
            + "Habitación ID: " + (reservaModificada.getHabitacion() != null ? reservaModificada.getHabitacion().getIdHabitacion() : "N/A") + "\n"
            + "Nueva fecha ingreso: " + reservaModificada.getFechaIngreso() + "\n"
            + "Nueva fecha salida: " + reservaModificada.getFechaSalida() + "\n"
            + "Nuevo total: $" + reservaModificada.getTotal();

        try {
            List<Usuario> admins = usuarioRepository.findByTipoUsuario("Administrador");
            for (Usuario admin : admins) {
              
                String correoDesencriptado = EncryptUtil.decrypt(admin.getCorreo());
                emailService.enviarCorreo(correoDesencriptado, asunto, mensaje);
            }
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }

        return reservaModificada;
    }

    
    public void eliminarReserva(Integer id) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservaRepository.delete(reservaExistente);

        String asunto = "Reserva eliminada - Hotel";
        String mensaje = "Se ha eliminado una reserva:\n"
            + "Reserva ID: " + reservaExistente.getId() + "\n"
            + "Usuario ID: " + (reservaExistente.getUsuario() != null ? reservaExistente.getUsuario().getId() : "N/A") + "\n"
            + "Habitación ID: " + (reservaExistente.getHabitacion() != null ? reservaExistente.getHabitacion().getIdHabitacion() : "N/A") + "\n"
            + "Fecha ingreso: " + reservaExistente.getFechaIngreso() + "\n"
            + "Fecha salida: " + reservaExistente.getFechaSalida() + "\n"
            + "Total: $" + reservaExistente.getTotal();

        try {
            List<Usuario> admins = usuarioRepository.findByTipoUsuario("Administrador");
            for (Usuario admin : admins) {
                // 🔹 Desencriptar correo antes de enviar
                String correoDesencriptado = EncryptUtil.decrypt(admin.getCorreo());
                emailService.enviarCorreo(correoDesencriptado, asunto, mensaje);
            }
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }
    }

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Reserva guardarReserva(Reserva reserva) {
        reserva.setFechaReserva(LocalDate.now()); 
        Reserva nuevaReserva = reservaRepository.save(reserva);

        String asunto = "Nueva Reserva - Hotel";
        String mensaje = "Nueva reserva registrada\n"
            + "Usuario ID: " + (reserva.getUsuario() != null ? reserva.getUsuario().getId() : "N/A") + "\n"
            + "Habitación ID: " + (reserva.getHabitacion() != null ? reserva.getHabitacion().getIdHabitacion() : "N/A") + "\n"
            + "Fecha ingreso: " + reserva.getFechaIngreso() + "\n"
            + "Fecha salida: " + reserva.getFechaSalida() + "\n"
            + "Total: $" + reserva.getTotal();

        try {
            List<Usuario> admins = usuarioRepository.findByTipoUsuario("Administrador");
            for (Usuario admin : admins) {
               
                String correoDesencriptado = EncryptUtil.decrypt(admin.getCorreo());
                emailService.enviarCorreo(correoDesencriptado, asunto, mensaje);
            }
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }

        return nuevaReserva;
    }

    public Reserva actualizarEstado(Integer id, String nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }
}
