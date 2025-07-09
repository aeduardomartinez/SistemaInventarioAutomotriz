package com.nexos.sotfware.Inventario.Automotriz.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MercanciaRequest {

   private Long id;

   private String nombreProducto;

   private int cantidad;

   private LocalDate fechaIngreso;

   private Long idUsuario;

   private Long idUsuarioModifico;
}
