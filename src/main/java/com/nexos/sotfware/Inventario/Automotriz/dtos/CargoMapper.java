package com.nexos.sotfware.Inventario.Automotriz.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.nexos.sotfware.Inventario.Automotriz.entities.Cargo;

@Mapper(componentModel = "spring")
public interface CargoMapper {
   @Named("stringToCargo")
   default Cargo stringToCargo(String nombre) {
      if (nombre == null) return null;
      Cargo cargo = new Cargo();
      cargo.setNombre(nombre);
      return cargo;
   }

   @Named("cargoToString")
   default String cargoToString(Cargo cargo) {
      if (cargo == null) return null;
      return cargo.getNombre();
   }
}