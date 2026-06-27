package com.Zapatillas.zapatillas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.Zapatillas.zapatillas.model.Tipo;
import com.Zapatillas.zapatillas.model.Tipos;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.repository.TiposRepository;
import com.Zapatillas.zapatillas.service.TiposService;

@ExtendWith(MockitoExtension.class)
class TiposApplicationTests {

    @Mock
    private TiposRepository tiposRepository;

    @InjectMocks
    private TiposService tiposService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas_exitoso() {
        Zapatilla zapatilla = new Zapatilla();
        zapatilla.setId(1);
        zapatilla.setNombre("Nike Air Max");

        Tipo tipo = new Tipo();
        tipo.setId(1);
        tipo.setNombre("Running");

        Tipos relacion = new Tipos();
        relacion.setZapatilla(zapatilla);
        relacion.setTipo(tipo);

        when(tiposRepository.findAll()).thenReturn(List.of(relacion));

        List<Tipos> resultado = tiposService.obtenerTodas();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(1, resultado.size(), "La lista deberia tener 1 relacion");
        assertEquals("Nike Air Max", resultado.get(0).getZapatilla().getNombre(), "El nombre de la zapatilla debe coincidir");
        assertEquals("Running", resultado.get(0).getTipo().getNombre(), "El nombre del tipo debe coincidir");

        verify(tiposRepository, times(1)).findAll();
    }

    @Test
    void testGuardarRelacion_exitoso() {
        Zapatilla zapatilla = new Zapatilla();
        zapatilla.setId(1);
        zapatilla.setNombre("Nike Air Max");

        Tipo tipo = new Tipo();
        tipo.setId(1);
        tipo.setNombre("Running");

        Tipos relacion = new Tipos();
        relacion.setZapatilla(zapatilla);
        relacion.setTipo(tipo);

        when(tiposRepository.save(relacion)).thenReturn(relacion);

        String resultado = tiposService.guardarRelacion(relacion);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        assertEquals(
                "El tipo de zapatilla1El tipo de zapatilla fue agregado1",
                resultado,
                "El mensaje debe coincidir con la relación guardada"
        );


        verify(tiposRepository, times(1)).save(relacion);
    }
}