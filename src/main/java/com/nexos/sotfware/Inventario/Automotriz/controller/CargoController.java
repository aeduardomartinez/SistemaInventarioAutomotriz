package com.nexos.sotfware.Inventario.Automotriz.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexos.sotfware.Inventario.Automotriz.dtos.ApiResponse;
import com.nexos.sotfware.Inventario.Automotriz.dtos.CargoRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.CargoResponse;
import com.nexos.sotfware.Inventario.Automotriz.entities.Cargo;
import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;
import com.nexos.sotfware.Inventario.Automotriz.service.CargoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cargo")
@RequiredArgsConstructor
public class CargoController {

   private final CargoService cargoService;

   @PostMapping("/registrar")
   public ResponseEntity<ApiResponse<CargoResponse>> registrar(@RequestBody CargoRequest request) {
      CargoResponse response = cargoService.crearCargo(request);
      ApiResponse<CargoResponse> resultado = ApiResponse
            .<CargoResponse>builder()
            .mensaje("Cargo registrado correctamente!!!")
            .datos(response)
            .build();
      return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
   }

   @GetMapping
   public ResponseEntity<List<Cargo>> listarUsuarios() {
      List<Cargo> cargos = cargoService.listarTodos();
      return ResponseEntity.ok(cargos);
   }
}
