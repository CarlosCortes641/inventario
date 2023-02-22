package com.prueba.nexos.inventario.controller;


import com.prueba.nexos.inventario.dto.MercanciaDto;
import com.prueba.nexos.inventario.dto.RespuestaPaginada;
import com.prueba.nexos.inventario.dto.UsuarioDto;
import com.prueba.nexos.inventario.entities.Mercancia;
import com.prueba.nexos.inventario.entities.Usuario;
import com.prueba.nexos.inventario.service.UsuarioService;
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
@RequestMapping(value = "/api/v1/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioControllerRest {

  private UsuarioService usuarioService;
  private ModelMapper modelMapper;

  @Autowired
  public UsuarioControllerRest(UsuarioService usuarioService, ModelMapper modelMapper) {
    this.usuarioService = usuarioService;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public RespuestaPaginada<UsuarioDto> listar(@RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    var pageUsuario = this.usuarioService.listar(pageNo, pageSize, sortBy);

    var paginada = new RespuestaPaginada<UsuarioDto>();
    paginada.setTotalElemetos(pageUsuario.getTotalElements());
    paginada.setTotalPaginas(pageUsuario.getTotalPages());
    paginada.setCantidadElementosPagina(pageUsuario.getSize());
    paginada.setPrimerPagina(pageUsuario.isFirst());
    paginada.setUltimaPagina(pageUsuario.isLast());

    var usuarioDtos = pageUsuario.getContent()
        .stream()
        .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
        .collect(Collectors.toList());

    paginada.setContenido(usuarioDtos);
    return paginada;

  }

  @PostMapping
  public ResponseEntity<UsuarioDto> crear(@Valid @RequestBody UsuarioDto usuarioDto) {
    var usuario = this.modelMapper.map(usuarioDto, Usuario.class);
    usuario = this.usuarioService.crear(usuario);
    return new ResponseEntity<>(modelMapper.map(usuario, UsuarioDto.class), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UsuarioDto> actualizar(@Valid @RequestBody UsuarioDto usuarioDto,
      @PathVariable("id") int id) {
    if (usuarioDto == null || usuarioDto.getId() != id) {
      throw new ObjetoNoExisteException("El id del usuario a actualizar no corresponde al path");
    }
    var usuario = this.modelMapper.map(usuarioDto, Usuario.class);
    usuario = this.usuarioService.actualizar(usuario);
    return new ResponseEntity<>(modelMapper.map(usuario, UsuarioDto.class), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity eliminar(@PathVariable("id") int id) {
    usuarioService.borrar(id);
    return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
  }
}
