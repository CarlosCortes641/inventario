package com.prueba.nexos.inventario.repository;

import com.prueba.nexos.inventario.entities.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {

}
