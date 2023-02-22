package com.prueba.nexos.inventario.repository;

import com.prueba.nexos.inventario.entities.Cargo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
  Optional<Cargo> findByNombre(String nombre);
}
