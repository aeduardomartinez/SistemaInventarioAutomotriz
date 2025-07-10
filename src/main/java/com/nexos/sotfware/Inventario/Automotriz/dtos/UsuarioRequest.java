package com.nexos.sotfware.Inventario.Automotriz.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioRequest {

   private String nombre;

   private int edad;

   private LocalDate fechaIngreso;

   private Long idCargo;
}