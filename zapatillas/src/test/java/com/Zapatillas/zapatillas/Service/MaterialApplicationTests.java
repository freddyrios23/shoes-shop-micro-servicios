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
        // GIVEN: preparo un ID falso y un material falso como si existiera en la BD
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

        // Simulo que el repository encuentra el material por ID
        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialFalso));

        // WHEN: ejecuto el método real del service
        MaterialDTO resultado = materialService.buscarPorId(idSimulado);

        // THEN: verifico que el DTO venga correcto
        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(materialFalso.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        // Verifico que el repository haya buscado por ID una sola vez
        verify(materialRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarMaterial_exitoso() {
        // GIVEN: preparo un material nuevo, como si viniera desde el controller
        Material materialNuevo = new Material();
        materialNuevo.setNombre("Cuero");

        // Preparo el material guardado, simulando que la BD le asignó un ID
        Material materialGuardado = new Material();
        materialGuardado.setId(1);
        materialGuardado.setNombre("Cuero");

        // Simulo que el repository guarda el material nuevo y devuelve el material con ID
        when(materialRepository.save(materialNuevo)).thenReturn(materialGuardado);

        // WHEN: ejecuto el método guardar del service
        MaterialDTO resultado = materialService.guardarMaterial(materialNuevo);

        // THEN: verifico que el resultado venga correcto
        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Cuero", resultado.getNombre(), "El nombre debe coincidir");

        // Verifico que el repository haya usado save una sola vez
        verify(materialRepository, times(1)).save(materialNuevo);
    }

    @Test
    void testObtenerTodos_exitoso() {
        // GIVEN: preparo materiales falsos como si vinieran desde la base de datos
        Material material1 = new Material();
        material1.setId(1);
        material1.setNombre("Cuero");

        Material material2 = new Material();
        material2.setId(2);
        material2.setNombre("Lona");

        // Simulo que el repository devuelve una lista con 2 materiales
        when(materialRepository.findAll()).thenReturn(List.of(material1, material2));

        // WHEN: ejecuto el método listar del service
        List<MaterialDTO> resultado = materialService.obtenerTodos();

        // THEN: verifico que la lista venga correcta
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 materiales");
        assertEquals("Cuero", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Lona", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        // Verifico que el repository se haya usado una sola vez
        verify(materialRepository, times(1)).findAll();
    }

    @Test
    void testActualizarMaterial_exitoso() {
        // GIVEN: preparo el ID de un material que ya existe
        Integer idSimulado = 1;

        // Este es el material que supuestamente ya está guardado en la BD
        Material materialExistente = new Material();
        materialExistente.setId(idSimulado);
        materialExistente.setNombre("Cuero");

        // Estos son los datos nuevos que quiero actualizar
        Material datosNuevos = new Material();
        datosNuevos.setNombre("Cuero Sintético");

        // Simulo que el repository encuentra el material por ID
        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialExistente));

        // Simulo que el repository guarda el material ya modificado
        when(materialRepository.save(materialExistente)).thenReturn(materialExistente);

        // WHEN: ejecuto el método actualizar del service
        Material resultado = materialService.actualizarMaterial(idSimulado, datosNuevos);

        // THEN: verifico que los datos se hayan actualizado correctamente
        assertNotNull(resultado, "El material actualizado no deberia ser nulo");
        assertEquals("Cuero Sintético", resultado.getNombre(), "El nombre deberia actualizarse");

        // Verifico que primero buscó por ID y después guardó los cambios
        verify(materialRepository, times(1)).findById(idSimulado);
        verify(materialRepository, times(1)).save(materialExistente);
    }

    @Test
    void testEliminarMaterial_exitoso() {
        // GIVEN: preparo un material que existe en la base de datos
        Integer idSimulado = 1;

        Material materialExistente = new Material();
        materialExistente.setId(idSimulado);
        materialExistente.setNombre("Cuero");

        // Simulo que el repository encuentra el material por ID
        when(materialRepository.findById(idSimulado)).thenReturn(Optional.of(materialExistente));

        // WHEN: ejecuto el método eliminar del service
        String resultado = materialService.eliminarMaterial(idSimulado);

        // THEN: verifico que el service devuelva un mensaje
        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        // Verifico que buscó por ID y luego eliminó el material encontrado
        verify(materialRepository, times(1)).findById(idSimulado);
        verify(materialRepository, times(1)).delete(materialExistente);
    }
}