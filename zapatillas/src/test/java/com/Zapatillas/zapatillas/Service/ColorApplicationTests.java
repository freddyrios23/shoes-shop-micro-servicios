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

import com.Zapatillas.zapatillas.DTO.ColorDTO;
import com.Zapatillas.zapatillas.model.Color;
import com.Zapatillas.zapatillas.repository.ColorRepository;
import com.Zapatillas.zapatillas.service.ColorService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ColorApplicationTests {

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ColorService colorService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 1;

        String nombreAleatorio = faker.options().option(
                "Negro",
                "Blanco",
                "Rojo",
                "Azul",
                "Verde",
                "Gris"
        );

        Color colorFalso = new Color();
        colorFalso.setId(idSimulado);
        colorFalso.setNombre(nombreAleatorio);

        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorFalso));

        ColorDTO resultado = colorService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(colorFalso.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        verify(colorRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarColor_exitoso() {
        Color colorNuevo = new Color();
        colorNuevo.setNombre("Negro");

        Color colorGuardado = new Color();
        colorGuardado.setId(1);
        colorGuardado.setNombre("Negro");

        when(colorRepository.save(colorNuevo)).thenReturn(colorGuardado);

        ColorDTO resultado = colorService.guardarColor(colorNuevo);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Negro", resultado.getNombre(), "El nombre debe coincidir");

        verify(colorRepository, times(1)).save(colorNuevo);
    }

    @Test
    void testObtenerTodos_exitoso() {
        Color color1 = new Color();
        color1.setId(1);
        color1.setNombre("Negro");

        Color color2 = new Color();
        color2.setId(2);
        color2.setNombre("Blanco");

        when(colorRepository.findAll()).thenReturn(List.of(color1, color2));

        List<ColorDTO> resultado = colorService.obtenerTodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 colores");
        assertEquals("Negro", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Blanco", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        verify(colorRepository, times(1)).findAll();
    }

    @Test
    void testActualizarColor_exitoso() {
        Integer idSimulado = 1;

        Color colorExistente = new Color();
        colorExistente.setId(idSimulado);
        colorExistente.setNombre("Negro");

        Color datosNuevos = new Color();
        datosNuevos.setNombre("Azul");

        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorExistente));

        when(colorRepository.save(colorExistente)).thenReturn(colorExistente);

        Color resultado = colorService.actualizarColor(idSimulado, datosNuevos);

        assertNotNull(resultado, "El color actualizado no deberia ser nulo");
        assertEquals("Azul", resultado.getNombre(), "El nombre deberia actualizarse");

        verify(colorRepository, times(1)).findById(idSimulado);
        verify(colorRepository, times(1)).save(colorExistente);
    }

    @Test
    void testEliminarColor_exitoso() {
        Integer idSimulado = 1;

        Color colorExistente = new Color();
        colorExistente.setId(idSimulado);
        colorExistente.setNombre("Negro");

        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorExistente));

        String resultado = colorService.eliminarColor(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(colorRepository, times(1)).findById(idSimulado);
        verify(colorRepository, times(1)).delete(colorExistente);
    }
}