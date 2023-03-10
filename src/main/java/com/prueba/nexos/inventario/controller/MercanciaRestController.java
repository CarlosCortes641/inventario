package com.prueba.nexos.inventario.controller;

import com.prueba.nexos.inventario.dto.MercanciaDto;
import com.prueba.nexos.inventario.dto.RespuestaPaginada;
import com.prueba.nexos.inventario.entities.Mercancia;
import com.prueba.nexos.inventario.service.MercanciaService;
import com.prueba.nexos.inventario.service.exceptions.ObjetoNoExisteException;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/mercancia", produces = MediaType.APPLICATION_JSON_VALUE)
public class MercanciaRestController {
  private MercanciaService mercanciaService;
  private ModelMapper modelMapper;

  @Autowired
  public MercanciaRestController(
      MercanciaService mercanciaService, ModelMapper modelMapper) {
    this.mercanciaService = mercanciaService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public RespuestaPaginada<MercanciaDto> listar(@RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    var paginaMercancia = this.mercanciaService.listar(pageNo, pageSize, sortBy);

    var paginada = new RespuestaPaginada<MercanciaDto>();
    paginada.setTotalElemetos(paginaMercancia.getTotalElements());
    paginada.setTotalPaginas(paginaMercancia.getTotalPages());
    paginada.setCantidadElementosPagina(paginaMercancia.getSize());
    paginada.setPrimerPagina(paginaMercancia.isFirst());
    paginada.setUltimaPagina(paginaMercancia.isLast());

    var mercanciaDtos = paginaMercancia.getContent()
        .stream()
        .map(mercancia -> modelMapper.map(mercancia, MercanciaDto.class))
        .collect(Collectors.toList());

    paginada.setContenido(mercanciaDtos);
    return paginada;

  }

  @PostMapping
  public ResponseEntity<MercanciaDto> crear(@Valid @RequestBody MercanciaDto mercanciaDto) {
    var mercancia = this.modelMapper.map(mercanciaDto, Mercancia.class);
    mercancia = this.mercanciaService.crear(mercancia);
    return new ResponseEntity<>(modelMapper.map(mercancia, MercanciaDto.class), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MercanciaDto> actualizar(@Valid @RequestBody MercanciaDto mercanciaDto,
      @PathVariable("id") int id) {
    if (mercanciaDto == null || mercanciaDto.getId() != id) {
      throw new ObjetoNoExisteException("El id de la mercancia  a actualizar no corresponde al path");
    }

    var mercancia = this.modelMapper.map(mercanciaDto, Mercancia.class);
    mercancia = this.mercanciaService.actualizar(mercancia);
    return new ResponseEntity<>(modelMapper.map(mercancia, MercanciaDto.class), HttpStatus.OK);
  }

  @DeleteMapping("/{id}/{idUsuario}")
  public ResponseEntity eliminar(@PathVariable("id") int id,@PathVariable("idUsuario") int idUsuario) {
    mercanciaService.borrar(id,idUsuario);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
