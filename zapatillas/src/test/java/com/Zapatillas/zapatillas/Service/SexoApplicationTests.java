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

import com.Zapatillas.zapatillas.DTO.SexoDTO;
import com.Zapatillas.zapatillas.model.Sexo;
import com.Zapatillas.zapatillas.repository.SexoRepository;
import com.Zapatillas.zapatillas.service.SexoService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class SexoApplicationTests {

    @Mock
    private SexoRepository sexoRepository;

    @InjectMocks
    private SexoService sexoService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 1;

        String generoAleatorio = faker.options().option(
                "Hombre",
                "Mujer",
                "Unisex"
        );

        Sexo sexoFalso = new Sexo();
        sexoFalso.setId(idSimulado);
        sexoFalso.setGenero(generoAleatorio);

        when(sexoRepository.findById(idSimulado)).thenReturn(Optional.of(sexoFalso));

        SexoDTO resultado = sexoService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(sexoFalso.getGenero(), resultado.getGenero(), "El nombre transformado al DTO debe coincidir con el de la BD");

        verify(sexoRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarSexo_exitoso() {
        Sexo sexoNuevo = new Sexo();
        sexoNuevo.setGenero("Unisex");

        Sexo sexoGuardado = new Sexo();
        sexoGuardado.setId(1);
        sexoGuardado.setGenero("Unisex");

        when(sexoRepository.save(sexoNuevo)).thenReturn(sexoGuardado);

        SexoDTO resultado = sexoService.guardarSexo(sexoNuevo);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Unisex", resultado.getGenero(), "El genero debe coincidir");

        verify(sexoRepository, times(1)).save(sexoNuevo);
    }

    @Test
    void testObtenerTodos_exitoso() {
        Sexo sexo1 = new Sexo();
        sexo1.setId(1);
        sexo1.setGenero("Hombre");

        Sexo sexo2 = new Sexo();
        sexo2.setId(2);
        sexo2.setGenero("Mujer");

        when(sexoRepository.findAll()).thenReturn(List.of(sexo1, sexo2));

        List<SexoDTO> resultado = sexoService.obtenerTodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 sexos");
        assertEquals("Hombre", resultado.get(0).getGenero(), "El primer genero debe coincidir");
        assertEquals("Mujer", resultado.get(1).getGenero(), "El segundo genero debe coincidir");

        verify(sexoRepository, times(1)).findAll();
    }

    @Test
    void testActualizarSexo_exitoso() {
        Integer idSimulado = 1;

        Sexo sexoExistente = new Sexo();
        sexoExistente.setId(idSimulado);
        sexoExistente.setGenero("Hombre");

        Sexo datosNuevos = new Sexo();
        datosNuevos.setGenero("Unisex");

        when(sexoRepository.findById(idSimulado)).thenReturn(Optional.of(sexoExistente));

        when(sexoRepository.save(sexoExistente)).thenReturn(sexoExistente);

        Sexo resultado = sexoService.actualizarSexo(idSimulado, datosNuevos);

        assertNotNull(resultado, "El sexo actualizado no deberia ser nulo");
        assertEquals("Unisex", resultado.getGenero(), "El genero deberia actualizarse");

        verify(sexoRepository, times(1)).findById(idSimulado);
        verify(sexoRepository, times(1)).save(sexoExistente);
    }

    @Test
    void testEliminarSexo_exitoso() {
        Integer idSimulado = 1;

        Sexo sexoExistente = new Sexo();
        sexoExistente.setId(idSimulado);
        sexoExistente.setGenero("Unisex");

        when(sexoRepository.findById(idSimulado)).thenReturn(Optional.of(sexoExistente));

        String resultado = sexoService.eliminarSexo(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(sexoRepository, times(1)).findById(idSimulado);
        verify(sexoRepository, times(1)).delete(sexoExistente);
    }
}