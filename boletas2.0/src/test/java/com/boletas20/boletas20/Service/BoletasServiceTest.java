package com.boletas20.boletas20.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.boletas20.boletas20.Model.Boleta;
import com.boletas20.boletas20.Model.Boletas;
import com.boletas20.boletas20.Repository.BoletasRepository;

@ExtendWith(MockitoExtension.class)
public class BoletasServiceTest {
    
    @Mock
    private BoletasRepository boletasRepository;

    @InjectMocks
    private BoletasService boletasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas_exitoso() {
        Boleta boleta1 = new Boleta();
        boleta1.setId(1);
        boleta1.setTotal(50000);
        boleta1.setCantidad(2);

        Boletas relacion1 = new Boletas();
        relacion1.setId(1);
        relacion1.setBoleta(boleta1);

        Boleta boleta2 = new Boleta();
        boleta2.setId(2);
        boleta2.setTotal(80000);
        boleta2.setCantidad(3);

        Boletas relacion2 = new Boletas();
        relacion2.setId(2);
        relacion2.setBoleta(boleta2);

        when(boletasRepository.findAll()).thenReturn(List.of(relacion1, relacion2));

        List<Boletas> resultado = boletasService.obtenerTodas();

        assertNotNull(resultado, "La lista no deberia ser nula");

        verify(boletasRepository, times(1)).findAll();
    }

    @Test
    void testGuardarRelacion_exitoso() {
        Boleta boleta = new Boleta();
        boleta.setId(1);
        boleta.setTotal(50000);
        boleta.setCantidad(2);

        Boletas relacionNueva = new Boletas();
        relacionNueva.setBoleta(boleta);

        Boletas relacionGuardada = new Boletas();
        relacionGuardada.setId(1);
        relacionGuardada.setBoleta(boleta);

        when(boletasRepository.save(relacionNueva)).thenReturn(relacionGuardada);

        String resultado = boletasService.guardarRelacion(relacionNueva);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(boletasRepository, times(1)).save(relacionNueva);
    }
}
