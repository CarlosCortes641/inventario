package com.prueba.nexos.inventario.service.impl;

import com.prueba.nexos.inventario.entities.Salida;
import com.prueba.nexos.inventario.repository.SalidaRepository;
import com.prueba.nexos.inventario.service.MercanciaService;
import com.prueba.nexos.inventario.service.SalidaService;
import com.prueba.nexos.inventario.service.exceptions.ObjetoExistenteException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SalidaServiceImpl implements SalidaService {

  private SalidaRepository salidaRepository;
  private MercanciaService mercanciaService;

  @Autowired
  public SalidaServiceImpl(SalidaRepository salidaRepository, MercanciaService mercanciaService) {
    this.salidaRepository = salidaRepository;
    this.mercanciaService = mercanciaService;
  }

  @Override
  public Salida crear(Salida salida) {
    this.restarCantidad(salida);
    salidaRepository.save(salida);
    return salida;
  }

  private void restarCantidad(Salida salida) {
    var nuevaCantidad = mercanciaService.buscarPorId(salida.getMercancia().getId());
    if (salida.getCantidad() > nuevaCantidad.getCantidad()){
      throw new ObjetoExistenteException("Cantidad infuciente para realizar la salida del material ");
    }
    nuevaCantidad.setCantidad(nuevaCantidad.getCantidad() - salida.getCantidad());
    mercanciaService.actualizar(nuevaCantidad);

  }
}
