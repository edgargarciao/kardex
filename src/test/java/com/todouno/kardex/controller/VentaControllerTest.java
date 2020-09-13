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
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.todouno.kardex.dao.VentaDao;
import com.todouno.kardex.dto.Factura;
import com.todouno.kardex.dto.TipoProducto;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class VentaControllerTest {

  @Mock
  private Model model;
  
  @Mock
  private VentaDao ventaDao;

  @InjectMocks
  private VentaController ventaController;

  @Test
  public void givenTheSystemWithoutVentaWhenCallIndexThenReturnTheModelNullAndTheJspName() {

    // Act
    String result = ventaController.index(model);

    // Assert
    assertEquals(result, "Administrador/Venta/Ventas");
    assertFalse(model.containsAttribute("productos"));
  }

  
  @Test
  public void givenAModelWhenCallsetUpUserFormThenReturnTheModelNotNull() {

    // Act
    Factura result = ventaController.setUpUserForm();

    // Assert
    assertNotNull(result);
  }
  
  
  @Test
  public void whenCallRegistrarTipoVentaThenReturnTheJspName() {
    // Act
    String result = ventaController.registrarVenta(model);

    // Assert
    assertEquals(result, "Administrador/Venta/RegistrarVenta");
  }

  @Test
  public void whenCallRegistrarFullVentaThenReturnTheNameJsp() {
    
    // Arrange
	Factura factura = Mockito.mock(Factura.class);
    Mockito.when(factura.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(ventaDao.registrarVenta(Mockito.any())).thenReturn("Registro exitoso");
    
    // Act
    ResponseEntity<String> result = ventaController.guardarVenta(factura);
    
    // Assert
    assertEquals("REGISTRO EXITOSO",result.getBody());
    
  }
  
  
  @Test
  public void whenCallGuardarVentaThenReturnTheNameJsp() {
    // Arrange
    Factura factura = Mockito.mock(Factura.class);
    Mockito.when(factura.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(ventaDao.registrarVenta(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    
    // Act
    ResponseEntity<String> result = ventaController.guardarVenta(factura);
    
    // Assert
    assertEquals("REGISTRO NO EXITOSO",result.getBody());    
  }
  
  @Test
  public void givenInvalidVentawhenCallguardarVentaThenReturnTheNameJsp() {
    // Arrange
    Factura factura = Mockito.mock(Factura.class);
    Mockito.when(factura.isValidoParaRegistrar()).thenReturn(false);
    
    // Act
    ResponseEntity<String> result = ventaController.guardarVenta(factura);
    
    // Assert
    assertEquals("CAMPOS INVALIDOS",result.getBody());    
  }
  
}
