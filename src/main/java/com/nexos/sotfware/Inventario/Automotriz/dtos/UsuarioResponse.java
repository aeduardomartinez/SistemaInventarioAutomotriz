package com.nexos.sotfware.Inventario.Automotriz.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponse {

   private Long id;

   private String nombre;

   private String cargo;
}