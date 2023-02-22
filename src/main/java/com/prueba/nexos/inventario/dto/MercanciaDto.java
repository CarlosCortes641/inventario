package com.prueba.nexos.inventario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MercanciaDto {
  protected Integer id;

  private String nombre;

  @NotNull(message = "La cantidad para la mercancia es requerido")
  private Integer cantidad;


  @NotNull(message = "Fecha de registro no puede ser null")
  @PastOrPresent(message = "Fecha de registro debe ser posterior o igual a la fecha actual")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDate fechaRegistro;

  @NotNull(message = "El usuario para la mercancia es requerido")
  private UsuarioDto usuario;
}
