package com.nexos.sotfware.Inventario.Automotriz.dtos;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MercanciaResponse {

   private Long id;

   private String nombreProducto;

   private int cantidad;

   private LocalDate fechaIngreso;

   private String nombreUsuario;
}