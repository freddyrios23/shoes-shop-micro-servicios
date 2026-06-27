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

import com.Zapatillas.zapatillas.model.Material;
import com.Zapatillas.zapatillas.model.Materiales;
import com.Zapatillas.zapatillas.model.Zapatilla;
import com.Zapatillas.zapatillas.repository.MaterialesRepository;
import com.Zapatillas.zapatillas.service.MaterialesService;

@ExtendWith(MockitoExtension.class)
class MaterialesApplicationTests {

    @Mock
    private MaterialesRepository materialesRepository;

    @InjectMocks
    private MaterialesService materialesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos_exitoso() {
        Zapatilla zapatilla = new Zapatilla();
        zapatilla.setId(1);
        zapatilla.setNombre("Nike Air Max");

        Material material = new Material();
        material.setId(1);
        material.setNombre("Cuero");

        Materiales relacion = new Materiales();
        relacion.setZapatilla(zapatilla);
        relacion.setMaterial(material);

        when(materialesRepository.findAll()).thenReturn(List.of(relacion));

        List<Materiales> resultado = materialesService.obtenerTodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(1, resultado.size(), "La lista deberia tener 1 relacion");
        assertEquals("Nike Air Max", resultado.get(0).getZapatilla().getNombre(), "El nombre de la zapatilla debe coincidir");
        assertEquals("Cuero", resultado.get(0).getMaterial().getNombre(), "El nombre del material debe coincidir");

        verify(materialesRepository, times(1)).findAll();
    }

    @Test
    void testGuardarRelacion_exitoso() {
        Zapatilla zapatilla = new Zapatilla();
        zapatilla.setId(1);
        zapatilla.setNombre("Nike Air Max");

        Material material = new Material();
        material.setId(1);
        material.setNombre("Cuero");

        Materiales relacion = new Materiales();
        relacion.setZapatilla(zapatilla);
        relacion.setMaterial(material);

        when(materialesRepository.save(relacion)).thenReturn(relacion);

        String resultado = materialesService.guardarRelacion(relacion);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        assertEquals(
                "La zapatilla Nike Air Max fue fabricada con el material Cuero",
                resultado,
                "El mensaje debe coincidir con la relación guardada"
        );

        verify(materialesRepository, times(1)).save(relacion);
    }
}