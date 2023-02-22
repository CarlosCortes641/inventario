package com.prueba.nexos.inventario.service.impl;

import com.prueba.nexos.inventario.entities.Ingreso;
import com.prueba.nexos.inventario.repository.IngresoRepository;
import com.prueba.nexos.inventario.service.IngresoService;
import com.prueba.nexos.inventario.service.MercanciaService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class IngresoServiceImpl implements IngresoService {

  private IngresoRepository ingresoRepository;
  private MercanciaService mercanciaService;

  @Autowired
  public IngresoServiceImpl(IngresoRepository ingresoRepository,
      MercanciaService mercanciaService) {
    this.ingresoRepository = ingresoRepository;
    this.mercanciaService = mercanciaService;
  }

  @Override
  public Ingreso crear(Ingreso ingreso) {
    ingresoRepository.save(ingreso);
    this.sumarCantidad(ingreso);
    return ingreso;
  }

  private void sumarCantidad(Ingreso ingreso) {
    var nuevaCantidad = mercanciaService.buscarPorId(ingreso.getMercancia().getId());
    nuevaCantidad.setCantidad(nuevaCantidad.getCantidad() + ingreso.getCantidad());
    mercanciaService.actualizar(nuevaCantidad);
  }
}
