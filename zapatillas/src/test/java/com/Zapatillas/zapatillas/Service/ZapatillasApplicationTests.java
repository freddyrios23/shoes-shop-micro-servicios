package com.Zapatillas.zapatillas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class ZapatillasApplicationTests{

    @Mock
    private ZapatillaRepository zapatillaRepository;

    @InjectMocks
    private ZapatillaService zapatillaService;
    private Faker faker = new Faker();
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso(){
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
        zapatillaFalsa.setNombre(nombreAleatorio);
        zapatillaFalsa.setPrecio(precioAleatorio);

        when(zapatillaRepository.findById(idSimulado)).thenReturn(Optional.of(zapatillaFalsa));

        ZapatillaDTO resultado = zapatillaService.buscarPorId(idSimulado);

        assertNotNull(resultado,"El DTO resultante no deberia ser nulo");
        assertEquals(zapatillaFalsa.getNombre(), resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la BD");
        verify(zapatillaRepository,times(1)).findById(idSimulado);

    }
}