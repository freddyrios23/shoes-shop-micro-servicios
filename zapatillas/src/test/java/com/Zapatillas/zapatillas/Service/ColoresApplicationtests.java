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

import com.Zapatillas.zapatillas.model.Color;
import com.Zapatillas.zapatillas.model.Colores;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.repository.ColoresRepository;
import com.Zapatillas.zapatillas.service.ColoresService;

@ExtendWith(MockitoExtension.class)
class ColoresApplicationTests {

    @Mock
    private ColoresRepository coloresRepository;

    @InjectMocks
    private ColoresService coloresService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos_exitoso() {
        Zapatilla zapatilla = new Zapatilla();
        zapatilla.setId(1);
        zapatilla.setNombre("Nike Air Max");

        Color color = new Color();
        color.setId(1);
        color.setNombre("Negro");

        Colores relacion = new Colores();
        relacion.setZapatilla(zapatilla);
        relacion.setColor(color);

        when(coloresRepository.findAll()).thenReturn(List.of(relacion));

        List<Colores> resultado = coloresService.obtenerTodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(1, resultado.size(), "La lista deberia tener 1 relación");
        assertEquals("Nike Air Max", resultado.get(0).getZapatilla().getNombre(), "El nombre de la zapatilla debe coincidir");
        assertEquals("Negro", resultado.get(0).getColor().getNombre(), "El nombre del color debe coincidir");

        verify(coloresRepository, times(1)).findAll();
    }

    @Test
    void testGuardarRelacion_exitoso() {
        Zapatilla zapatilla = new Zapatilla();
        zapatilla.setId(1);
        zapatilla.setNombre("Nike Air Max");

        Color color = new Color();
        color.setId(1);
        color.setNombre("Negro");

        Colores relacion = new Colores();
        relacion.setZapatilla(zapatilla);
        relacion.setColor(color);

        when(coloresRepository.save(relacion)).thenReturn(relacion);

        String resultado = coloresService.guardarRelacion(relacion);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        assertEquals(
                "La zapatilla Nike Air Max fue asignada al color Negro",
                resultado,
                "El mensaje debe coincidir con la relación guardada"
        );

        verify(coloresRepository, times(1)).save(relacion);
    }
}