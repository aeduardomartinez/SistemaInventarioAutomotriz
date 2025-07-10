package com.nexos.sotfware.Inventario.Automotriz.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaMapper;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaResponse;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioMapper;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioResponse;
import com.nexos.sotfware.Inventario.Automotriz.entities.Cargo;
import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;
import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;
import com.nexos.sotfware.Inventario.Automotriz.exceptiones.InventarioException;
import com.nexos.sotfware.Inventario.Automotriz.repository.CargoRepository;
import com.nexos.sotfware.Inventario.Automotriz.repository.MercanciaRepository;
import com.nexos.sotfware.Inventario.Automotriz.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

   private final UsuarioRepository usuarioRepository;

   private final MercanciaRepository mercanciaRepository;

   private final UsuarioMapper usuarioMapper;

   private final CargoRepository cargoRepository;

   public UsuarioResponse crearUsuario(UsuarioRequest request) {
      Cargo cargo = cargoRepository.findById(request.getIdCargo())
                                   .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

      Usuario usuario = usuarioMapper.toEntity(request);
      usuario.setCargo(cargo);
      return usuarioMapper.toResponse(usuarioRepository.save(usuario));
   }

   public List<Usuario> listarTodos() {
      return usuarioRepository.findAll();
   }


}
