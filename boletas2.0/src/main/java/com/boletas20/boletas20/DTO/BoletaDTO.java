package com.boletas20.boletas20.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
@Data
public class BoletaDTO {
    private Integer id;
    private LocalDate fecha;
    private Integer total;
    private Integer cantidad;
    private List<Integer> zapatillasId;
}
