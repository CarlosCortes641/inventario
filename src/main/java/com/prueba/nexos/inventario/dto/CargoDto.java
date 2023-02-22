package com.prueba.nexos.inventario.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CargoDto {
  protected Integer id;

  @NotNull(message = "El nombre no puede ser vacío")
  private String nombre;
}
