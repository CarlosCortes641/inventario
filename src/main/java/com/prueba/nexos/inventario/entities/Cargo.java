package com.prueba.nexos.inventario.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cargos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cargo  extends EntidadBase{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "car_id")
  private int id;

  @Column(name = "cargo_nombre")
  @NotBlank(message = "El nombre no puede ser vacío")
  @Size(min = 1, max = 255, message = "El nombre debe tener mínimo un carácter y máximo 255")
  private String nombre;

}
