package com.todouno.kardex.dto;

import org.springframework.util.StringUtils;

public class TipoProducto {

  private int codigo;
  private String nombre;
  private String descripcion;
  
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
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  public boolean isValidoParaRegistrar() {
    
    return (!StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.descripcion));
  }
  public boolean isValidoParaActualizar() {
    return (codigo != 0 && !StringUtils.isEmpty(this.nombre) && !StringUtils.isEmpty(this.descripcion));
  }
  
}
