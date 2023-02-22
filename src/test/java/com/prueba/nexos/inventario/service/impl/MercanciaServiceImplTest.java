package com.prueba.nexos.inventario.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prueba.nexos.inventario.entities.Mercancia;
import com.prueba.nexos.inventario.entities.Usuario;
import com.prueba.nexos.inventario.repository.MercanciaRepository;
import com.prueba.nexos.inventario.service.exceptions.ObjetoExistenteException;
import com.prueba.nexos.inventario.service.exceptions.ObjetoNoExisteException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(MockitoExtension.class)
class MercanciaServiceImplTest {

  @Mock
  private static MercanciaRepository mercanciaRepository;

  @InjectMocks
  private static MercanciaServiceImpl mercanciaService;

  @Mock
  private static LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

  final static String NOMBRE_NO_EXISTENTE = "No existe nombre";
  final static String NOMBRE_YA_EXISTENTE = "Ya existe nombre";
  private static Integer CANTIDAD = 18;
  private final static LocalDate FECHA_HORA_ACTUAL = LocalDate.now();

  @Test
  @DisplayName("Cuando crea un nueva mercancia retornar el elemento")
  void test_crear_tipo_vehiculo() {
    when(mercanciaRepository.findByNombre(NOMBRE_NO_EXISTENTE))
        .thenReturn(Optional.ofNullable(null));
    var mercancia = this.construirMercancia(0, NOMBRE_NO_EXISTENTE);

    mercanciaService.crear(mercancia);

    verify(mercanciaRepository, times(1))
        .findByNombre(mercancia.getNombre());
    verify(mercanciaRepository, times(1))
        .save(mercancia);
  }

  @Test
  @DisplayName("Cuando crea mercancia  y nombre ya existe mostrar mensaje")
  void test_nombre_ya_existe() {
    var mercancia = this.construirMercancia(0, NOMBRE_YA_EXISTENTE);
    this.validarMercanciaExistente(NOMBRE_YA_EXISTENTE);

    assertThrows(ObjetoExistenteException.class,()->mercanciaService.crear(mercancia));

    verify(mercanciaRepository,times(1)).findByNombre(mercancia.getNombre());
    verify(mercanciaRepository,times(0)).save(mercancia);
  }

  @Test
  @DisplayName("Cuando actualiza y mercancia no existe debe mostrar mensaje")
  void cuando_actualiza_y_mercancia_no_existe_deberia_lanzar_exception() {
    var mercancia = this.construirMercancia(10, NOMBRE_NO_EXISTENTE);
    when(mercanciaRepository.findById(mercancia.getId())).thenReturn(Optional.ofNullable(null));

    assertThrows(RuntimeException.class, ()-> mercanciaService.actualizar(mercancia));

    verify(mercanciaRepository, times(1))
        .findById(mercancia.getId());
    verify(mercanciaRepository, times(0))
        .save(mercancia);
  }

  @Test
  @DisplayName("Cuando actualiza el nombre de la mercancia y este ya existe mostrar mensaje")
  void cuando_actualiza_y_nombre_existe_deberia_lanzar_exception() {

    var mercancia = this.construirMercancia(0, NOMBRE_YA_EXISTENTE);
    this.validarMercanciaExistente(NOMBRE_YA_EXISTENTE);

    assertThrows(ObjetoExistenteException.class,()->mercanciaService.actualizar(mercancia));

    verify(mercanciaRepository,times(1)).findByNombre(mercancia.getNombre());
    verify(mercanciaRepository,times(0)).save(mercancia);

  }

  @Test
  @DisplayName("Cuando borra mercancia y esta existe")
  void cuando_borrar_mercancia_existe() {
    var mercancia = this.construirMercancia(20, NOMBRE_YA_EXISTENTE);
    Optional<Mercancia> mercanciaExistente = Optional.of(mercancia);

    when(mercanciaRepository.findById(20)).thenReturn(mercanciaExistente);

    mercanciaService.borrar(20,1);
    verify(mercanciaRepository, times(1)).deleteById(20);
  }

  @Test
  @DisplayName("Cuando borra mercancia y no existe")
  void cuando_borrar_mercancia_no_existe() {
    var mercancia = this.construirMercancia(10, NOMBRE_NO_EXISTENTE);
    Optional<Mercancia> mercanciaNoExistente = Optional.ofNullable(null);

    when(mercanciaRepository.findById(10)).thenReturn(mercanciaNoExistente);

    assertThrows(ObjetoNoExisteException.class, () -> mercanciaService.borrar(10,1));

    verify(mercanciaRepository, times(0)).deleteById(10);
    verify(mercanciaRepository, times(1)).findById(10);
  }

  @Test
  @DisplayName("Cuando borra mercancia y usuario no es el propietario mostrar mensaje")
  void cuando_borrar_mercancia_y_usuario_no_propietario() {
    var mercancia = this.construirMercancia(10, NOMBRE_YA_EXISTENTE);
    Optional<Mercancia> mercanciaNoExistente = Optional.ofNullable(null);

    when(mercanciaRepository.findById(10)).thenReturn(mercanciaNoExistente);

    assertThrows(ObjetoNoExisteException.class, () -> mercanciaService.borrar(10,3));

    verify(mercanciaRepository, times(0)).deleteById(10);
    verify(mercanciaRepository, times(1)).findById(10);
  }

  @ParameterizedTest(name = "Buscar mercancia por id {0}")
  @ValueSource(ints = {5, 8, 9, 10, 50, 500, 1000})
  @DisplayName("Buscar por id y mercancia no exite arrojar mensaje")
  void test_buscar_por_id_y_marca_no_existe(int id) {
    Optional<Mercancia> mercanciaPorIdNoExiste = Optional.empty();
    when(mercanciaRepository.findById(id)).thenReturn(mercanciaPorIdNoExiste);

    assertThrows(ObjetoNoExisteException.class, () -> mercanciaService.buscarPorId(id));

    verify(mercanciaRepository, times(1)).findById(id);
  }

  private Mercancia construirMercancia(int id, String nombre) {
    var mercancia = new Mercancia();
    mercancia.setId(id);
    mercancia.setNombre(nombre);
    mercancia.setCantidad(CANTIDAD);
    mercancia.setFechaRegistro(FECHA_HORA_ACTUAL);
    mercancia.setUsuario(this.crearUsuario());

    return mercancia;
  }

  private Usuario crearUsuario(){
    var usuario = new Usuario();
    usuario.setId(1);
    usuario.setNombre("Usuario de prueba");
    usuario.setEdad(19);
    usuario.setFechaIngreso(FECHA_HORA_ACTUAL);
    return usuario;
  }

  private void validarMercanciaExistente(String nombre) {
    var mercanciaExistenteConMismoNombre = this.construirMercancia(1,nombre);
    when(mercanciaRepository.findByNombre(NOMBRE_YA_EXISTENTE))
        .thenReturn(Optional.of(mercanciaExistenteConMismoNombre));
  }
}