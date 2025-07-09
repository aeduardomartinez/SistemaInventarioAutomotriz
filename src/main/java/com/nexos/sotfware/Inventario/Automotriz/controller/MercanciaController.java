package com.nexos.sotfware.Inventario.Automotriz.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaRequest;
import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;
import com.nexos.sotfware.Inventario.Automotriz.exceptiones.InventarioException;
import com.nexos.sotfware.Inventario.Automotriz.service.MercanciaService;

@RestController
@RequestMapping("/api/mercancia")
@RequiredArgsConstructor
public class MercanciaController {

   private final MercanciaService service;

   @PostMapping("/registrar")
   public Mercancia registrar(@RequestBody MercanciaRequest request) {
      return service.registrarMercancia(request.getNombreProducto(), request.getCantidad(), request.getFechaIngreso(), request.getIdUsuario());
   }

   @PutMapping("/editar")
   public Mercancia editar(@RequestBody MercanciaRequest request) {
      return service.editarMercancia(request.getId(), request.getIdUsuarioModifico(), request.getNombreProducto(), request.getCantidad());
   }

   @DeleteMapping("/eliminar")
   public String eliminar(@RequestParam Long idMercancia, @RequestParam Long idUsuario) {
      service.eliminarMercancia(idMercancia, idUsuario);
      return "Mercancía eliminada correctamente";
   }

   @GetMapping("/buscar")
   public ResponseEntity<List<Mercancia>> buscarMercancia(
         @RequestParam(required = false) String nombre,
         @RequestParam(required = false) Long idUsuario,
         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaIngreso) {

      // Validar que al menos un filtro esté presente
      if (nombre == null && idUsuario == null && fechaIngreso == null) {
         throw new InventarioException("Debe proporcionar al menos un filtro: nombre, idUsuario o fechaIngreso");
      }

      List<Mercancia> resultados = service.buscarPorFiltros(nombre, idUsuario, fechaIngreso);
      return ResponseEntity.ok(resultados);
   }


}
