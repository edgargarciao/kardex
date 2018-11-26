package com.todouno.kardex.dao;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.todouno.kardex.data.DataMgr;
import com.todouno.kardex.dto.DetalleFactura;
import com.todouno.kardex.dto.Factura;
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.ResultDB;

public class VentaDao {

	private DataMgr dataMgr;
	private VendedorDao vendedorDao;
	private ProductoDao productoDao;

	public VentaDao() {
		dataMgr = new DataMgr();
		vendedorDao = new VendedorDao();
		productoDao = new ProductoDao();
	}

	public List<Factura> getVentas() {
		List<Factura> facturas = new LinkedList<>();

		// Consulta para realizar en base de datos
		SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM FACTURA");

		// Recorre cada registro obtenido de base de datos
		while (sqlRowSet.next()) {
			// Objeto en el que sera guardada la informacion del registro
			Factura factura = new Factura();

			factura.setCodigo(sqlRowSet.getInt("codigo"));
			factura.setFecha(sqlRowSet.getDate("fecha"));
			factura.setVendedor(vendedorDao.obtenerVendedorPorId(sqlRowSet.getInt("vendedor")));
			factura.setTotalFactura(sqlRowSet.getDouble("totalFactura"));

			// Guarda el registro para ser retornado
			facturas.add(factura);
		}
		return facturas;
	}

	public String registrarVenta(Factura venta) {

		// Agrego los datos del registro (nombreColumna/Valor)

		MapSqlParameterSource map = new MapSqlParameterSource();

		map.addValue("totalFactura", venta.getTotalFactura());
		map.addValue("vendedor", venta.getVendedor().getCodigo());
		map.addValue("iva", venta.getIva());
		map.addValue("total", venta.getTotal());

		// Armar la sentencia de actualizacion debase de datos
		String query = "INSERT INTO FACTURA(fecha,totalFactura,vendedor,iva,total) VALUES(CURRENT_DATE(),:totalFactura,:vendedor,:iva,:total)";

		// Ejecutar la sentencia
		ResultDB result = null;
		try {
			result = dataMgr.executeDmlWithKey(query, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		insertarDetalles(result.getKey(),venta);
		// Si hubieron filas afectadas es por que si hubo registro, en caso
		// contrario muestra el mensaje
		// de error.
		return (result.getResult() == 1) ? "Registro exitoso" : "Error en el sistema. Por favor contacte al administrador.";

	}

	private void insertarDetalles(long idFactura, Factura venta) {
		for(DetalleFactura detalleFactura : venta.getDetalles()){
			 // Agrego los datos del registro (nombreColumna/Valor)

		    MapSqlParameterSource map = new MapSqlParameterSource();    
		    map.addValue("cantidad", detalleFactura.getCantidad());
		    map.addValue("producto", detalleFactura.getProducto().getCodigo());
		    map.addValue("factura", idFactura);
		    
		    // Armar la sentencia de actualizacion debase de datos
		    String query = "INSERT INTO DETALLE(cantidad,producto,factura) VALUES(:cantidad,:producto,:factura)";

		    // Ejecutar la sentencia
		    try {
		      dataMgr.executeDml(query, map);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    
		    actualizarStockProducto(detalleFactura.getProducto().getCodigo(), detalleFactura.getCantidad());
		    
		}
		
	}

	private void actualizarStockProducto(int codigoProducto, int cantidad) {
		Producto producto = productoDao.obtenerProductoPorId(codigoProducto);
		
		 // Agrego los datos del registro (nombreColumna/Valor)

	    MapSqlParameterSource map = new MapSqlParameterSource();    
	    map.addValue("cantidad", producto.getCantidad() - cantidad);	    
	    map.addValue("codigo", codigoProducto);
	    
	    // Armar la sentencia de actualizacion debase de datos
	    String query = "UPDATE PRODUCTO SET stock = :cantidad WHERE codigo = :codigo";

	    // Ejecutar la sentencia
	    try {
	      dataMgr.executeDml(query, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    
	}

}
