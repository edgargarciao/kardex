package com.todouno.kardex.dao;

import java.util.LinkedList;
import java.util.List;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.todouno.kardex.data.DataMgr;
import com.todouno.kardex.dto.Factura;

public class VentaDao {

  private DataMgr dataMgr;
  private VendedorDao vendedorDao;

  public VentaDao() {
    dataMgr = new DataMgr();
    vendedorDao = new VendedorDao();
  }


  public List<Factura> getVentas() {
    List<Factura> facturas = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM PRODUCTO");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Factura factura= new Factura();

      factura.setCodigo(sqlRowSet.getInt("codigo"));
      factura.setFecha(sqlRowSet.getDate("fecha"));
      factura.setVendedor(
          vendedorDao.obtenerVendedorPorId(sqlRowSet.getInt("vendedor")));
      factura.setTotalFactura(sqlRowSet.getInt("totalFactura"));
      

      // Guarda el registro para ser retornado
      facturas.add(factura);
    }
    return facturas;
  }


}
