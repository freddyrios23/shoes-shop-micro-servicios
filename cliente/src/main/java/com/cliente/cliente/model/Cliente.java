package com.cliente.cliente.model;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El rut es obligatorio")
    @Size(min=9,max=15,message = "El rut debe tener entre 9 y 15 caracteres")
    @Column(nullable = false,length = 15)
    private String rut;    

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min=1,max=150,message = "El nombre debe tener entre 1 y 150 caracteres")
    @Column(nullable = false,length = 150)
    private String nombre;

    @NotBlank(message = "El numero telefono es obligatorio")
    @Size(min=9,max=12,message = "El numero telefono debe tener entre 9 y 12 caracteres")
    @Column(nullable = false,length = 12)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;
}

