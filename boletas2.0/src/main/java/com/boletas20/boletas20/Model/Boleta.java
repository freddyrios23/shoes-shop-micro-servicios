package com.boletas20.boletas20.Model;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "boletas")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    @Min(value = 1)
    private Integer total;

    @Column(nullable = false)
    @Min(value = 1,message = "debe llevar al menos 1 zapatilla")
    private Integer cantidad;

    @OneToMany(mappedBy = "boleta")
    private List<Boletas> boletas;

    @ManyToOne
    @JoinColumn(name = "metodoEnvio_id")
    private MetodoEnvio metodoEnvio;

    @ManyToOne
    @JoinColumn(name = "metodoPago_id")
    private MetodoPago metodoPago;
}
