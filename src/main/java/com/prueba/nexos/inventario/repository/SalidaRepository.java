package com.prueba.nexos.inventario.repository;

import com.prueba.nexos.inventario.entities.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalidaRepository extends JpaRepository<Salida, Integer> {

}
