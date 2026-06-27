package com.Zapatillas.zapatillas.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoletaExternaDTO {
    private Integer id;
    private LocalDate fecha;
    private Integer total;
    private Integer cantidad;
    private Integer zapatillasId;
}

