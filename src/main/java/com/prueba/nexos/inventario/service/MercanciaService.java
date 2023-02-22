package com.prueba.nexos.inventario.service;

import com.prueba.nexos.inventario.entities.Mercancia;
import org.springframework.data.domain.Page;

public interface MercanciaService {
  Mercancia crear(Mercancia mercancia);

  Mercancia actualizar(Mercancia mercancia);

  Mercancia buscarPorId(int id);
  Mercancia buscarPorNombre(String nombre);

  Page<Mercancia> listar(Integer pageNo, Integer pageSize, String sortBy);

  void borrar(int id, int idUsuario);
}
