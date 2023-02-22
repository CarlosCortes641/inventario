package com.prueba.nexos.inventario.service.impl;

import com.prueba.nexos.inventario.entities.Mercancia;
import com.prueba.nexos.inventario.repository.MercanciaRepository;
import com.prueba.nexos.inventario.service.MercanciaService;
import com.prueba.nexos.inventario.service.exceptions.NoEsPropietarioException;
import com.prueba.nexos.inventario.service.exceptions.ObjetoExistenteException;
import com.prueba.nexos.inventario.service.exceptions.ObjetoNoExisteException;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MercanciaServiceImpl implements MercanciaService {

  private Validator validator;
  private MercanciaRepository mercanciaRepository;

  @Autowired
  public MercanciaServiceImpl(Validator validator,
      MercanciaRepository mercanciaRepository) {
    this.validator = validator;
    this.mercanciaRepository = mercanciaRepository;
  }

  @Override
  public Mercancia crear(Mercancia mercancia) {
    this.validar(mercancia);
    this.mercanciaRepository.save(mercancia);
    return mercancia;
  }

  @Override
  public Mercancia actualizar(Mercancia mercancia) {
    this.validar(mercancia);
    this.buscarPorId(mercancia.getId());
    this.mercanciaRepository.save(mercancia);
    return mercancia;
  }

  @Override
  public Mercancia buscarPorId(int id) {
    return this.mercanciaRepository.findById(id)
        .orElseThrow(() -> new ObjetoNoExisteException("No existe Mercancia con el ID: " + id));
  }

  @Override
  public Mercancia buscarPorNombre(String nombre) {
    return this.mercanciaRepository.findByNombre(nombre)
        .orElseThrow(
            () -> new ObjetoNoExisteException("No existe mercancia con el nombre: " + nombre));
  }

  @Override
  public Page<Mercancia> listar(Integer pageNo, Integer pageSize, String sortBy) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Mercancia> pagedResult = mercanciaRepository.findAll(pageable);
    return pagedResult;
  }

  @Override
  public void borrar(int id, int idUsuario) {
    var mercancia = this.buscarPorId(id);
    this.validarPropietario(mercancia,idUsuario);
    this.mercanciaRepository.deleteById(id);
  }

  private void validar(Mercancia mercancia) {
    Set<ConstraintViolation<Mercancia>> violations = validator.validate(mercancia);
    if (!violations.isEmpty()) {
      var sb = new StringBuilder();
      for (ConstraintViolation<Mercancia> constraintViolation : violations) {
        sb.append(constraintViolation.getMessage());
      }
      throw new ConstraintViolationException("Error occurred: " + sb, violations);
    }
    this.validarExistePorNombre(mercancia);
  }

  private void validarExistePorNombre(Mercancia mercancia) {
    Optional<Mercancia> mercanciaExistente = this.mercanciaRepository
        .findByNombre(mercancia.getNombre());
    if (mercanciaExistente.isPresent() && !mercanciaExistente.get().esElMismoId(mercancia)) {
      throw new ObjetoExistenteException("Ya existe la marca con nombre " + mercancia.getNombre());
    }
  }

  private void validarPropietario(Mercancia mercancia, int idUsuario) {
    if (mercancia.getUsuario().getId() != idUsuario){
      throw new NoEsPropietarioException(
          "Usted no es propietario de esta mercancia: " + mercancia.getNombre());
    }
  }
}
