package com.prueba.nexos.inventario.service;

import com.prueba.nexos.inventario.entities.Cargo;
import org.springframework.data.domain.Page;

public interface CargoService {

  Cargo crear(Cargo cargo);

  Page<Cargo> listar(Integer pageNo, Integer pageSize, String sortBy);
}
