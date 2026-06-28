package com.Zapatillas.zapatillas.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class BoletaExternaDTO {
    private Integer id;
    private LocalDate fecha;
    private Integer cantidad;
    private Integer total;
    private List<Integer> zapatillasId;
}