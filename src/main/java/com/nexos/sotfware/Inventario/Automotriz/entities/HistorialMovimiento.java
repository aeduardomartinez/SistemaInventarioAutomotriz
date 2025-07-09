package com.nexos.sotfware.Inventario.Automotriz.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialMovimiento {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Enumerated(EnumType.STRING)
   private TipoAccion tipoAccion;

   private LocalDateTime fechaAccion;

   @ManyToOne(optional = false)
   private Mercancia mercancia;

   @ManyToOne(optional = false)
   @JoinColumn(name = "usuario_Accion")
   private Usuario usuarioAccion;
}