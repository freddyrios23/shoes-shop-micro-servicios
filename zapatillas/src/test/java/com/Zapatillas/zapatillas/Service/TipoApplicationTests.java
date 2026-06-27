package com.Zapatillas.zapatillas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Zapatillas.zapatillas.DTO.TipoDTO;
import com.Zapatillas.zapatillas.model.Tipo;
import com.Zapatillas.zapatillas.repository.TipoRepository;
import com.Zapatillas.zapatillas.service.TipoService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class TipoApplicationTests {

    @Mock
    private TipoRepository tipoRepository;

    @InjectMocks
    private TipoService tipoService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 1;

        String nombreAleatorio = faker.options().option(
                "Urbana",
                "Running",
                "Basketball",
                "Skate",
                "Casual",
                "Deportiva"
        );

        Tipo tipoFalso = new Tipo();
        tipoFalso.setId(idSimulado);
        tipoFalso.setNombre(nombreAleatorio);

        when(tipoRepository.findById(idSimulado)).thenReturn(Optional.of(tipoFalso));

        TipoDTO resultado = tipoService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(tipoFalso.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        verify(tipoRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarTipo_exitoso() {
        Tipo tipoNuevo = new Tipo();
        tipoNuevo.setNombre("Running");

        Tipo tipoGuardado = new Tipo();
        tipoGuardado.setId(1);
        tipoGuardado.setNombre("Running");

        when(tipoRepository.save(tipoNuevo)).thenReturn(tipoGuardado);

        TipoDTO resultado = tipoService.guardaTipo(tipoNuevo);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Running", resultado.getNombre(), "El nombre debe coincidir");

        verify(tipoRepository, times(1)).save(tipoNuevo);
    }

    @Test
    void testObtenerTodos_exitoso() {
        Tipo tipo1 = new Tipo();
        tipo1.setId(1);
        tipo1.setNombre("Running");

        Tipo tipo2 = new Tipo();
        tipo2.setId(2);
        tipo2.setNombre("Casual");

        when(tipoRepository.findAll()).thenReturn(List.of(tipo1, tipo2));

        List<TipoDTO> resultado = tipoService.obtenertodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 tipos");
        assertEquals("Running", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Casual", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        verify(tipoRepository, times(1)).findAll();
    }

    @Test
    void testActualizarTipo_exitoso() {
        Integer idSimulado = 1;

        Tipo tipoExistente = new Tipo();
        tipoExistente.setId(idSimulado);
        tipoExistente.setNombre("Running");

        Tipo datosNuevos = new Tipo();
        datosNuevos.setNombre("Skate");

        when(tipoRepository.findById(idSimulado)).thenReturn(Optional.of(tipoExistente));

        when(tipoRepository.save(tipoExistente)).thenReturn(tipoExistente);

        Tipo resultado = tipoService.actualizarTipo(idSimulado, datosNuevos);

        assertNotNull(resultado, "El tipo actualizado no deberia ser nulo");
        assertEquals("Skate", resultado.getNombre(), "El nombre deberia actualizarse");

        verify(tipoRepository, times(1)).findById(idSimulado);
        verify(tipoRepository, times(1)).save(tipoExistente);
    }

    @Test
    void testEliminarTipo_exitoso() {
        Integer idSimulado = 1;

        Tipo tipoExistente = new Tipo();
        tipoExistente.setId(idSimulado);
        tipoExistente.setNombre("Running");

        when(tipoRepository.findById(idSimulado)).thenReturn(Optional.of(tipoExistente));

        String resultado = tipoService.eliminarTipo(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(tipoRepository, times(1)).findById(idSimulado);
        verify(tipoRepository, times(1)).delete(tipoExistente);
    }
}