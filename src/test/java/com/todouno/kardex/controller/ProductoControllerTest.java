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

import com.todouno.kardex.dao.ProductoDao;
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.TipoProducto;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ProductoControllerTest {

  @Mock
  private Model model;
  
  @Mock
  private ProductoDao productoDao;

  @InjectMocks
  private ProductoController productoController;

  @Test
  public void givenTheSystemWithoutProductoWhenCallIndexThenReturnTheModelNullAndTheJspName() {

    // Act
    String result = productoController.index(model);

    // Assert
    assertEquals(result, "Administrador/Producto/Productos");
    assertFalse(model.containsAttribute("productos"));
  }

  
  @Test
  public void givenAModelWhenCallsetUpUserFormThenReturnTheModelNotNull() {

    // Act
    Producto result = productoController.setUpUserForm();

    // Assert
    assertNotNull(result);
  }
  
  
  @Test
  public void whenCallRegistrarTipoProductoThenReturnTheJspName() {
    // Act
    String result = productoController.registrarProducto(model);

    // Assert
    assertEquals(result, "Administrador/Producto/RegistrarProducto");
  }
  
  

  @Test
  public void whenCallRegistrarProductoEmptyThenReturnTheNameJsp() {
    
    // Arrange
    Producto producto = Mockito.mock(Producto.class);
    
    // Act
    String result = productoController.registrarProducto(producto, model);
    
    // Assert
    assertEquals("Administrador/Producto/RegistrarProducto",result);
    
  }

  @Test
  public void whenCallRegistrarFullProductoThenReturnTheNameJsp() {
    
    // Arrange
    Producto producto = Mockito.mock(Producto.class);
    Mockito.when(producto.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(productoDao.registrarProducto(Mockito.any())).thenReturn("Registro exitoso");
    
    // Act
    String result = productoController.registrarProducto(producto, model);
    
    // Assert
    assertEquals("Administrador/Producto/Productos",result);
    
  }
  
  
  @Test
  public void whenCallRegistrarIncorrectProductoThenReturnTheNameJsp() {
    // Arrange
    Producto producto = Mockito.mock(Producto.class);
    Mockito.when(producto.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(productoDao.registrarProducto(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    
    // Act
    String result = productoController.registrarProducto(producto, model);
    
    // Assert
    assertEquals("Administrador/Producto/RegistrarProducto",result);
    
  }
  
  
  @Test
  public void givenIdProductInvalidwhenCallActualizarProductoThenReturnTheJspName() {
    // Act
    String result = productoController.actualizarProducto(-1, model);

    // Assert
    assertEquals(result, "Administrador/Producto/Productos");
  }
  
  
  @Test
  public void givenIdProductvalidwhenCallActualizarProductoThenReturnTheJspName() {
	// Arrange
	Producto producto = Mockito.mock(Producto.class);
	Mockito.when(productoDao.obtenerProductoPorId(Mockito.anyLong())).thenReturn(producto);
	Mockito.when(producto.getTipoProducto()).thenReturn(new TipoProducto());
	  
    // Act
    String result = productoController.actualizarProducto(10, model);

    // Assert
    assertEquals(result, "Administrador/Producto/ActualizarProductos");
  }
  
  
  @Test
  public void givenProductvalidwhenCalleditarProductoThenReturnTheJspName() {
	// Arrange
	Producto producto = Mockito.mock(Producto.class);
    Mockito.when(producto.isValidoParaActualizar()).thenReturn(true);
    Mockito.when(productoDao.editarProducto(Mockito.any())).thenReturn("Actualizacion exitosa");
	  
	// Act    
    String result = productoController.editarProducto(producto, model);

    // Assert
    assertEquals(result, "Administrador/Producto/Productos");
  }
  
  
  @Test
  public void givenIdProductvalidWithWorngDatawhenCalleditarProductoThenReturnTheJspName() {
	// Arrange
	Producto producto = Mockito.mock(Producto.class);
    Mockito.when(producto.isValidoParaActualizar()).thenReturn(true);
    Mockito.when(productoDao.editarProducto(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    Mockito.when(producto.getCodigo()).thenReturn(10);    
	Mockito.when(productoDao.obtenerProductoPorId(Mockito.anyLong())).thenReturn(producto);
	Mockito.when(producto.getTipoProducto()).thenReturn(new TipoProducto());
	  
	// Act    
    String result = productoController.editarProducto(producto, model);

    // Assert
    assertEquals(result, "Administrador/Producto/ActualizarProductos");
  }
  
  
  @Test
  public void givenProductInvalidwhenCalleditarProductoThenReturnTheJspName() {
	// Arrange
	Producto producto = Mockito.mock(Producto.class);
    Mockito.when(producto.isValidoParaActualizar()).thenReturn(false);
    Mockito.when(producto.getCodigo()).thenReturn(10);
	Mockito.when(productoDao.obtenerProductoPorId(Mockito.anyLong())).thenReturn(producto);
	Mockito.when(producto.getTipoProducto()).thenReturn(new TipoProducto());
	  
	// Act    
    String result = productoController.editarProducto(producto, model);

    // Assert
    assertEquals(result, "Administrador/Producto/ActualizarProductos");
  }
  
  
  @Test
  public void givenIdProductInvalidwhenCalleliminarProductoThenReturnTheJspName() {
    // Act
    String result = productoController.eliminarProducto(-1, model);

    // Assert
    assertEquals(result, "Administrador/Producto/Productos");
  }
  
  
  @Test
  public void givenIdProductvalidwhenCallEliminarProductoThenReturnTheJspName() {
    // Act
    String result = productoController.eliminarProducto(10, model);

    // Assert
    assertEquals(result, "Administrador/Producto/EliminarProductos");
  }
  
  
  @Test
  public void givenProductvalidwhenCallBorrarProductoThenReturnTheJspName() {
	// Arrange
	Producto producto = Mockito.mock(Producto.class);
    Mockito.when(productoDao.eliminarProducto(Mockito.any())).thenReturn("Eliminacion exitosa");
	  
	// Act    
    String result = productoController.borrarProducto(producto, model);

    // Assert
    assertEquals(result, "Administrador/Producto/Productos");
  }
  
  
  @Test
  public void givenIdProductvalidWithWorngDatawhenCallBorrarProductoThenReturnTheJspName() {
	// Arrange
	Producto producto = Mockito.mock(Producto.class);
    Mockito.when(productoDao.eliminarProducto(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    Mockito.when(producto.getCodigo()).thenReturn(10);
	  
	// Act    
    String result = productoController.borrarProducto(producto, model);

    // Assert
    assertEquals(result, "Administrador/Producto/EliminarProductos");
  }
  
}
