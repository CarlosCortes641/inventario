package com.prueba.nexos.inventario.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "mercancias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mercancia extends EntidadBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("default")
  @Column(name = "mer_id")
  private int id;

  @Column(name = "mer_nombre")
  @NotNull(message = "El nombre no puede ser vacío")
  @Size(min = 1, max = 255, message = "El nombre debe tener mínimo un carácter y máximo 255")
  private String nombre;

  @Column(name = "mer_cantidad")
  @ColumnDefault("1")
  @NotNull(message = "La cantidad no puede ser vacío")
  @Min(value = 1, message = "La cantidad debe tener mínimo un carácter")
  private Integer cantidad;


  @Column(name = "mer_fecha_registro")
  @NotNull(message = "Fecha de registro no puede ser null")
  @PastOrPresent(message = "Fecha fin de registro debe ser posterior o igual a la fecha actual")
  private LocalDate fechaRegistro;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "mer_usu_id")
  @NotNull(message = "El usuario de la mercancia  no puede ser vacía")
  private Usuario usuario;


}
