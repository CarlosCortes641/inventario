package com.prueba.nexos.inventario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioDto {

  protected Integer id;

  @NotNull(message = "El nombre no puede ser vacío")
  private String nombre;

  @NotNull(message = "La edad  no puede ser vacío")
  private Integer edad;

  @NotNull(message = "Fecha de registro no puede ser null")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDate fechaIngreso;

  @NotNull(message = "El cargo del usuario es requerido")
  private CargoDto cargo;
}
