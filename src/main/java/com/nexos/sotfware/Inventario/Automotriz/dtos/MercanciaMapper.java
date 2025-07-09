package com.nexos.sotfware.Inventario.Automotriz.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;

@Mapper(componentModel = "spring")
public interface MercanciaMapper {

   Mercancia toEntity(MercanciaRequest request);

   @Mapping(source = "usuarioRegistro.nombre", target = "nombreUsuario")
   MercanciaResponse toResponse(Mercancia mercancia);
}
