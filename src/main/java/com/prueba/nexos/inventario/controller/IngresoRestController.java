package com.prueba.nexos.inventario.controller;

import com.prueba.nexos.inventario.dto.CargoDto;
import com.prueba.nexos.inventario.dto.IngresoDto;
import com.prueba.nexos.inventario.entities.Cargo;
import com.prueba.nexos.inventario.entities.Ingreso;
import com.prueba.nexos.inventario.service.CargoService;
import com.prueba.nexos.inventario.service.IngresoService;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/ingreso", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngresoRestController {

  private IngresoService ingresoService;
  private ModelMapper modelMapper;

  @Autowired
  public IngresoRestController(IngresoService ingresoService, ModelMapper modelMapper) {
    this.ingresoService = ingresoService;
    this.modelMapper = modelMapper;
  }

  @PostMapping
  public ResponseEntity<IngresoDto> crear(@Valid @RequestBody IngresoDto ingresoDto) {
    var ingreso = this.modelMapper.map(ingresoDto, Ingreso.class);
    ingreso = this.ingresoService.crear(ingreso);
    return new ResponseEntity<>(modelMapper.map(ingreso, IngresoDto.class), HttpStatus.CREATED);
  }

}
