package com.prueba.nexos.inventario.service.impl;


import com.prueba.nexos.inventario.entities.Cargo;
import com.prueba.nexos.inventario.entities.Usuario;
import com.prueba.nexos.inventario.repository.UsuarioRepository;
import com.prueba.nexos.inventario.service.UsuarioService;
import com.prueba.nexos.inventario.service.exceptions.ObjetoExistenteException;
import com.prueba.nexos.inventario.service.exceptions.ObjetoNoExisteException;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

  private Validator validator;
  private UsuarioRepository usuarioRepository;

  @Autowired
  public UsuarioServiceImpl(Validator validator, UsuarioRepository usuarioRepository) {
    this.validator = validator;
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public Usuario crear(@Valid Usuario usuario) {
    this.validar(usuario);
    this.usuarioRepository.save(usuario);
    return usuario;
  }

  @Override
  public Usuario actualizar(Usuario usuario) {
    this.validar(usuario);
    this.buscarPorId(usuario.getId());
    this.usuarioRepository.save(usuario);
    return usuario;
  }

  @Override
  public Usuario buscarPorId(int id) {
    return this.usuarioRepository.findById(id)
        .orElseThrow(() -> new ObjetoNoExisteException("No existe Usuario con el ID: " + id));
  }

  @Override
  public Page<Usuario> listar(Integer pageNo, Integer pageSize, String sortBy) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Usuario> pagedResult = usuarioRepository.findAll(pageable);
    return pagedResult;
  }

  @Override
  public void borrar(int id) {
    this.buscarPorId(id);
    this.usuarioRepository.deleteById(id);
  }

  private void validar(Usuario usuario) {
    Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
    if (!violations.isEmpty()) {
      var sb = new StringBuilder();
      for (ConstraintViolation<Usuario> constraintViolation : violations) {
        sb.append(constraintViolation.getMessage());
      }
      throw new ConstraintViolationException("Error occurred: " + sb, violations);
    }
    this.validarExistePorNombre(usuario);
  }
  private void validarExistePorNombre(Usuario usuario) {
    Optional<Usuario>usarioExistente = this.usuarioRepository.findByNombre(usuario.getNombre());
    if (usarioExistente.isPresent() && !usarioExistente.get().esElMismoId(usuario)) {
      throw new ObjetoExistenteException("Ya existe el usuario con nombre " + usuario.getNombre());
    }
  }
}
