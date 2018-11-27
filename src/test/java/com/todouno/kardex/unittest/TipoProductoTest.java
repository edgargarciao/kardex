package com.todouno.kardex.unittest;

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
import com.todouno.kardex.controller.TipoProductoController;
import com.todouno.kardex.dto.TipoProducto;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TipoProductoTest {

  @Mock
  private Model model;

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
  public void whenCallRegistrarTipoProductoThenReturnTheNameJsp() {
    
    // Arrange
    TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
    
    // Act
    String result = tipoProductoController.registrarTipoProducto(tipoProducto, model);
    
    // Assert
    assertEquals("Administrador/TipoProducto/RegistrarTipoProducto",result);
    
  }

}
