package com.nexos.sotfware.Inventario.Automotriz.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaMapper;
import com.nexos.sotfware.Inventario.Automotriz.dtos.MercanciaResponse;
import com.nexos.sotfware.Inventario.Automotriz.entities.HistorialMovimiento;
import com.nexos.sotfware.Inventario.Automotriz.entities.Mercancia;
import com.nexos.sotfware.Inventario.Automotriz.entities.Usuario;
import com.nexos.sotfware.Inventario.Automotriz.exceptiones.InventarioException;
import com.nexos.sotfware.Inventario.Automotriz.repository.HistorialMovimientoRepository;
import com.nexos.sotfware.Inventario.Automotriz.repository.MercanciaRepository;
import com.nexos.sotfware.Inventario.Automotriz.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MercanciaServiceTest {

	@Mock
	private MercanciaRepository mercanciaRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private HistorialMovimientoRepository historialRepository;

	@Mock
	private MercanciaMapper mapper;

	@InjectMocks
	private MercanciaService mercanciaService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	//Test registra mercancia caso exitoso
	@Test
	void registrarMercancia() {
		// Arrange
		String nombreProducto = "Aceite";
		int cantidad = 10;
		LocalDate fechaIngreso = LocalDate.now();
		Long idUsuario = 1L;

		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);

		when(mercanciaRepository.existsByNombreProducto(nombreProducto)).thenReturn(false);
		when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));

		Mercancia mercanciaGuardada = Mercancia.builder()
															.nombreProducto(nombreProducto)
															.cantidad(cantidad)
															.fechaIngreso(fechaIngreso)
															.usuarioRegistro(usuario)
															.build();

		when(mercanciaRepository.save(any(Mercancia.class))).thenReturn(mercanciaGuardada);

		Mercancia result = mercanciaService.registrarMercancia(nombreProducto, cantidad, fechaIngreso, idUsuario);


		assertNotNull(result);
		assertEquals(nombreProducto, result.getNombreProducto());
		assertEquals(cantidad, result.getCantidad());
		verify(historialRepository, times(1)).save(any());
	}

	//Tes para cuando el producto ya existe

	@Test
	void registrarMercancia_ProductoYaExiste() {
		String nombreProducto = "Aceite";
		int cantidad = 10;
		LocalDate fechaIngreso = LocalDate.now();
		Long idUsuario = 1L;

		when(mercanciaRepository.existsByNombreProducto(nombreProducto)).thenReturn(true);

		InventarioException ex = assertThrows(InventarioException.class, () ->
				mercanciaService.registrarMercancia(nombreProducto, cantidad, fechaIngreso, idUsuario)
		);

		assertEquals("Ya existe una mercancía con ese nombre", ex.getMessage());
	}

	//Test Debera lanzar exception si ya existe la mercancia
	@Test
	void registrarMercanciaException() {
		when(mercanciaRepository.existsByNombreProducto("Duplicado")).thenReturn(true);
		InventarioException ex = assertThrows(InventarioException.class,
				() -> mercanciaService.registrarMercancia("Duplicado", 5, LocalDate.now(), 1L));
		assertEquals("Ya existe una mercancía con ese nombre", ex.getMessage());
		System.out.println("Mensaje de error: " + ex.getMessage());

	}
	//Test editar mercancia caso exitoso
	@Test
	void testEditarMercancia_Exito() {
		Long id = 1L;
		Long idUsuario = 2L;
		String nuevoNombre = "Producto Editado";
		int nuevaCantidad = 20;

		Mercancia mercanciaExistente = new Mercancia();
		mercanciaExistente.setId(id);
		mercanciaExistente.setNombreProducto("Producto Original");
		mercanciaExistente.setCantidad(10);
		mercanciaExistente.setFechaIngreso(LocalDate.now());
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);

		when(mercanciaRepository.findById(id)).thenReturn(Optional.of(mercanciaExistente));
		when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
		when(mercanciaRepository.save(any(Mercancia.class))).thenAnswer(i -> i.getArgument(0));
		when(historialRepository.save(any(HistorialMovimiento.class))).thenReturn(new HistorialMovimiento());

		Mercancia resultado = mercanciaService.editarMercancia(id, idUsuario, nuevoNombre, nuevaCantidad);

		assertNotNull(resultado);
		assertEquals(nuevoNombre, resultado.getNombreProducto());
		assertEquals(nuevaCantidad, resultado.getCantidad());
		assertEquals(usuario, resultado.getUsuarioModifico());

		verify(mercanciaRepository).save(any(Mercancia.class));
		verify(historialRepository).save(any(HistorialMovimiento.class));
	}

	//Test eliminar mercancia
	@Test
	void testEliminarMercancia_Exito() {
		Long idMercancia = 1L;
		Long idUsuario = 2L;

		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);

		Mercancia mercancia = new Mercancia();
		mercancia.setId(idMercancia);
		mercancia.setNombreProducto("Producto a Eliminar");
		mercancia.setUsuarioRegistro(usuario);

		when(mercanciaRepository.findById(idMercancia)).thenReturn(Optional.of(mercancia));
		when(historialRepository.save(any(HistorialMovimiento.class))).thenReturn(new HistorialMovimiento());

		Mercancia resultado = mercanciaService.eliminarMercancia(idMercancia, idUsuario);

		assertNotNull(resultado);
		assertEquals("Producto a Eliminar", resultado.getNombreProducto());

		verify(historialRepository).save(any(HistorialMovimiento.class));
		verify(mercanciaRepository).deleteById(idMercancia);
		System.out.println("Test eliminarMercancia ejecutado correctamente");
	}

	// Test eliminar mercancia - caso negativo: no se encuentra la mercancía
	@Test
	void testEliminarMercancia_MercanciaNoExiste() {
		Long idMercancia = 99L;
		Long idUsuario = 1L;

		// Simula que no se encuentra la mercancía
		when(mercanciaRepository.findById(idMercancia)).thenReturn(Optional.empty());

		// Verifica que se lanza GlicException
		InventarioException exception = assertThrows(InventarioException.class, () -> {
			mercanciaService.eliminarMercancia(idMercancia, idUsuario);
		});

		assertEquals("La mercancía no existe", exception.getMessage());

		System.out.println("test eliminarMercancia con mercancía no existente ejecutado correctamente");
	}

	//Buscar mercancia por id y devuelve el nombre del product
	@Test
	void testBuscarPorId() {
		Long id = 1L;
		Mercancia mercancia = new Mercancia();
		mercancia.setId(id);

		MercanciaResponse response = MercanciaResponse.builder()
																	 .id(id)
																	 .nombreProducto("Aceite")
																	 .build();

		when(mercanciaRepository.findById(id)).thenReturn(Optional.of(mercancia));
		when(mapper.toResponse(mercancia)).thenReturn(response);

		MercanciaResponse result = mercanciaService.buscarPorId(id);

		assertNotNull(result);
		assertEquals("Aceite", result.getNombreProducto());
		verify(mapper).toResponse(mercancia);
	}

	// Buscar mercancia por id - caso negativo: no existe la mercancía
	@Test
	void testBuscarPorId_MercanciaNoExiste() {
		Long id = 999L;

		// Simula que no se encuentra la mercancía
		when(mercanciaRepository.findById(id)).thenReturn(Optional.empty());

		// Verifica que se lanza GlicException con el mensaje esperado
		InventarioException exception = assertThrows(InventarioException.class, () -> {
			mercanciaService.buscarPorId(id);
		});

		assertEquals("La mercancía no existe", exception.getMessage());

		System.out.println("Test buscarPorId con mercancía no existente ejecutado correctamente");
	}

}
