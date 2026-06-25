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

import com.Zapatillas.zapatillas.DTO.ZapatillaDTO;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.repository.ZapatillaRepository;
import com.Zapatillas.zapatillas.service.ZapatillaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ZapatillasApplicationTests {

    @Mock
    private ZapatillaRepository zapatillaRepository; 

    @InjectMocks
    private ZapatillaService zapatillaService; 

    private Faker faker = new Faker(); 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        // GIVEN preparo un ID falso y datos falsos para simular una zapatilla existente
        Integer idSimulado = 54;

        String nombreAleatorio = faker.options().option(
            "Nike Air Max",
            "Adidas Superstar",
            "Puma RS-X",
            "Nike Dunk Low",
            "Adidas Forum",
            "New Balance 550",
            "Converse Chuck Taylor"
        );

        Integer precioAleatorio = faker.number().numberBetween(30000, 90000);

        Zapatilla zapatillaFalsa = new Zapatilla();
        zapatillaFalsa.setId(idSimulado);
        zapatillaFalsa.setNombre(nombreAleatorio);
        zapatillaFalsa.setPrecio(precioAleatorio);

        // Simulo que cuando el repository busque por ese ID, encontrará la zapatilla falsa
        when(zapatillaRepository.findById(idSimulado)).thenReturn(Optional.of(zapatillaFalsa));

        // WHEN: ejecuto el método real del service
        ZapatillaDTO resultado = zapatillaService.buscarPorId(idSimulado);

        // THEN: verifico que el resultado no sea nulo y que los datos coincidan
        assertNotNull(resultado, "El DTO resultante no deberia ser nulo");
        assertEquals(zapatillaFalsa.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");

        // Verifico que el repository haya buscado por ID exactamente una vez
        verify(zapatillaRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarZapatilla_exitoso() {
        // GIVEN preparo una zapatilla nueva, como si viniera desde el controller
        Zapatilla zapatillaNueva = new Zapatilla();
        zapatillaNueva.setNombre("Nike Air Max");
        zapatillaNueva.setPrecio(89990);

        // Preparo la zapatilla guardada, simulando que la BD le asignó un ID
        Zapatilla zapatillaGuardada = new Zapatilla();
        zapatillaGuardada.setId(1);
        zapatillaGuardada.setNombre("Nike Air Max");
        zapatillaGuardada.setPrecio(89990);

        // Simulo que el repository guarda la zapatilla nueva y devuelve la zapatilla con ID
        when(zapatillaRepository.save(zapatillaNueva)).thenReturn(zapatillaGuardada);

        // WHEN: ejecuto el método guardar del service
        ZapatillaDTO resultado = zapatillaService.guardarZapatilla(zapatillaNueva);

        // THEN: verifico que el resultado venga correcto
        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Nike Air Max", resultado.getNombre(), "El nombre debe coincidir");

        // Verifico que el repository haya usado save una sola vez
        verify(zapatillaRepository, times(1)).save(zapatillaNueva);
    }

    @Test
    void testObtenerTodas_exitoso() {
        // GIVEN preparo zapatillas falsas como si vinieran desde la base de datos
        Zapatilla zapatilla1 = new Zapatilla();
        zapatilla1.setId(1);
        zapatilla1.setNombre("Nike Air Max");
        zapatilla1.setPrecio(89990);

        Zapatilla zapatilla2 = new Zapatilla();
        zapatilla2.setId(2);
        zapatilla2.setNombre("Adidas Superstar");
        zapatilla2.setPrecio(79990);

        // Simulo que el repository devuelve una lista con 2 zapatillas
        when(zapatillaRepository.findAll()).thenReturn(List.of(zapatilla1, zapatilla2));

        // WHEN: ejecuto el método listar del service
        List<ZapatillaDTO> resultado = zapatillaService.obtenerTodas();

        // THEN: verifico que la lista venga correcta
        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 zapatillas");
        assertEquals("Nike Air Max", resultado.get(0).getNombre(), "El primer nombre debe coincidir");
        assertEquals("Adidas Superstar", resultado.get(1).getNombre(), "El segundo nombre debe coincidir");

        // Verifico que el repository se haya usado una sola vez
        verify(zapatillaRepository, times(1)).findAll();
    }

    @Test
    void testActualizarZapatilla_exitoso() {
        // GIVEN preparo el ID de una zapatilla que ya existe
        Integer idSimulado = 1;

        // Esta es la zapatilla que supuestamente ya está guardada en la BD
        Zapatilla zapatillaExistente = new Zapatilla();
        zapatillaExistente.setId(idSimulado);
        zapatillaExistente.setNombre("Nike Air Max");
        zapatillaExistente.setPrecio(89990);

        // Estos son los datos nuevos que quiero actualizar
        Zapatilla datosNuevos = new Zapatilla();
        datosNuevos.setNombre("Nike Air Max Editada");
        datosNuevos.setPrecio(99990);

        // Simulo que el repository encuentra la zapatilla por ID
        when(zapatillaRepository.findById(idSimulado)).thenReturn(Optional.of(zapatillaExistente));

        // Simulo que el repository guarda la zapatilla ya modificada
        when(zapatillaRepository.save(zapatillaExistente)).thenReturn(zapatillaExistente);

        // WHEN: ejecuto el método actualizar del service
        Zapatilla resultado = zapatillaService.actualizarZapatilla(idSimulado, datosNuevos);

        // THEN: verifico que los datos se hayan actualizado correctamente
        assertNotNull(resultado, "La zapatilla actualizada no deberia ser nula");
        assertEquals("Nike Air Max Editada", resultado.getNombre(), "El nombre deberia actualizarse");
        assertEquals(99990, resultado.getPrecio(), "El precio deberia actualizarse");

        // Verifico que primero buscó por ID y después guardó los cambios
        verify(zapatillaRepository, times(1)).findById(idSimulado);
        verify(zapatillaRepository, times(1)).save(zapatillaExistente);
    }

    @Test
    void testEliminarZapatilla_exitoso() {
        // GIVEN preparo una zapatilla que existe en la base de datos
        Integer idSimulado = 1;

        Zapatilla zapatillaExistente = new Zapatilla();
        zapatillaExistente.setId(idSimulado);
        zapatillaExistente.setNombre("Nike Air Max");
        zapatillaExistente.setPrecio(89990);

        // Simulo que el repository encuentra la zapatilla por ID
        when(zapatillaRepository.findById(idSimulado)).thenReturn(Optional.of(zapatillaExistente));

        // WHEN: ejecuto el método eliminar del service
        String resultado = zapatillaService.eliminarZapatilla(idSimulado);

        // THEN: verifico que el service devuelva un mensaje
        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        // Verifico que buscó por ID y luego eliminó la zapatilla encontrada
        verify(zapatillaRepository, times(1)).findById(idSimulado);
        verify(zapatillaRepository, times(1)).delete(zapatillaExistente);
    }
}