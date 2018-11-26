package com.todouno.kardex.dto;

import java.sql.Date;

public class Factura {

  private int codigo;
  private Date fecha;
  private int totalFactura;
  private Vendedor vendedor;

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public int getTotalFactura() {
    return totalFactura;
  }

  public void setTotalFactura(int totalFactura) {
    this.totalFactura = totalFactura;
  }

  public Vendedor getVendedor() {
    return vendedor;
  }

  public void setVendedor(Vendedor vendedor) {
    this.vendedor = vendedor;
  }


}
