package com.nexos.sotfware.Inventario.Automotriz.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaMapper;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaResponse;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioMapper;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioRequest;
import com.nexos.sotfware.Inventario.Automotriz.dtos.UsuarioResponse;
import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;
import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;
import com.nexos.sotfware.Inventario.Automotriz.exceptiones.InventarioException;
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

   public UsuarioResponse crearUsuario(UsuarioRequest request) {
      Usuario usuario = usuarioMapper.toEntity(request);
      return usuarioMapper.toResponse(usuarioRepository.save(usuario));
   }

}
