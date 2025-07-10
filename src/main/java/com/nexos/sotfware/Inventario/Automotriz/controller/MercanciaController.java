package com.nexos.sotfware.Inventario.Automotriz.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaResponse;
import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;
import com.nexos.sotfware.Inventario.Automotriz.exceptiones.InventarioException;
import com.nexos.sotfware.Inventario.Automotriz.service.MercanciaService;

@RestController
@RequestMapping("/api/mercancia")
@RequiredArgsConstructor
public class MercanciaController {

   private final MercanciaService service;

   @PostMapping("/registrar")
   public ResponseEntity<?> registrar(@RequestBody MercanciaRequest request) {
      Mercancia nueva =  service.registrarMercancia(request.getNombreProducto(), request.getCantidad(), request.getFechaIngreso(), request.getIdUsuario());
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Map.of(
                  "mensaje", "Mercancía registrada correctamente",
                  "mercancia", nueva
            ));
   }

   @PutMapping("/editar")
   public ResponseEntity<?> editar(@RequestBody MercanciaRequest request) {
      Mercancia actualizada = service.editarMercancia(request.getId(), request.getIdUsuarioModifico(), request.getNombreProducto(), request.getCantidad());

      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Map.of(
                  "mensaje", "Datos actualizados correctamente",
                  "mercancia", actualizada
            ));
   }

   @DeleteMapping("/eliminar")
   public ResponseEntity<?> eliminar(@RequestParam Long idMercancia, @RequestParam Long idUsuario) {
      Mercancia delete = service.eliminarMercancia(idMercancia, idUsuario);
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(Map.of(
                  "mensaje", "Mercancía eliminada correctamente",
                  "mercancia", delete
            ));

   }

   @GetMapping("/buscar")
   public ResponseEntity<List<Mercancia>> buscarMercancia(
         @RequestParam(required = false) String nombre,
         @RequestParam(required = false) String nombreUsuario,
         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaIngreso) {


      List<Mercancia> resultados = service.buscarPorFiltros(nombre, nombreUsuario, fechaIngreso);
      return ResponseEntity.ok(resultados);
   }
   @GetMapping("/con/{id}")
   public ResponseEntity<MercanciaResponse> buscarPorId(@PathVariable Long id) {
      MercanciaResponse respuesta = service.buscarPorId(id);
      if (respuesta != null) {
         return ResponseEntity.ok(respuesta);
      } else {
         return ResponseEntity.notFound().build();
      }
   }

   @GetMapping("/listar-todo")
   public ResponseEntity<List<Mercancia>> listarTodaLaMercancia() {
      List<Mercancia> lista = service.listarTodo();
      return ResponseEntity.ok(lista);
   }


}
