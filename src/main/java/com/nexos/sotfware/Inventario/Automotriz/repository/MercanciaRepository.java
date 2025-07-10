package com.nexos.sotfware.Inventario.Automotriz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;

public interface MercanciaRepository extends JpaRepository<Mercancia, Long>, JpaSpecificationExecutor<Mercancia> {
   boolean existsByNombreProducto(String nombreProducto);


}
