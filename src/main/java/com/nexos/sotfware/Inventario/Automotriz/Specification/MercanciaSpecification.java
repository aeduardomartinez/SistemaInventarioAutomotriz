package com.nexos.sotfware.Inventario.Automotriz.Specification;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;

import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class MercanciaSpecification {

   public static Specification<Mercancia> filtros(String nombre, String nombreUsuario, LocalDate fechaIngreso) {
      return (root, query, cb) -> {
         List<Predicate> predicates = new ArrayList<>();

         if (nombre != null && !nombre.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nombreProducto")), "%" + nombre.toLowerCase() + "%"));
         }

         if (nombreUsuario != null) {
            predicates.add(cb.like(cb.lower(root.get("usuarioRegistro").get("nombre")), "%" + nombreUsuario.toLowerCase() + "%"));
         }

         if (fechaIngreso != null) {
            predicates.add(cb.equal(root.get("fechaIngreso"), fechaIngreso));
         }

         return cb.and(predicates.toArray(new Predicate[0]));
      };
   }
}