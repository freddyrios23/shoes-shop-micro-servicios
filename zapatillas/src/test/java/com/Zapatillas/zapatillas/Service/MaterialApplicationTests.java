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

import com.Zapatillas.zapatillas.DTO.MaterialDTO;
import com.Zapatillas.zapatillas.model.Material;
import com.Zapatillas.zapatillas.repository.MaterialRepository;
import com.Zapatillas.zapatillas.service.MaterialService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class MaterialApplicationTests {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialService materialService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 1;

        String nombreAleatorio = faker.options().option(
                "Cuero",
                "Lona",
                "Gamuza",
                "Sintético",
                "Malla",
                "Tela"
        );

        Material materialFalso = new Material();
        materialFalso.setId(idSimulado);
        materialFalso.setNombre(nombreAleatorio);

        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialFalso));

        MaterialDTO resultado = materialService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(materialFalso.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        verify(materialRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarMaterial_exitoso() {
        Material materialNuevo = new Material();
        materialNuevo.setNombre("Cuero");

        Material materialGuardado = new Material();
        materialGuardado.setId(1);
        materialGuardado.setNombre("Cuero");

        when(materialRepository.save(materialNuevo)).thenReturn(materialGuardado);

        MaterialDTO resultado = materialService.guardarMaterial(materialNuevo);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Cuero", resultado.getNombre(), "El nombre debe coincidir");

        verify(materialRepository, times(1)).save(materialNuevo);
    }

    @Test
    void testObtenerTodos_exitoso() {
        Material material1 = new Material();
        material1.setId(1);
        material1.setNombre("Cuero");

        Material material2 = new Material();
        material2.setId(2);
        material2.setNombre("Lona");

        when(materialRepository.findAll()).thenReturn(List.of(material1, material2));

        List<MaterialDTO> resultado = materialService.obtenerTodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 materiales");
        assertEquals("Cuero", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Lona", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        verify(materialRepository, times(1)).findAll();
    }

    @Test
    void testActualizarMaterial_exitoso() {
        Integer idSimulado = 1;

        Material materialExistente = new Material();
        materialExistente.setId(idSimulado);
        materialExistente.setNombre("Cuero");

        Material datosNuevos = new Material();
        datosNuevos.setNombre("Cuero Sintético");

        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialExistente));

        when(materialRepository.save(materialExistente)).thenReturn(materialExistente);

        Material resultado = materialService.actualizarMaterial(idSimulado, datosNuevos);

        assertNotNull(resultado, "El material actualizado no deberia ser nulo");
        assertEquals("Cuero Sintético", resultado.getNombre(), "El nombre deberia actualizarse");

        verify(materialRepository, times(1)).findById(idSimulado);
        verify(materialRepository, times(1)).save(materialExistente);
    }

    @Test
    void testEliminarMaterial_exitoso() {
        Integer idSimulado = 1;

        Material materialExistente = new Material();
        materialExistente.setId(idSimulado);
        materialExistente.setNombre("Cuero");

        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialExistente));

        String resultado = materialService.eliminarMaterial(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(materialRepository, times(1)).findById(idSimulado);
        verify(materialRepository, times(1)).delete(materialExistente);
    }
}