package com.nexos.sotfware.Inventario.Automotriz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexos.sotfware.Inventario.Automotriz.dtos.ApiResponse;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioResponse;
import com.nexos.sotfware.Inventario.Automotriz.service.UsuarioService;

import lombok.RequiredArgsConstructor;

// Controlador para el CRUD de usuarios
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

   private final UsuarioService usuarioService;

   @PostMapping("/registrar")
   public ResponseEntity<ApiResponse<UsuarioResponse>> registrar(@RequestBody UsuarioRequest request) {
      UsuarioResponse response = usuarioService.crearUsuario(request);
      ApiResponse<UsuarioResponse> resultado = ApiResponse
            .<UsuarioResponse>builder()
            .mensaje("Usuario registrado correctamente!!!")
            .datos(response)
            .build();
      return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
   }
}
