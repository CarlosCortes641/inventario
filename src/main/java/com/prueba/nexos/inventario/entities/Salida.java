package com.prueba.nexos.inventario.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "salidas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Salida {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnDefault("default")
  @Column(name = "sal_id")
  private int id;

  @Column(name = "sal_fecha_salida")
  @NotNull(message = "La fecha de salida no puede ser null")
  private LocalDate fechaIngreso;

  @Column(name = "sal_cantidad")
  @ColumnDefault("1")
  @NotNull(message = "La cantidad no puede ser vacío")
  @Min(value = 1, message = "La cantidad debe tener mínimo un carácter")
  private Integer cantidad;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sal_mer_id")
  @NotNull(message = "La mercancia de la salida no puede ser vacío")
  private Mercancia mercancia;


}
