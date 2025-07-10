package com.nexos.sotfware.Inventario.Automotriz.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;

@Mapper(componentModel = "spring" , uses = CargoMapper.class)
public interface UsuarioMapper {


   Usuario toEntity(UsuarioRequest request);

   @Mapping(source = "cargo", target = "cargo", qualifiedByName = "cargoToString")
   UsuarioResponse toResponse(Usuario usuario);
}
