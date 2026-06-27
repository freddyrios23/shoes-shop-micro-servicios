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
        // GIVEN: preparo un ID falso y una marca falsa como si existiera en la BD
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

        // Simulo que el repository encuentra la marca por ID
        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaFalsa));

        // WHEN: ejecuto el método real del service
        MarcaDTO resultado = marcaService.buscarPorId(idSimulado);

        // THEN: verifico que el DTO venga correcto
        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(marcaFalsa.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        // Verifico que el repository haya buscado por ID una sola vez
        verify(marcaRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarMarca_exitoso() {
        // GIVEN: preparo una marca nueva, como si viniera desde el controller
        Marca marcaNueva = new Marca();
        marcaNueva.setNombre("Adidas");

        // Preparo la marca guardada, simulando que la BD le asignó un ID
        Marca marcaGuardada = new Marca();
        marcaGuardada.setId(1);
        marcaGuardada.setNombre("Adidas");

        // Simulo que el repository guarda la marca nueva y devuelve la marca con ID
        when(marcaRepository.save(marcaNueva)).thenReturn(marcaGuardada);

        // WHEN: ejecuto el método guardar del service
        MarcaDTO resultado = marcaService.guardarMarca(marcaNueva);

        // THEN: verifico que el resultado venga correcto
        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Adidas", resultado.getNombre(), "El nombre debe coincidir");

        // Verifico que el repository haya usado save una sola vez
        verify(marcaRepository, times(1)).save(marcaNueva);
    }

    @Test
    void testObtenerTodas_exitoso() {
        // GIVEN: preparo marcas falsas como si vinieran desde la base de datos
        Marca marca1 = new Marca();
        marca1.setId(1);
        marca1.setNombre("Nike");

        Marca marca2 = new Marca();
        marca2.setId(2);
        marca2.setNombre("Adidas");

        // Simulo que el repository devuelve una lista con 2 marcas
        when(marcaRepository.findAll()).thenReturn(List.of(marca1, marca2));

        // WHEN: ejecuto el método listar del service
        List<MarcaDTO> resultado = marcaService.obtenerTodas();

        // THEN: verifico que la lista venga correcta
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 marcas");
        assertEquals("Nike", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Adidas", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        // Verifico que el repository se haya usado una sola vez
        verify(marcaRepository, times(1)).findAll();
    }

    @Test
    void testActualizarMarca_exitoso() {
        // GIVEN: preparo el ID de una marca que ya existe
        Integer idSimulado = 1;

        // Esta es la marca que supuestamente ya está guardada en la BD
        Marca marcaExistente = new Marca();
        marcaExistente.setId(idSimulado);
        marcaExistente.setNombre("Nike");

        // Estos son los datos nuevos que quiero actualizar
        Marca datosNuevos = new Marca();
        datosNuevos.setNombre("Nike Actualizada");

        // Simulo que el repository encuentra la marca por ID
        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaExistente));

        // Simulo que el repository guarda la marca ya modificada
        when(marcaRepository.save(marcaExistente)).thenReturn(marcaExistente);

        // WHEN: ejecuto el método actualizar del service
        Marca resultado = marcaService.actualizarMarca(idSimulado, datosNuevos);

        // THEN: verifico que los datos se hayan actualizado correctamente
        assertNotNull(resultado, "La marca actualizada no deberia ser nula");
        assertEquals("Nike Actualizada", resultado.getNombre(), "El nombre deberia actualizarse");

        // Verifico que primero buscó por ID y después guardó los cambios
        verify(marcaRepository, times(1)).findById(idSimulado);
        verify(marcaRepository, times(1)).save(marcaExistente);
    }

    @Test
    void testEliminarMarca_exitoso() {
        // GIVEN: preparo una marca que existe en la base de datos
        Integer idSimulado = 1;

        Marca marcaExistente = new Marca();
        marcaExistente.setId(idSimulado);
        marcaExistente.setNombre("Nike");

        // Simulo que el repository encuentra la marca por ID
        when(marcaRepository.findById(idSimulado)).thenReturn(Optional.of(marcaExistente));

        // WHEN: ejecuto el método eliminar del service
        String resultado = marcaService.eliminarMarca(idSimulado);

        // THEN: verifico que el service devuelva un mensaje
        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        // Verifico que buscó por ID y luego eliminó la marca encontrada
        verify(marcaRepository, times(1)).findById(idSimulado);
        verify(marcaRepository, times(1)).delete(marcaExistente);
    }
}