package com.todouno.kardex.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.todouno.kardex.data.DataMgr;
import com.todouno.kardex.dto.TipoProducto;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TipoProductoDaoTest {

	@Mock
	private DataMgr dataMgr;
	
	@InjectMocks
	private TipoProductoDao tipoProductoDao;
	
	@Test
	public void whenCallGetTipoProductosThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getInt(Mockito.anyString())).thenReturn(12);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("Nombre").thenReturn("Descripcion");
		
		// Act
		List<TipoProducto> result = tipoProductoDao.getTipoProductos();
		
		// Assert
		assertNotNull(result);
	}
	
	@Test
	public void whenCallRegistrarTipoDeProductoThenSaveTheProduct() {
		// Arrange
		TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		
		// Act
		String result = tipoProductoDao.registrarTipoDeProducto(tipoProducto);
		
		// Assert		
		assertEquals("Registro exitoso", result);
	}
	
	@Test
	public void whenCallRegistrarTipoDeProductoThenDontSaveTheProduct() {
		// Arrange
		TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = tipoProductoDao.registrarTipoDeProducto(tipoProducto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallObtenerTipoProductoPorIdsThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getInt(Mockito.anyString())).thenReturn(12);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("Nombre").thenReturn("Descripcion");
		
		// Act
		TipoProducto result = tipoProductoDao.obtenerTipoProductoPorId(12l);
		
		// Assert
		assertNotNull(result);
	}
	
	@Test
	public void whenCallObtenerTipoProductoPorIdsWithoutResultThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(false);
		
		// Act
		TipoProducto result = tipoProductoDao.obtenerTipoProductoPorId(12l);
		
		// Assert
		assertNotNull(result);
	}
	
	
	@Test
	public void whenCallEditarTipoProductoThenModifyTheProduct() {
		// Arrange
		TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		
		// Act
		String result = tipoProductoDao.editarTipoProducto(tipoProducto);
		
		// Assert		
		assertEquals("Actualizacion exitosa", result);
	}
	
	@Test
	public void whenCallEditarTipoProductoThenDontModifyTheProduct() {
		// Arrange
		TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = tipoProductoDao.editarTipoProducto(tipoProducto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallEliminarTipoProductoThenDeleteTheProduct() {
		// Arrange
		TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		
		// Act
		String result = tipoProductoDao.eliminarTipoProducto(tipoProducto);
		
		// Assert		
		assertEquals("Eliminacion exitosa", result);
	}
	
	@Test
	public void whenCallEliminarTipoProductoThenDontDeleteTheProduct() {
		// Arrange
		TipoProducto tipoProducto = Mockito.mock(TipoProducto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = tipoProductoDao.eliminarTipoProducto(tipoProducto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
}
