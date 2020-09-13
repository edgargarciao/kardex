package com.todouno.kardex.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.todouno.kardex.data.DataMgr;
import com.todouno.kardex.dto.DetalleFactura;
import com.todouno.kardex.dto.Factura;
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.ResultDB;
import com.todouno.kardex.dto.Vendedor;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class VentaDaoTest {

	@Mock
	private ProductoDao productoDao;

	@Mock
	private VendedorDao vendedorDao;

	@Mock
	private DataMgr dataMgr;

	@InjectMocks
	private VentaDao ventaDao;

	@Test
	public void whenCallGetProductosThenReturnAllProducts() {

		// Arrange
		SqlRowSet sqlRowSet = Mockito.mock(SqlRowSet.class);
		doReturn(sqlRowSet).when(dataMgr).executeQuery(Mockito.anyString());
		Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(false);
		Mockito.when(sqlRowSet.getInt(Mockito.anyString())).thenReturn(12).thenReturn(12).thenReturn(12).thenReturn(12)
				.thenReturn(12);
		Mockito.when(sqlRowSet.getDouble(Mockito.anyString())).thenReturn(1899029d);
		Mockito.when(sqlRowSet.getDate(Mockito.anyString())).thenReturn(new Date(1212121211));
		Mockito.when(vendedorDao.obtenerVendedorPorId(Mockito.anyLong())).thenReturn(new Vendedor());
		doReturn(sqlRowSet).when(dataMgr).executeQuery(Mockito.anyString(), Mockito.any());
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		doReturn(new Producto()).when(productoDao).obtenerProductoPorId(Mockito.anyLong());

		// Act
		List<Factura> result = ventaDao.getVentas();

		// Assert
		assertNotNull(result);
	}

	@Test
	public void whenCallRegistrarVentaThenSaveTheProduct() {
		// Arrange
		Factura factura = Mockito.mock(Factura.class);
		doReturn(new ResultDB(1, 100)).when(dataMgr).executeDmlWithKey(Mockito.anyString(), Mockito.any());
		Mockito.when(factura.getVendedor()).thenReturn(new Vendedor());
		Mockito.when(factura.getDetalles()).thenReturn(getDetalles());
		doReturn(new Producto()).when(productoDao).obtenerProductoPorId(Mockito.anyLong());
		
		// Act
		String result = ventaDao.registrarVenta(factura);

		// Assert
		assertEquals("Registro exitoso", result);
	}

	@Test
	public void whenCallRegistrarVentaThenThrowExceptionSavingBuy() {
		// Arrange
		Factura factura = Mockito.mock(Factura.class);
		Mockito.when(factura.getVendedor()).thenReturn(new Vendedor());
		Mockito.when(dataMgr.executeDmlWithKey(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = ventaDao.registrarVenta(factura);

		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallRegistrarVentaThenThrowExceptionSavingBuyDetails() {
		// Arrange
		Factura factura = Mockito.mock(Factura.class);
		Mockito.when(factura.getVendedor()).thenReturn(new Vendedor());
		doReturn(new ResultDB(0, 100)).when(dataMgr).executeDmlWithKey(Mockito.anyString(), Mockito.any());
		Mockito.when(factura.getVendedor()).thenReturn(new Vendedor());
		Mockito.when(factura.getDetalles()).thenReturn(getDetalles());
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenThrow(new RuntimeException());
		
		// Act
		String result = ventaDao.registrarVenta(factura);

		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	@Test
	public void whenCallRegistrarVentaThenThrowExceptionUpdatingStock() {
		// Arrange
		Factura factura = Mockito.mock(Factura.class);
		Mockito.when(factura.getVendedor()).thenReturn(new Vendedor());
		doReturn(new ResultDB(0, 100)).when(dataMgr).executeDmlWithKey(Mockito.anyString(), Mockito.any());
		Mockito.when(factura.getVendedor()).thenReturn(new Vendedor());
		Mockito.when(factura.getDetalles()).thenReturn(getDetalles());
		doReturn(new Producto()).when(productoDao).obtenerProductoPorId(Mockito.anyLong());
		Mockito.when(dataMgr.executeDml(Mockito.anyString(), Mockito.any())).thenReturn(1).thenThrow(new RuntimeException());
		
		// Act
		String result = ventaDao.registrarVenta(factura);

		// Assert
		assertEquals("Error en el sistema. Por favor contacte al administrador.", result);
	}
	
	private List<DetalleFactura> getDetalles() {
		List<DetalleFactura> detalles = new ArrayList<DetalleFactura>();
		DetalleFactura detalleFactura = new DetalleFactura();
		detalleFactura.setCantidad(12);
		detalleFactura.setCodigo(12);
		detalleFactura.setProducto(new Producto());
		detalles.add(detalleFactura);
		return detalles;
	}

}
