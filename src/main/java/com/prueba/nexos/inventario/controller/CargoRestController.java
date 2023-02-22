package com.prueba.nexos.inventario.controller;

import com.prueba.nexos.inventario.dto.CargoDto;
import com.prueba.nexos.inventario.dto.RespuestaPaginada;
import com.prueba.nexos.inventario.entities.Cargo;
import com.prueba.nexos.inventario.service.CargoService;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/cargo", produces = MediaType.APPLICATION_JSON_VALUE)
public class CargoRestController {

  private CargoService cargoService;
  private ModelMapper modelMapper;

  @Autowired
  public CargoRestController(CargoService cargoService, ModelMapper modelMapper) {
    this.cargoService = cargoService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public RespuestaPaginada<CargoDto> listar(@RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    var paginaCargo = this.cargoService.listar(pageNo, pageSize, sortBy);

    var paginada = new RespuestaPaginada<CargoDto>();
    paginada.setTotalElemetos(paginaCargo.getTotalElements());
    paginada.setTotalPaginas(paginaCargo.getTotalPages());
    paginada.setCantidadElementosPagina(paginaCargo.getSize());
    paginada.setPrimerPagina(paginaCargo.isFirst());
    paginada.setUltimaPagina(paginaCargo.isLast());

    var cargoDtos = paginaCargo.getContent()
        .stream()
        .map(cargo -> modelMapper.map(cargo, CargoDto.class))
        .collect(Collectors.toList());

    paginada.setContenido(cargoDtos);
    return paginada;

  }

  @PostMapping
  public ResponseEntity<CargoDto> crear(@Valid @RequestBody CargoDto cargoDto) {
    var cargo = this.modelMapper.map(cargoDto, Cargo.class);
    cargo = this.cargoService.crear(cargo);
    return new ResponseEntity<>(modelMapper.map(cargo, CargoDto.class), HttpStatus.CREATED);
  }
}
