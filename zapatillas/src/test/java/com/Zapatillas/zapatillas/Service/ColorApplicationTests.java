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
        // GIVEN: preparo un ID falso y un color falso como si existiera en la BD
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

        // Simulo que el repository encuentra el color por ID
        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorFalso));

        // WHEN: ejecuto el método real del service
        ColorDTO resultado = colorService.buscarPorId(idSimulado);

        // THEN: verifico que el DTO venga correcto
        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(colorFalso.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        // Verifico que el repository haya buscado por ID una sola vez
        verify(colorRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarColor_exitoso() {
        // GIVEN: preparo un color nuevo, como si viniera desde el controller
        Color colorNuevo = new Color();
        colorNuevo.setNombre("Negro");

        // Preparo el color guardado, simulando que la BD le asignó un ID
        Color colorGuardado = new Color();
        colorGuardado.setId(1);
        colorGuardado.setNombre("Negro");

        // Simulo que el repository guarda el color nuevo y devuelve el color con ID
        when(colorRepository.save(colorNuevo)).thenReturn(colorGuardado);

        // WHEN: ejecuto el método guardar del service
        ColorDTO resultado = colorService.guardarColor(colorNuevo);

        // THEN: verifico que el resultado venga correcto
        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Negro", resultado.getNombre(), "El nombre debe coincidir");

        // Verifico que el repository haya usado save una sola vez
        verify(colorRepository, times(1)).save(colorNuevo);
    }

    @Test
    void testObtenerTodos_exitoso() {
        // GIVEN: preparo colores falsos como si vinieran desde la base de datos
        Color color1 = new Color();
        color1.setId(1);
        color1.setNombre("Negro");

        Color color2 = new Color();
        color2.setId(2);
        color2.setNombre("Blanco");

        // Simulo que el repository devuelve una lista con 2 colores
        when(colorRepository.findAll()).thenReturn(List.of(color1, color2));

        // WHEN: ejecuto el método listar del service
        List<ColorDTO> resultado = colorService.obtenerTodos();

        // THEN: verifico que la lista venga correcta
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 colores");
        assertEquals("Negro", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Blanco", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        // Verifico que el repository se haya usado una sola vez
        verify(colorRepository, times(1)).findAll();
    }

    @Test
    void testActualizarColor_exitoso() {
        // GIVEN: preparo el ID de un color que ya existe
        Integer idSimulado = 1;

        // Este es el color que supuestamente ya está guardado en la BD
        Color colorExistente = new Color();
        colorExistente.setId(idSimulado);
        colorExistente.setNombre("Negro");

        // Estos son los datos nuevos que quiero actualizar
        Color datosNuevos = new Color();
        datosNuevos.setNombre("Azul");

        // Simulo que el repository encuentra el color por ID
        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorExistente));

        // Simulo que el repository guarda el color ya modificado
        when(colorRepository.save(colorExistente)).thenReturn(colorExistente);

        // WHEN: ejecuto el método actualizar del service
        Color resultado = colorService.actualizarColor(idSimulado, datosNuevos);

        // THEN: verifico que los datos se hayan actualizado correctamente
        assertNotNull(resultado, "El color actualizado no deberia ser nulo");
        assertEquals("Azul", resultado.getNombre(), "El nombre deberia actualizarse");

        // Verifico que primero buscó por ID y después guardó los cambios
        verify(colorRepository, times(1)).findById(idSimulado);
        verify(colorRepository, times(1)).save(colorExistente);
    }

    @Test
    void testEliminarColor_exitoso() {
        // GIVEN: preparo un color que existe en la base de datos
        Integer idSimulado = 1;

        Color colorExistente = new Color();
        colorExistente.setId(idSimulado);
        colorExistente.setNombre("Negro");

        // Simulo que el repository encuentra el color por ID
        when(colorRepository.findById(idSimulado)).thenReturn(Optional.of(colorExistente));

        // WHEN: ejecuto el método eliminar del service
        String resultado = colorService.eliminarColor(idSimulado);

        // THEN: verifico que el service devuelva un mensaje
        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        // Verifico que buscó por ID y luego eliminó el color encontrado
        verify(colorRepository, times(1)).findById(idSimulado);
        verify(colorRepository, times(1)).delete(colorExistente);
    }
}