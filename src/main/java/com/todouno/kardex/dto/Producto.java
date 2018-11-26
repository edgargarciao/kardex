package com.todouno.kardex.dto;

import org.springframework.util.StringUtils;

public class Producto {

  private int codigo;
  private String nombre;
  private int cantidad;
  private TipoProducto tipoProducto;
  private int precio;

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public TipoProducto getTipoProducto() {
    return tipoProducto;
  }

  public void setTipoProducto(TipoProducto tipoProducto) {
    this.tipoProducto = tipoProducto;
  }

  public int getPrecio() {
    return precio;
  }

  public void setPrecio(int precio) {
    this.precio = precio;
  }

  public boolean isValidoParaRegistrar() {

    return (!StringUtils.isEmpty(this.nombre) && this.cantidad > 0 && this.precio > 0 && this.tipoProducto.getCodigo() > 0 );
  }

  public boolean isValidoParaActualizar() {
    return (this.codigo > 0 &&  !StringUtils.isEmpty(this.nombre) && this.cantidad > 0 && this.precio > 0 && this.tipoProducto.getCodigo() > 0 );
  }

}
