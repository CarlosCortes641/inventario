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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "ingresos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ingreso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("default")
  @Column(name = "ing_id")
  private int id;

  @Column(name = "ing_fecha_ingreso")
  @NotNull(message = "La fecha de ingreso no puede ser null")
  @PastOrPresent(message = "Fecha de ingreso debe ser posterior o igual a la fecha actual")
  private LocalDate fechaIngreso;

  @Column(name = "ing_cantidad")
  @ColumnDefault("1")
  @NotNull(message = "La cantidad no puede ser vacío")
  @Min(value = 1, message = "La cantidad debe tener mínimo un carácter")
  private Integer cantidad;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ing_mer_id")
  @NotNull(message = "La mercancia del ingreso no puede ser vacío")
  private Mercancia mercancia;

}
