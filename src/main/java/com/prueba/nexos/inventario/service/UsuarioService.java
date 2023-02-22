package com.prueba.nexos.inventario.service;

import com.prueba.nexos.inventario.entities.Usuario;
import org.springframework.data.domain.Page;

public interface UsuarioService {

  Usuario crear(Usuario usuario);

  Usuario actualizar(Usuario usuario);

  Usuario buscarPorId(int id);

  Page<Usuario> listar(Integer pageNo, Integer pageSize,String sortBy);

  void borrar(int id);
}
