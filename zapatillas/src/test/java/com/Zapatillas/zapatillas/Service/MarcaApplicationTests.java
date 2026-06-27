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

import com.Zapatillas.zapatillas.DTO.MarcaDTO;
import com.Zapatillas.zapatillas.model.Marca;
import com.Zapatillas.zapatillas.repository.MarcaRepository;
import com.Zapatillas.zapatillas.service.MarcaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class MarcaApplicationTests {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 51;

        String nombreAleatorio = faker.options().option(
                "Nike",
                "Adidas",
                "Puma",
                "Converse"
        );

        Marca marcaFalsa = new Marca();
        marcaFalsa.setId(idSimulado);
        marcaFalsa.setNombre(nombreAleatorio);

        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaFalsa));

        MarcaDTO resultado = marcaService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(marcaFalsa.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        verify(marcaRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarMarca_exitoso() {
        Marca marcaNueva = new Marca();
        marcaNueva.setNombre("Adidas");

        Marca marcaGuardada = new Marca();
        marcaGuardada.setId(1);
        marcaGuardada.setNombre("Adidas");

        when(marcaRepository.save(marcaNueva)).thenReturn(marcaGuardada);

        MarcaDTO resultado = marcaService.guardarMarca(marcaNueva);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Adidas", resultado.getNombre(), "El nombre debe coincidir");

        verify(marcaRepository, times(1)).save(marcaNueva);
    }

    @Test
    void testObtenerTodas_exitoso() {
        Marca marca1 = new Marca();
        marca1.setId(1);
        marca1.setNombre("Nike");

        Marca marca2 = new Marca();
        marca2.setId(2);
        marca2.setNombre("Adidas");

        when(marcaRepository.findAll()).thenReturn(List.of(marca1, marca2));

        List<MarcaDTO> resultado = marcaService.obtenerTodas();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 marcas");
        assertEquals("Nike", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Adidas", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        verify(marcaRepository, times(1)).findAll();
    }

    @Test
    void testActualizarMarca_exitoso() {
        Integer idSimulado = 1;

        Marca marcaExistente = new Marca();
        marcaExistente.setId(idSimulado);
        marcaExistente.setNombre("Nike");

        Marca datosNuevos = new Marca();
        datosNuevos.setNombre("Nike Actualizada");

        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaExistente));

        when(marcaRepository.save(marcaExistente)).thenReturn(marcaExistente);

        Marca resultado = marcaService.actualizarMarca(idSimulado, datosNuevos);

        assertNotNull(resultado, "La marca actualizada no deberia ser nula");
        assertEquals("Nike Actualizada", resultado.getNombre(), "El nombre deberia actualizarse");

        verify(marcaRepository, times(1)).findById(idSimulado);
        verify(marcaRepository, times(1)).save(marcaExistente);
    }

    @Test
    void testEliminarMarca_exitoso() {
        Integer idSimulado = 1;

        Marca marcaExistente = new Marca();
        marcaExistente.setId(idSimulado);
        marcaExistente.setNombre("Nike");

        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaExistente));

        String resultado = marcaService.eliminarMarca(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(marcaRepository, times(1)).findById(idSimulado);
        verify(marcaRepository, times(1)).delete(marcaExistente);
    }
}