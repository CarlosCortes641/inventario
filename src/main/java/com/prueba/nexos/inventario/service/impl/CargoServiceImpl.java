package com.prueba.nexos.inventario.service.impl;

import com.prueba.nexos.inventario.entities.Cargo;
import com.prueba.nexos.inventario.repository.CargoRepository;
import com.prueba.nexos.inventario.service.CargoService;
import com.prueba.nexos.inventario.service.exceptions.ObjetoExistenteException;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
public class CargoServiceImpl implements CargoService {


  private Validator validator;
  private CargoRepository cargoRepository;

  @Autowired
  public CargoServiceImpl(Validator validator,
      CargoRepository cargoRepository) {
    this.validator = validator;
    this.cargoRepository = cargoRepository;
  }

  @Override
  public Cargo crear(Cargo cargo) {
    this.validar(cargo);
    this.cargoRepository.save(cargo);
    return cargo;
  }

  @Override
  public Page<Cargo> listar(Integer pageNo, Integer pageSize, String sortBy) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Cargo> pagedResult = cargoRepository.findAll(pageable);
    return pagedResult;
  }

  private void validar(Cargo cargo) {
    Set<ConstraintViolation<Cargo>> violations = validator.validate(cargo);
    if (!violations.isEmpty()) {
      var sb = new StringBuilder();
      for (ConstraintViolation<Cargo> constraintViolation : violations) {
        sb.append(constraintViolation.getMessage());
      }
      throw new ConstraintViolationException("Error occurred: " + sb, violations);
    }
    this.validarExistePorNombre(cargo);
  }

  private void validarExistePorNombre(Cargo cargo) {
    Optional<Cargo> cargoExistente = this.cargoRepository.findByNombre(cargo.getNombre());
    if (cargoExistente.isPresent() && !cargoExistente.get().esElMismoId(cargo)) {
      throw new ObjetoExistenteException("Ya existe el cargo con nombre " + cargo.getNombre());
    }
  }
}
