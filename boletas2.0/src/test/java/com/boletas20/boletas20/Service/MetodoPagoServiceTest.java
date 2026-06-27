package com.boletas20.boletas20.Service;

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

import com.boletas20.boletas20.DTO.MetodoPagoDTO;
import com.boletas20.boletas20.Model.MetodoPago;
import com.boletas20.boletas20.Repository.MetodoPagoRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class MetodoPagoServiceTest {

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @InjectMocks
    private MetodoPagoService metodoPagoService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 1;

        String tipoAleatorio = faker.options().option(
                "Tarjeta de credito",
                "Tarjeta de debito",
                "Transferencia",
                "Efectivo"
        );

        MetodoPago metodoPagoFalso = new MetodoPago();
        metodoPagoFalso.setId(idSimulado);
        metodoPagoFalso.setTipo(tipoAleatorio);

        when(metodoPagoRepository.findById(idSimulado)).thenReturn(Optional.of(metodoPagoFalso));

        MetodoPagoDTO resultado = metodoPagoService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(metodoPagoFalso.getTipo(), resultado.getTipo(), "El tipo transformado al DTO debe coincidir con el de la BD");

        verify(metodoPagoRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarMetodoPago_exitoso() {
        MetodoPago metodoPagoNuevo = new MetodoPago();
        metodoPagoNuevo.setTipo("Tarjeta de credito");

        MetodoPago metodoPagoGuardado = new MetodoPago();
        metodoPagoGuardado.setId(1);
        metodoPagoGuardado.setTipo("Tarjeta de credito");

        when(metodoPagoRepository.save(metodoPagoNuevo)).thenReturn(metodoPagoGuardado);

        MetodoPagoDTO resultado = metodoPagoService.guardarMetodoPago(metodoPagoNuevo);

        assertNotNull(resultado, "El metodo de pago guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Tarjeta de credito", resultado.getTipo(), "El tipo debe coincidir");

        verify(metodoPagoRepository, times(1)).save(metodoPagoNuevo);
    }

    @Test
    void testObtenerTodos_exitoso() {
        MetodoPago metodoPago1 = new MetodoPago();
        metodoPago1.setId(1);
        metodoPago1.setTipo("Tarjeta de credito");

        MetodoPago metodoPago2 = new MetodoPago();
        metodoPago2.setId(2);
        metodoPago2.setTipo("Transferencia");

        when(metodoPagoRepository.findAll()).thenReturn(List.of(metodoPago1, metodoPago2));

        List<MetodoPagoDTO> resultado = metodoPagoService.obtenerTodos();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 metodos de pago");
        assertEquals("Tarjeta de credito", resultado.get(0).getTipo(), "El primer tipo debe coincidir");
        assertEquals("Transferencia", resultado.get(1).getTipo(), "El segundo tipo debe coincidir");

        verify(metodoPagoRepository, times(1)).findAll();
    }

    @Test
    void testActualizarMetodoPago_exitoso() {
        Integer idSimulado = 1;

        MetodoPago metodoPagoExistente = new MetodoPago();
        metodoPagoExistente.setId(idSimulado);
        metodoPagoExistente.setTipo("Efectivo");

        MetodoPago datosNuevos = new MetodoPago();
        datosNuevos.setTipo("Tarjeta de debito");

        when(metodoPagoRepository.findById(idSimulado)).thenReturn(Optional.of(metodoPagoExistente));
        when(metodoPagoRepository.save(metodoPagoExistente)).thenReturn(metodoPagoExistente);

        MetodoPagoDTO resultado = metodoPagoService.actualizarMetodoPago(idSimulado, datosNuevos);

        assertNotNull(resultado, "El metodo de pago actualizado no deberia ser nulo");
        assertEquals("Tarjeta de debito", resultado.getTipo(), "El tipo deberia actualizarse");

        verify(metodoPagoRepository, times(1)).findById(idSimulado);
        verify(metodoPagoRepository, times(1)).save(metodoPagoExistente);
    }

    @Test
    void testEliminarMetodoPago_exitoso() {
        Integer idSimulado = 1;

        MetodoPago metodoPagoExistente = new MetodoPago();
        metodoPagoExistente.setId(idSimulado);
        metodoPagoExistente.setTipo("Efectivo");

        when(metodoPagoRepository.findById(idSimulado)).thenReturn(Optional.of(metodoPagoExistente));

        String resultado = metodoPagoService.eliminarMetodoPAgo(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(metodoPagoRepository, times(1)).findById(idSimulado);
        verify(metodoPagoRepository, times(1)).delete(metodoPagoExistente);
    }
}
