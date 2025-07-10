package com.nexos.sotfware.Inventario.Automotriz.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexos.sotfware.Inventario.Automotriz.Specification.MercanciaSpecification;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaMapper;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaResponse;
import com.nexos.sotfware.Inventario.Automotriz.entities.HistorialMovimiento;
import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;
import com.nexos.sotfware.Inventario.Automotriz.entities.TipoAccion;
import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;
import com.nexos.sotfware.Inventario.Automotriz.exceptiones.InventarioException;
import com.nexos.sotfware.Inventario.Automotriz.repository.HistorialMovimientoRepository;
import com.nexos.sotfware.Inventario.Automotriz.repository.MercanciaRepository;
import com.nexos.sotfware.Inventario.Automotriz.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercanciaService {

   private final MercanciaRepository mercanciaRepository;

   private final UsuarioRepository usuarioRepository;

   private final HistorialMovimientoRepository historialRepository;

   private final MercanciaMapper mapper;

   public Mercancia registrarMercancia(String nombreProducto, int cantidad, LocalDate fechaIngreso, Long idUsuario) {
      if (mercanciaRepository.existsByNombreProducto(nombreProducto)) {
         throw new InventarioException("Ya existe una mercancía con ese nombre");
      }
      if (cantidad <= 0) {
         throw new InventarioException("La cantidad debe ser mayor que cero");
      }
      if (fechaIngreso.isAfter(LocalDate.now())) {
         throw new InventarioException("La fecha de ingreso no puede ser futura");
      }

      Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new InventarioException("Usuario no encontrado"));

      Mercancia mercancia = Mercancia
            .builder()
            .nombreProducto(nombreProducto)
            .cantidad(cantidad)
            .fechaIngreso(fechaIngreso)
            .fechaModificacion(null)
            .usuarioRegistro(usuario)
            .usuarioModifico(null)
            .build();
      Mercancia guardada = mercanciaRepository.save(mercancia);

      historialRepository.save(HistorialMovimiento
            .builder()
            .tipoAccion(TipoAccion.REGISTRO)
            .fechaAccion(LocalDateTime.now())
            .mercancia(guardada)
            .usuarioAccion(usuario)
            .build());

      return guardada;
   }

   @Transactional
   public Mercancia editarMercancia(Long id, Long idUsuario, String nuevoNombre, int nuevaCantidad) {
      Mercancia mercancia = mercanciaRepository.findById(id).orElseThrow(() -> new InventarioException("Mercancía no encontrada"));

      Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new InventarioException("Usuario no encontrado"));

      if (nuevoNombre == null || nuevaCantidad <= 0) {
         throw new InventarioException("Datos inválidos para actualizar mercancía");
      }

      if (nuevaCantidad <= 0) {
         throw new InventarioException("La cantidad debe ser mayor que cero");
      }

      mercancia.setId(id);
      mercancia.setNombreProducto(nuevoNombre);
      mercancia.setCantidad(nuevaCantidad);
      mercancia.setFechaModificacion(LocalDate.now());
      mercancia.setUsuarioModifico(usuario);

      Mercancia actualizada = mercanciaRepository.save(mercancia);

      historialRepository.save(HistorialMovimiento
            .builder()
            .tipoAccion(TipoAccion.MODIFICACION)
            .fechaAccion(LocalDateTime.now())
            .mercancia(actualizada)
            .usuarioAccion(usuario)
            .build());

      return actualizada;
   }

   @Transactional
   public Mercancia eliminarMercancia(Long idMercancia, Long idUsuario) {
      Mercancia mercancia = mercanciaRepository.findById(idMercancia).orElseThrow(() -> new InventarioException("Mercancía no encontrada"));

      if (!mercancia.getUsuarioRegistro().getId().equals(idUsuario)) {
         throw new InventarioException("No puedes eliminar esta mercancía porque no eres quien la registró.");
      }

      // Opcional: registrar en el historial antes de eliminar
      historialRepository.save(HistorialMovimiento
            .builder()
            .tipoAccion(TipoAccion.ELIMINACION)
            .fechaAccion(LocalDateTime.now())
            .mercancia(mercancia)
            .usuarioAccion(mercancia.getUsuarioRegistro())
            .build());

      mercanciaRepository.deleteById(idMercancia);
      return mercancia;
   }

   public List<Mercancia> buscarPorFiltros(String nombre, String nombreUsuario, LocalDate fechaIngreso) {
      if (nombre == null && nombreUsuario == null && fechaIngreso == null) {
         throw new InventarioException("Debe proporcionar al menos un filtro");
      }

      return mercanciaRepository.findAll(MercanciaSpecification.filtros(nombre, nombreUsuario, fechaIngreso));
   }

   public MercanciaResponse buscarPorId(Long id) {
      Mercancia mercancia = mercanciaRepository.findById(id).orElseThrow(() -> new InventarioException("Mercancía no encontrada con ID: " + id));

      return mapper.toResponse(mercancia);
   }

   public List<Mercancia> listarTodo() {
      return mercanciaRepository.findAll();
   }

}