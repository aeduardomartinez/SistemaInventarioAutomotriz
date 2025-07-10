package com.nexos.sotfware.Inventario.Automotriz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nexos.sotfware.Inventario.Automotriz.dtos.CargoMapper;
import com.nexos.sotfware.Inventario.Automotriz.dtos.CargoRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.CargoResponse;
import com.nexos.sotfware.Inventario.Automotriz.entities.Cargo;
import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;
import com.nexos.sotfware.Inventario.Automotriz.repository.CargoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoService {

   private final CargoRepository cargoRepository;

   private final CargoMapper cargoMapper;

   public CargoResponse crearCargo(CargoRequest request) {
      Cargo cargo = cargoMapper.stringToCargo(request.getNombre());

      Cargo cargoGuardado = cargoRepository.save(cargo);

      CargoResponse response = new CargoResponse();
      response.setId(cargoGuardado.getId());
      response.setNombre(cargoGuardado.getNombre());

      return response;
   }

   public List<Cargo> listarTodos() {
      return cargoRepository.findAll();
   }

}
