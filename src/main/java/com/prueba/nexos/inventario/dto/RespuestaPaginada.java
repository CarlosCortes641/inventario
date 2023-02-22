package com.prueba.nexos.inventario.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RespuestaPaginada<T>{

  private long totalElemetos;
  private int totalPaginas;
  private int cantidadElementosPagina;
  private boolean primerPagina;
  private boolean ultimaPagina;
  private List<T> contenido;

}
