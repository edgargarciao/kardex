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
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.TipoProducto;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ProductoDaoTest {

	@Mock 
	private TipoProductoDao tipoProductoDao;
	
	@Mock
	private DataMgr dataMgr;
	
	@InjectMocks
	private ProductoDao productoDao;
	
	@Test
	public void whenCallGetProductosThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getInt(Mockito.anyString())).thenReturn(12);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("Nombre").thenReturn("Descripcion");
		Mockito.when(tipoProductoDao.obtenerTipoProductoPorId(Mockito.anyLong())).thenReturn(new TipoProducto());
		
		// Act
		List<Producto> result = productoDao.getProductos();
		
		// Assert
		assertNotNull(result);
	}
	
	@Test
	public void whenCallRegistrarProductoThenSaveTheProduct() {
		// Arrange
		Producto producto = Mockito.mock(Producto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		Mockito.when(producto.getTipoProducto()).thenReturn(new TipoProducto());
		
		// Act
		String result = productoDao.registrarProducto(producto);
		
		// Assert		
		assertEquals("Registro exitoso", result);
	}
	
	@Test
	public void whenCallRegistrarProductoThenDontSaveTheProduct() {
		// Arrange
		Producto producto = Mockito.mock(Producto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		Mockito.when(producto.getTipoProducto()).thenReturn(new TipoProducto());
		
		// Act
		String result = productoDao.registrarProducto(producto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallObtenerProductoPorIdsThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(sqlRowSet.getInt(Mockito.anyString())).thenReturn(12);
		Mockito.when(sqlRowSet.getString(Mockito.anyString())).thenReturn("Nombre").thenReturn("Descripcion");
		
		// Act
		Producto result = productoDao.obtenerProductoPorId(12l);
		
		// Assert
		assertNotNull(result);
	}
	
	@Test
	public void whenCallObtenerProductoPorIdWithoutResultThenReturnAllProducts() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(false);
		
		// Act
		Producto result = productoDao.obtenerProductoPorId(12l);
		
		// Assert
		assertNotNull(result);
	}
	
	
	@Test
	public void whenCallEditarProductoThenModifyTheProduct() {
		// Arrange
		Producto producto = Mockito.mock(Producto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		Mockito.when(producto.getTipoProducto()).thenReturn(new TipoProducto());
		
		// Act
		String result = productoDao.editarProducto(producto);
		
		// Assert		
		assertEquals("Actualizacion exitosa", result);
	}
	
	@Test
	public void whenCallEditarProductoThenDontModifyTheProduct() {
		// Arrange
		Producto producto = Mockito.mock(Producto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		Mockito.when(producto.getTipoProducto()).thenReturn(new TipoProducto());
		
		// Act
		String result = productoDao.editarProducto(producto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallEliminarProductoThenDeleteTheProduct() {
		// Arrange
		Producto producto = Mockito.mock(Producto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1);
		
		// Act
		String result = productoDao.eliminarProducto(producto);
		
		// Assert		
		assertEquals("Eliminacion exitosa", result);
	}
	
	@Test
	public void whenCallEliminarProductoThenDontDeleteTheProduct() {
		// Arrange
		Producto producto = Mockito.mock(Producto.class);
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = productoDao.eliminarProducto(producto);
		
		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
}
