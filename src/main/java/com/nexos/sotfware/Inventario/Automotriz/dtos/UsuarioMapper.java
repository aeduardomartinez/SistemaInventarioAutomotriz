package com.nexos.sotfware.Inventario.Automotriz.dtos;

import org.mapstruct.Mapper;

import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

   Usuario toEntity(UsuarioRequest request);

   UsuarioResponse toResponse(Usuario usuario);
}