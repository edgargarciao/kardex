package com.todouno.kardex.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.todouno.kardex.config.SessionManager;
import com.todouno.kardex.dao.LoginDao;
import com.todouno.kardex.dao.ProductoDao;
import com.todouno.kardex.dao.UsuarioDao;
import com.todouno.kardex.dto.Login;
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.TipoProducto;
import com.todouno.kardex.util.JwtUtil;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class AdminControllerTest {

  @Mock
  private Model model;
  
  @Mock
  private LoginDao loginDao;
  
  @Mock
  private JwtUtil jwtUtil;
  
  @Mock
  private SessionManager sessionManager;
  
  @Mock
  private UsuarioDao usuarioDao;

  @InjectMocks
  private AdminController adminController;

  @Test
  public void givenTheSystemWithoutProductoWhenCallLoginThenReturnTheModelNullAndTheJspName() {

    // Act
    String result = adminController.main(model);

    // Assert
    assertEquals(result, "Administrador/Login");
  }
  
  @Test
  public void givenTheSystemWithoutProductoWhenCallIndexAdminThenReturnTheModelNullAndTheJspName() {

    // Act
    String result = adminController.indexAdmin(model);

    // Assert
    assertEquals(result, "Administrador/IndexAdmin");
  }
  
  @Test
  public void givenTheSystemWithoutProductoWhenCallRecordarThenReturnTheModelNullAndTheJspName() {

    // Act
    String result = adminController.recordar();

    // Assert
    assertEquals(result, "Administrador/Recordar");
  }

  
  @Test
  public void givenAModelWhenCallsetUpUserFormThenReturnTheModelNotNull() {

    // Act
    Login result = adminController.setUpUserForm();

    // Assert
    assertNotNull(result);
  }
  
 
  @Test
  public void whenCallAuthenticateUserThenReturnTheJspName() {
	// Arrange
	Login login = new Login();
	login.setCorreoEmpresarial("egarcia@todo1.net");
	login.setContrasena("pass");
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	Mockito.when(loginDao.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn("egarcia@todo1.net");
	HttpSession session = Mockito.mock(HttpSession.class);
	Mockito.when(request.getSession()).thenReturn(session);
	  
    // Act
    String result = adminController.authenticateUser(login, model, request);

    // Assert
    assertEquals(result, "Administrador/IndexAdmin");
  }
  
  @Test
  public void givenInvalidUserInfowhenCallAuthenticateUserThenReturnTheJspName() {
	// Arrange
	Login login = new Login();
	login.setCorreoEmpresarial("egarcia@todo1.net");
	login.setContrasena("pass");
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	Mockito.when(loginDao.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn("");
	  
    // Act
    String result = adminController.authenticateUser(login, model, request);

    // Assert
    assertEquals(result, "Administrador/Login");
  }
  
  @Test
  public void givenPassEmptyWhenCallAuthenticateUserThenReturnTheJspName() {
	// Arrange
	Login login = new Login();
	login.setCorreoEmpresarial("egarcia@todo1.net");
	login.setContrasena("");
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	  
    // Act
    String result = adminController.authenticateUser(login, model, request);

    // Assert
    assertEquals(result, "Administrador/Login");
  }
  
  @Test
  public void givenEmailEmptyWhenCallAuthenticateUserThenReturnTheJspName() {
	// Arrange
	Login login = new Login();
	login.setCorreoEmpresarial("");
	login.setContrasena("pass");
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	  
    // Act
    String result = adminController.authenticateUser(login, model, request);

    // Assert
    assertEquals(result, "Administrador/Login");
  }
  
  @Test
  public void whenCallGetLogOutThenReturnTheJspName() {
	// Arrange
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	HttpSession session = Mockito.mock(HttpSession.class);
	Mockito.when(request.getSession()).thenReturn(session);
	  
    // Act
    String result = adminController.getLogOut("", request);

    // Assert
    assertEquals(result, "Administrador/Login");
  }
  
  @Test
  public void givenEmailNotEmptywhenCallGetLogOutThenReturnTheJspName() {
	// Arrange
	Login login = new Login();
	login.setCorreoEmpresarial("egarcia@todo1.net");
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	Mockito.when(usuarioDao.enviarCorreo(Mockito.anyString())).thenReturn("Actualizacion");
	  
    // Act
    String result = adminController.recordarContrasena(login, model, request);

    // Assert
    assertEquals(result, "Administrador/Login");
  }
  
  @Test
  public void givenEmailInvalidWhenCallGetLogOutThenReturnTheJspName() {
	// Arrange
	Login login = new Login();
	login.setCorreoEmpresarial("egarcia@todo1.net");
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	Mockito.when(usuarioDao.enviarCorreo(Mockito.anyString())).thenReturn("Correo no autorizado. Debes permitir el acceso de aplicaciones no seguras en la configuracion de google.");
	  
    // Act
    String result = adminController.recordarContrasena(login, model, request);

    // Assert
    assertEquals(result, "Administrador/Recordar");
  }
  
  @Test
  public void givenEmailEmptywhenCallGetLogOutThenReturnTheJspName() {
	// Arrange
	Login login = new Login();
	login.setCorreoEmpresarial("");
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	  
    // Act
    String result = adminController.recordarContrasena(login, model, request);

    // Assert
    assertEquals(result, "Administrador/Recordar");
  }
  
}
