package com.todouno.kardex.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.todouno.kardex.data.DataMgr;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class UsuarioDaoTest {

	@Mock
	private DataMgr dataMgr;
	
	@InjectMocks
	private UsuarioDao usuarioDao;
	
	@Test
	public void whenCallEnviarCorreoThenReturnTheInfo() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("pass");
		
		// Act
		String result = usuarioDao.enviarCorreo("egarcia@todo1.net");
		
		// Assert
		assertEquals(result, "Actualizacion");
		assertNotNull(result);
	}
	
	@Test
	public void givenEmailNonExistentwhenCallEnviarCorreoThenReturnTheInfo() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("");
		
		// Act
		String result = usuarioDao.enviarCorreo("egarcia@todo1.net");
		
		// Assert
		assertEquals(result, "El correo no esta registrado en el sistema. Contacte al administrador.");
		assertNotNull(result);
	}
	
	@Test
	public void givenEmailInvalidwhenCallEnviarCorreoThenReturnTheInfo() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("");
		
		// Act
		String result = usuarioDao.enviarCorreo("egarciaqw@todo1.net");
		
		// Assert
		assertEquals(result, "El correo no esta registrado en el sistema. Contacte al administrador.");
		assertNotNull(result);
	}
	
	@Test
	public void givenEmailInvalid2whenCallEnviarCorreoThenReturnTheInfo() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(false);
		
		// Act
		String result = usuarioDao.enviarCorreo("egarciaqw@todo1.net");
		
		// Assert
		assertEquals(result, "El correo no esta registrado en el sistema. Contacte al administrador.");
		assertNotNull(result);
	}

}
