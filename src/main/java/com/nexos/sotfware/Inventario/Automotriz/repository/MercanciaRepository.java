package com.nexos.sotfware.Inventario.Automotriz.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;

public interface MercanciaRepository extends JpaRepository<Mercancia, Long> {

   boolean existsByNombreProducto(String nombreProducto);

   @Query("SELECT m FROM Mercancia m WHERE " + "(:nombre IS NULL OR LOWER(m.nombreProducto) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND "
         + "(:idUsuario IS NULL OR m.usuarioRegistro.id = :idUsuario) AND " + "(:fechaIngreso IS NULL OR m.fechaIngreso = :fechaIngreso)")
   List<Mercancia> findByFiltros(@Param("nombre") String nombre, @Param("idUsuario") Long idUsuario, @Param("fechaIngreso") LocalDate fechaIngreso);

}