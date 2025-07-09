package com.nexos.sotfware.Inventario.Automotriz.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mercancia {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true, nullable = false)
   @JoinColumn(name = "nombre_producto")
   private String nombreProducto;

   private int cantidad;

   @Column(name = "fecha_ingreso", nullable = false)
   private LocalDate fechaIngreso;

   @Column(name = "fecha_modificacion", nullable = true)
   private LocalDate fechaModificacion;

   @ManyToOne(optional = false)
   @JoinColumn(name = "usuario_registro_id")
   private Usuario usuarioRegistro;

   @ManyToOne(optional = true)
   @JoinColumn(name = "usuario_modifico_id", nullable = true)
   private Usuario usuarioModifico;

   @OneToMany(mappedBy = "mercancia", cascade = CascadeType.REMOVE)
   @JsonIgnore
   private List<HistorialMovimiento> movimientos;
}