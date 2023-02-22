package com.prueba.nexos.inventario.repository;

import com.prueba.nexos.inventario.entities.Mercancia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercanciaRepository extends JpaRepository<Mercancia, Integer> {

  Optional<Mercancia> findByNombre(String nombre);
}
