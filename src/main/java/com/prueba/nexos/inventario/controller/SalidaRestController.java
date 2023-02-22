package com.prueba.nexos.inventario.controller;

import com.prueba.nexos.inventario.dto.IngresoDto;
import com.prueba.nexos.inventario.dto.SalidaDto;
import com.prueba.nexos.inventario.entities.Ingreso;
import com.prueba.nexos.inventario.entities.Salida;
import com.prueba.nexos.inventario.repository.SalidaRepository;
import com.prueba.nexos.inventario.service.SalidaService;
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
@RequestMapping(value = "/api/v1/salida", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalidaRestController {

  private SalidaService salidaService;
  private ModelMapper modelMapper;

  @Autowired
  public SalidaRestController(SalidaService salidaService, ModelMapper modelMapper) {
    this.salidaService = salidaService;
    this.modelMapper = modelMapper;
  }

  @PostMapping
  public ResponseEntity<SalidaDto> crear(@Valid @RequestBody SalidaDto salidaDto) {
    var salida = this.modelMapper.map(salidaDto, Salida.class);
    salida = this.salidaService.crear(salida);
    return new ResponseEntity<>(modelMapper.map(salida, SalidaDto.class), HttpStatus.CREATED);
  }
}
