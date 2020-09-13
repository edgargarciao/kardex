package com.todouno.kardex.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.todouno.kardex.dao.TipoProductoDao;
import com.todouno.kardex.dto.TipoProducto;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TipoProductoControllerTest {

  @Mock
  private Model model;
  
  @Mock
  private TipoProductoDao tipoProductoDao;

  @InjectMocks
  private TipoProductoController tipoProductoController;

  @Test
  public void givenTheSystemWithoutTipoProductoWhenCallIndexThenReturnTheModelNullAndTheJspName() {

    // Act
    String result = tipoProductoController.index(model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/TipoProductos");
    assertFalse(model.containsAttribute("tipoProductos"));
  }

  @Test
  public void givenAModelWhenCallsetUpUserFormThenReturnTheModelNotNull() {

    // Act
    TipoProducto result = tipoProductoController.setUpUserForm();

    // Assert
    assertNotNull(result);
  }

  @Test
  public void whenCallRegistrarTipoProductoThenReturnTheJspName() {
    // Act
    String result = tipoProductoController.registrarTipoProducto();

    // Assert
    assertEquals(result, "Administrador/TipoProducto/RegistrarTipoProducto");
  }

  @Test
  public void whenCallRegistrarTipoProductoEmptyThenReturnTheNameJsp() {
    
    // Arrange
    TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    
    // Act
    String result = tipoProductoController.registrarTipoProducto(tipoProducto, model);
    
    // Assert
    assertEquals("Administrador/TipoProducto/RegistrarTipoProducto",result);
    
  }

  
  @Test
  public void whenCallRegistrarFullTipoProductoThenReturnTheNameJsp() {
    
    // Arrange
    TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    Mockito.when(tipoProducto.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(tipoProductoDao.registrarTipoDeProducto(Mockito.any())).thenReturn("Registro exitoso");
    
    // Act
    String result = tipoProductoController.registrarTipoProducto(tipoProducto, model);
    
    // Assert
    assertEquals("Administrador/TipoProducto/TipoProductos",result);
    
  }
  
  @Test
  public void whenCallRegistrarIncorrectTipoProductoThenReturnTheNameJsp() {
    // Arrange
    TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    Mockito.when(tipoProducto.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(tipoProductoDao.registrarTipoDeProducto(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    
    // Act
    String result = tipoProductoController.registrarTipoProducto(tipoProducto, model);
    
    // Assert
    assertEquals("Administrador/TipoProducto/RegistrarTipoProducto",result);
    
  }
  
  @Test
  public void givenIdProductInvalidwhenCallActualizarTipoProductoThenReturnTheJspName() {
    // Act
    String result = tipoProductoController.actualizarTipoProducto(-1, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/TipoProductos");
  }
  
  @Test
  public void givenIdProductvalidwhenCallActualizarTipoProductoThenReturnTheJspName() {
    // Act
    String result = tipoProductoController.actualizarTipoProducto(10, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/ActualizarTipoProductos");
  }
  
  @Test
  public void givenProductvalidwhenCalleditarTipoProductoThenReturnTheJspName() {
	// Arrange
	TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    Mockito.when(tipoProducto.isValidoParaActualizar()).thenReturn(true);
    Mockito.when(tipoProductoDao.editarTipoProducto(Mockito.any())).thenReturn("Actualizacion exitosa");
	  
	// Act    
    String result = tipoProductoController.editarTipoProducto(tipoProducto, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/TipoProductos");
  }
  
  @Test
  public void givenIdProductvalidWithWorngDatawhenCalleditarTipoProductoThenReturnTheJspName() {
	// Arrange
	TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    Mockito.when(tipoProducto.isValidoParaActualizar()).thenReturn(true);
    Mockito.when(tipoProductoDao.editarTipoProducto(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    Mockito.when(tipoProducto.getCodigo()).thenReturn(10);
	  
	// Act    
    String result = tipoProductoController.editarTipoProducto(tipoProducto, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/ActualizarTipoProductos");
  }
  
  @Test
  public void givenProductInvalidwhenCalleditarTipoProductoThenReturnTheJspName() {
	// Arrange
	TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    Mockito.when(tipoProducto.isValidoParaActualizar()).thenReturn(false);
    Mockito.when(tipoProducto.getCodigo()).thenReturn(10);
	  
	// Act    
    String result = tipoProductoController.editarTipoProducto(tipoProducto, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/ActualizarTipoProductos");
  }
  
  @Test
  public void givenIdProductInvalidwhenCalleliminarTipoProductoThenReturnTheJspName() {
    // Act
    String result = tipoProductoController.eliminarTipoProducto(-1, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/TipoProductos");
  }
  
  @Test
  public void givenIdProductvalidwhenCallEliminarTipoProductoThenReturnTheJspName() {
    // Act
    String result = tipoProductoController.eliminarTipoProducto(10, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/EliminarTipoProductos");
  }
  
  @Test
  public void givenProductvalidwhenCallBorrarTipoProductoThenReturnTheJspName() {
	// Arrange
	TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    Mockito.when(tipoProductoDao.eliminarTipoProducto(Mockito.any())).thenReturn("Eliminacion exitosa");
	  
	// Act    
    String result = tipoProductoController.borrarTipoProducto(tipoProducto, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/TipoProductos");
  }
  
  @Test
  public void givenIdProductvalidWithWorngDatawhenCallBorrarTipoProductoThenReturnTheJspName() {
	// Arrange
	TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    Mockito.when(tipoProductoDao.eliminarTipoProducto(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    Mockito.when(tipoProducto.getCodigo()).thenReturn(10);
	  
	// Act    
    String result = tipoProductoController.borrarTipoProducto(tipoProducto, model);

    // Assert
    assertEquals(result, "Administrador/TipoProducto/EliminarTipoProductos");
  }
  
}
