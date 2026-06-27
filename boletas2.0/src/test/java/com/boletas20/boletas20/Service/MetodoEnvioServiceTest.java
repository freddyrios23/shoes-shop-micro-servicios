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

import com.boletas20.boletas20.DTO.MetodoEnvioDTO;
import com.boletas20.boletas20.Model.MetodoEnvio;
import com.boletas20.boletas20.Repository.MetodoEnvioRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class MetodoEnvioServiceTest {

    @Mock
    private MetodoEnvioRepository metodoEnvioRepository;

    @InjectMocks
    private MetodoEnvioService metodoEnvioService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_exitoso() {
        Integer idSimulado = 1;

        String tipoAleatorio = faker.options().option(
                "Envio Express",
                "Envio Normal",
                "Retiro en tienda",
                "Envio Internacional"
        );

        MetodoEnvio metodoEnvioFalso = new MetodoEnvio();
        metodoEnvioFalso.setId(idSimulado);
        metodoEnvioFalso.setTipo(tipoAleatorio);
        metodoEnvioFalso.setCosto(5000);
        metodoEnvioFalso.setTiempoEntrega(3);

        when(metodoEnvioRepository.findById(idSimulado)).thenReturn(Optional.of(metodoEnvioFalso));

        MetodoEnvioDTO resultado = metodoEnvioService.buscarPorId(idSimulado);

        assertNotNull(resultado, "El DTO resultante no deberia estar nulo");
        assertEquals(metodoEnvioFalso.getTipo(), resultado.getTipo(), "El tipo transformado al DTO debe coincidir con el de la BD");

        verify(metodoEnvioRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testGuardarMetodoEnvio_exitoso() {
        MetodoEnvio metodoEnvioNuevo = new MetodoEnvio();
        metodoEnvioNuevo.setTipo("Envio Express");
        metodoEnvioNuevo.setCosto(5000);
        metodoEnvioNuevo.setTiempoEntrega(2);

        MetodoEnvio metodoEnvioGuardado = new MetodoEnvio();
        metodoEnvioGuardado.setId(1);
        metodoEnvioGuardado.setTipo("Envio Express");
        metodoEnvioGuardado.setCosto(5000);
        metodoEnvioGuardado.setTiempoEntrega(2);

        when(metodoEnvioRepository.save(metodoEnvioNuevo)).thenReturn(metodoEnvioGuardado);

        MetodoEnvioDTO resultado = metodoEnvioService.guardarMetodoEnvio(metodoEnvioNuevo);

        assertNotNull(resultado, "El metodo de envio guardado no deberia ser nulo");
        assertEquals(1, resultado.getId(), "El ID debe ser el que asignó la BD");
        assertEquals("Envio Express", resultado.getTipo(), "El tipo debe coincidir");

        verify(metodoEnvioRepository, times(1)).save(metodoEnvioNuevo);
    }

    @Test
    void testObtenerTodas_exitoso() {
        MetodoEnvio metodoEnvio1 = new MetodoEnvio();
        metodoEnvio1.setId(1);
        metodoEnvio1.setTipo("Envio Express");
        metodoEnvio1.setCosto(5000);
        metodoEnvio1.setTiempoEntrega(2);

        MetodoEnvio metodoEnvio2 = new MetodoEnvio();
        metodoEnvio2.setId(2);
        metodoEnvio2.setTipo("Envio Normal");
        metodoEnvio2.setCosto(2000);
        metodoEnvio2.setTiempoEntrega(7);

        when(metodoEnvioRepository.findAll()).thenReturn(List.of(metodoEnvio1, metodoEnvio2));

        List<MetodoEnvioDTO> resultado = metodoEnvioService.obtenerTodas();

        assertNotNull(resultado, "La lista no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista deberia tener 2 metodos de envio");
        assertEquals("Envio Express", resultado.get(0).getTipo(), "El primer tipo debe coincidir");
        assertEquals("Envio Normal", resultado.get(1).getTipo(), "El segundo tipo debe coincidir");

        verify(metodoEnvioRepository, times(1)).findAll();
    }

    @Test
    void testActualizarMetodoEnvio_exitoso() {
        Integer idSimulado = 1;

        MetodoEnvio metodoEnvioExistente = new MetodoEnvio();
        metodoEnvioExistente.setId(idSimulado);
        metodoEnvioExistente.setTipo("Envio Normal");
        metodoEnvioExistente.setCosto(2000);
        metodoEnvioExistente.setTiempoEntrega(7);

        MetodoEnvio datosNuevos = new MetodoEnvio();
        datosNuevos.setTipo("Envio Express");
        datosNuevos.setCosto(5000);
        datosNuevos.setTiempoEntrega(2);

        when(metodoEnvioRepository.findById(idSimulado)).thenReturn(Optional.of(metodoEnvioExistente));
        when(metodoEnvioRepository.save(metodoEnvioExistente)).thenReturn(metodoEnvioExistente);

        MetodoEnvioDTO resultado = metodoEnvioService.actualizarMetodoEnvio(idSimulado, datosNuevos);

        assertNotNull(resultado, "El metodo de envio actualizado no deberia ser nulo");
        assertEquals("Envio Express", resultado.getTipo(), "El tipo deberia actualizarse");
        assertEquals(5000, resultado.getCosto(), "El costo deberia actualizarse");

        verify(metodoEnvioRepository, times(1)).findById(idSimulado);
        verify(metodoEnvioRepository, times(1)).save(metodoEnvioExistente);
    }

    @Test
    void testEliminarMetodoEnvio_exitoso() {
        Integer idSimulado = 1;

        MetodoEnvio metodoEnvioExistente = new MetodoEnvio();
        metodoEnvioExistente.setId(idSimulado);
        metodoEnvioExistente.setTipo("Envio Express");
        metodoEnvioExistente.setCosto(5000);
        metodoEnvioExistente.setTiempoEntrega(2);

        when(metodoEnvioRepository.findById(idSimulado)).thenReturn(Optional.of(metodoEnvioExistente));

        String resultado = metodoEnvioService.eliminarMetodoEnvio(idSimulado);

        assertNotNull(resultado, "El mensaje no deberia ser nulo");

        verify(metodoEnvioRepository, times(1)).findById(idSimulado);
        verify(metodoEnvioRepository, times(1)).delete(metodoEnvioExistente);
    }

}
