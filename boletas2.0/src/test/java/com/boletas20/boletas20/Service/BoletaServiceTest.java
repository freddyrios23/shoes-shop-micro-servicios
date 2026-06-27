package com.boletas20.boletas20.Service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.boletas20.boletas20.DTO.BoletaDTO;
import com.boletas20.boletas20.Model.Boleta;
import com.boletas20.boletas20.Repository.BoletaRepository;
import net.datafaker.Faker;


@ExtendWith(MockitoExtension.class)
public class BoletaServiceTest {
    @Mock
    private BoletaRepository boletaRepository;

    @InjectMocks
    private BoletaService boletaService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 1;

        Integer totalAleatorio = faker.number().numberBetween(10000, 99999);
        Integer cantidadAleatoria = faker.number().numberBetween(1, 10);

        Boleta boletaFalsa = new Boleta();
        boletaFalsa.setId(idSimulado);
        boletaFalsa.setFecha(LocalDate.now());
        boletaFalsa.setTotal(totalAleatorio);
        boletaFalsa.setCantidad(cantidadAleatoria);

        when(boletaRepository.findById(idSimulado)).thenReturn(Optional.of(boletaFalsa));

        BoletaDTO resultado = boletaService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(boletaFalsa.getTotal(), resultado.getTotal(), "El total transformado al DTO debe coincidir con el de la BD");
        assertEquals(boletaFalsa.getCantidad(), resultado.getCantidad(), "La cantidad transformada al DTO debe coincidir con el de la BD");

        verify(boletaRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarBoleta_exitoso() {
        Boleta boletaNueva = new Boleta();
        boletaNueva.setFecha(LocalDate.now());
        boletaNueva.setTotal(50000);
        boletaNueva.setCantidad(2);

        Boleta boletaGuardada = new Boleta();
        boletaGuardada.setId(1);
        boletaGuardada.setFecha(LocalDate.now());
        boletaGuardada.setTotal(50000);
        boletaGuardada.setCantidad(2);

        when(boletaRepository.save(boletaNueva)).thenReturn(boletaGuardada);

        BoletaDTO resultado = boletaService.guardarBoleta(boletaNueva);

        assertNotNull(resultado, "El DTO guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals(50000, resultado.getTotal(), "El total debe coincidir");
        assertEquals(2, resultado.getCantidad(), "La cantidad debe coincidir");

        verify(boletaRepository, times(1)).save(boletaNueva);
    }

    @Test
    void testObtenerTodas_exitoso() {
        Boleta boleta1 = new Boleta();
        boleta1.setId(1);
        boleta1.setFecha(LocalDate.now());
        boleta1.setTotal(50000);
        boleta1.setCantidad(2);

        Boleta boleta2 = new Boleta();
        boleta2.setId(2);
        boleta2.setFecha(LocalDate.now());
        boleta2.setTotal(80000);
        boleta2.setCantidad(3);

        when(boletaRepository.findAll()).thenReturn(List.of(boleta1, boleta2));

        List<BoletaDTO> resultado = boletaService.obtenerTodas();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 boletas");
        assertEquals(50000, resultado.get(0).getTotal(), "El primer total debe coincidir");
        assertEquals(80000, resultado.get(1).getTotal(), "El segundo total debe coincidir");

        verify(boletaRepository, times(1)).findAll();
    }

    @Test
    void testActualizarBoleta_exitoso() {
        Integer idSimulado = 1;

        Boleta boletaExistente = new Boleta();
        boletaExistente.setId(idSimulado);
        boletaExistente.setFecha(LocalDate.now());
        boletaExistente.setTotal(50000);
        boletaExistente.setCantidad(2);

        Boleta datosNuevos = new Boleta();
        datosNuevos.setTotal(99000);
        datosNuevos.setCantidad(4);

        when(boletaRepository.findById(idSimulado)).thenReturn(Optional.of(boletaExistente));
        when(boletaRepository.save(boletaExistente)).thenReturn(boletaExistente);

        Boleta resultado = boletaService.actualizarBoleta(idSimulado, datosNuevos);

        assertNotNull(resultado, "La boleta actualizada no deberia ser nula");
        assertEquals(99000, resultado.getTotal(), "El total deberia actualizarse");
        assertEquals(4, resultado.getCantidad(), "La cantidad deberia actualizarse");

        verify(boletaRepository, times(1)).findById(idSimulado);
        verify(boletaRepository, times(1)).save(boletaExistente);
    }

    @Test
    void testEliminarBoleta_exitoso() {
        Integer idSimulado = 1;

        Boleta boletaExistente = new Boleta();
        boletaExistente.setId(idSimulado);
        boletaExistente.setFecha(LocalDate.now());
        boletaExistente.setTotal(50000);
        boletaExistente.setCantidad(2);

        when(boletaRepository.findById(idSimulado)).thenReturn(Optional.of(boletaExistente));

        String resultado = boletaService.eliminarBoleta(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(boletaRepository, times(1)).findById(idSimulado);
        verify(boletaRepository, times(1)).delete(boletaExistente);
    }

}
