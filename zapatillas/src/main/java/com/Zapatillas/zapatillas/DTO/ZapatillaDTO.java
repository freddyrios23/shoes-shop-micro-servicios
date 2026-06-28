package com.Zapatillas.zapatillas.DTO;

import lombok.Data;

@Data
public class ZapatillaDTO {
    private Integer id;
    private String nombre;
    private Integer precio;
    private Integer marcaId;
    private String nombreMarca;
    private BoletaExternaDTO boleta;
}
