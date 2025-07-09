package com.nexos.sotfware.Inventario.Automotriz.exceptiones;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(InventarioException.class)
   public ResponseEntity<Map<String, Object>> handleInventarioException(InventarioException ex) {
      Map<String, Object> error = new HashMap<>();
      error.put("timestamp", LocalDateTime.now());
      error.put("status", HttpStatus.BAD_REQUEST.value());
      error.put("error", "Operación no permitida");
      error.put("message", ex.getMessage());

      return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
   }

}