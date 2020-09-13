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

import com.todouno.kardex.dao.VendedorDao;
import com.todouno.kardex.dto.Vendedor;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class VendedorControllerTest {

  @Mock
  private Model model;
  
  @Mock
  private VendedorDao vendedorDao;

  @InjectMocks
  private VendedorController vendedorController;

  @Test
  public void givenTheSystemWithoutVendedorWhenCallIndexThenReturnTheModelNullAndTheJspName() {

    // Act
    String result = vendedorController.index(model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/Vendedores");
    assertFalse(model.containsAttribute("vendedors"));
  }

  
  @Test
  public void givenAModelWhenCallsetUpUserFormThenReturnTheModelNotNull() {

    // Act
    Vendedor result = vendedorController.setUpUserForm();

    // Assert
    assertNotNull(result);
  }
  
  
  @Test
  public void whenCallRegistrarTipoVendedorThenReturnTheJspName() {
    // Act
    String result = vendedorController.registrarVendedor();

    // Assert
    assertEquals(result, "Administrador/Vendedor/RegistrarVendedor");
  }
  
  

  @Test
  public void whenCallRegistrarVendedorEmptyThenReturnTheNameJsp() {
    
    // Arrange
    Vendedor vendedor = Mockito.mock(Vendedor.class);
    
    // Act
    String result = vendedorController.guardarVendedor(vendedor, model);
    
    // Assert
    assertEquals("Administrador/Vendedor/RegistrarVendedor",result);
    
  }

  @Test
  public void whenCallRegistrarFullVendedorThenReturnTheNameJsp() {
    
    // Arrange
    Vendedor vendedor = Mockito.mock(Vendedor.class);
    Mockito.when(vendedor.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(vendedorDao.registrarVendedor(Mockito.any())).thenReturn("Registro exitoso");
    
    // Act
    String result = vendedorController.guardarVendedor(vendedor, model);
    
    // Assert
    assertEquals("Administrador/Vendedor/Vendedores",result);
    
  }
  
  
  @Test
  public void whenCallRegistrarIncorrectVendedorThenReturnTheNameJsp() {
    // Arrange
    Vendedor vendedor = Mockito.mock(Vendedor.class);
    Mockito.when(vendedor.isValidoParaRegistrar()).thenReturn(true);
    Mockito.when(vendedorDao.registrarVendedor(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    
    // Act
    String result = vendedorController.guardarVendedor(vendedor, model);
    
    // Assert
    assertEquals("Administrador/Vendedor/RegistrarVendedor",result);
    
  }
  
  
  @Test
  public void givenIdProductInvalidwhenCallActualizarVendedorThenReturnTheJspName() {
    // Act
    String result = vendedorController.actualizarVendedor(-1, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/Vendedores");
  }
  
  
  @Test
  public void givenIdProductvalidwhenCallActualizarVendedorThenReturnTheJspName() {
	// Arrange
	Vendedor vendedor = Mockito.mock(Vendedor.class);
	Mockito.when(vendedorDao.obtenerVendedorPorId(Mockito.anyLong())).thenReturn(vendedor);
	  
    // Act
    String result = vendedorController.actualizarVendedor(10, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/ActualizarVendedor");
  }
  
  
  @Test
  public void givenProductvalidwhenCalleditarVendedorThenReturnTheJspName() {
	// Arrange
	Vendedor vendedor = Mockito.mock(Vendedor.class);
    Mockito.when(vendedor.isValidoParaActualizar()).thenReturn(true);
    Mockito.when(vendedorDao.editarVendedor(Mockito.any())).thenReturn("Actualizacion exitosa");
	  
	// Act    
    String result = vendedorController.editarVendedor(vendedor, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/Vendedores");
  }
  
  
  @Test
  public void givenIdProductvalidWithWorngDatawhenCalleditarVendedorThenReturnTheJspName() {
	// Arrange
	Vendedor vendedor = Mockito.mock(Vendedor.class);
    Mockito.when(vendedor.isValidoParaActualizar()).thenReturn(true);
    Mockito.when(vendedorDao.editarVendedor(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    Mockito.when(vendedor.getCodigo()).thenReturn(10);
	  
	// Act    
    String result = vendedorController.editarVendedor(vendedor, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/ActualizarVendedor");
  }
  
  
  @Test
  public void givenProductInvalidwhenCalleditarVendedorThenReturnTheJspName() {
	// Arrange
	Vendedor vendedor = Mockito.mock(Vendedor.class);
    Mockito.when(vendedor.isValidoParaActualizar()).thenReturn(false);
    Mockito.when(vendedor.getCodigo()).thenReturn(10);
	  
	// Act    
    String result = vendedorController.editarVendedor(vendedor, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/ActualizarVendedor");
  }
  
  
  @Test
  public void givenIdProductInvalidwhenCalleliminarVendedorThenReturnTheJspName() {
    // Act
    String result = vendedorController.eliminarVendedor(-1, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/Vendedores");
  }
  
  
  @Test
  public void givenIdProductvalidwhenCallEliminarVendedorThenReturnTheJspName() {
    // Act
    String result = vendedorController.eliminarVendedor(10, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/EliminarVendedor");
  }
  
  
  @Test
  public void givenProductvalidwhenCallBorrarVendedorThenReturnTheJspName() {
	// Arrange
	Vendedor vendedor = Mockito.mock(Vendedor.class);
    Mockito.when(vendedorDao.eliminarVendedor(Mockito.any())).thenReturn("Eliminacion exitosa");
	  
	// Act    
    String result = vendedorController.borrarVendedor(vendedor, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/Vendedores");
  }
  
  
  @Test
  public void givenIdProductvalidWithWorngDatawhenCallBorrarVendedorThenReturnTheJspName() {
	// Arrange
	Vendedor vendedor = Mockito.mock(Vendedor.class);
    Mockito.when(vendedorDao.eliminarVendedor(Mockito.any())).thenReturn("Error en el sistema. Por favor contacte al administrador.");
    Mockito.when(vendedor.getCodigo()).thenReturn(10);
	  
	// Act    
    String result = vendedorController.borrarVendedor(vendedor, model);

    // Assert
    assertEquals(result, "Administrador/Vendedor/EliminarVendedor");
  }
  
}
