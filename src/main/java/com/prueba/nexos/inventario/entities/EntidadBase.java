package com.prueba.nexos.inventario.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntidadBase {

  public abstract int getId();

  @Column(name = "modificado_en")
  @LastModifiedDate
  private LocalDateTime modificadoEn;

  @Column(name = "modificado_por")
  private String modificadoPor;

  public boolean esElMismoId(EntidadBase o) {
    return (o == this || (o.getId() == this.getId()));
  }
}
