package com.Zapatillas.zapatillas.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tipos_zapatillas")
public class Tipos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "zapatilla_id")
    private Zapatilla zapatilla;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;
}