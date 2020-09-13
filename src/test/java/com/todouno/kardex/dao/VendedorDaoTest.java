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
import com.todouno.kardex.dto.Vendedor;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class VendedorDaoTest {
	
	@Mock
	private DataMgr dataMgr;
	
	@InjectMocks
	private VendedorDao vendedorDao;
	
	@Test
	public void whenCallRegistrarVendedorThenSaveTheProduct() {
		// Arrange
		Vendedor producto = Mockito.mock(Vendedor.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		
		// Act
		String result = vendedorDao.registrarVendedor(producto);
		
		// Assert		
		assertEquals("Registro exitoso", result);
	}
	
	@Test
	public void whenCallRegistrarVendedorThenDontSaveTheProduct() {
		// Arrange
		Vendedor producto = Mockito.mock(Vendedor.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = vendedorDao.registrarVendedor(producto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallObtenerVendedorPorIdsThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getInt(Mockito.anyString())).thenReturn(12);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("Nombre").thenReturn("Descripcion");
		
		// Act
		Vendedor result = vendedorDao.obtenerVendedorPorId(12l);
		
		// Assert
		assertNotNull(result);
	}
	
	@Test
	public void whenCallObtenerVendedorPorIdWithoutResultThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(false).thenReturn(false);

		// Act
		Vendedor result = vendedorDao.obtenerVendedorPorId(12l);
		
		// Assert
		assertNotNull(result);
	}
	
	
	@Test
	public void whenCallEditarVendedorThenModifyTheProduct() {
		// Arrange
		Vendedor producto = Mockito.mock(Vendedor.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		
		// Act
		String result = vendedorDao.editarVendedor(producto);
		
		// Assert		
		assertEquals("Actualizacion exitosa", result);
	}
	
	@Test
	public void whenCallEditarVendedorThenDontModifyTheProduct() {
		// Arrange
		Vendedor producto = Mockito.mock(Vendedor.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = vendedorDao.editarVendedor(producto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallEliminarVendedorThenDeleteTheProduct() {
		// Arrange
		Vendedor producto = Mockito.mock(Vendedor.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		
		// Act
		String result = vendedorDao.eliminarVendedor(producto);
		
		// Assert		
		assertEquals("Eliminacion exitosa", result);
	}
	
	@Test
	public void whenCallEliminarVendedorThenDontDeleteTheProduct() {
		// Arrange
		Vendedor producto = Mockito.mock(Vendedor.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = vendedorDao.eliminarVendedor(producto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
}
