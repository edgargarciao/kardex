package com.todouno.kardex.dto;


import java.sql.Date;
import org.springframework.util.StringUtils;

public class Vendedor {

  private int codigo;
  private String documento;
  private String nombres;
  private String telefono;
  private String correo;
  private Date fechaDeNacimiento;

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public Date getFechaDeNacimiento() {
    return fechaDeNacimiento;
  }

  public void setFechaDeNacimiento(Date fechaDeNacimiento) {
    this.fechaDeNacimiento = fechaDeNacimiento;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public boolean isValidoParaRegistrar() {

    return (!StringUtils.isEmpty(this.nombres) && !StringUtils.isEmpty(this.documento) && !StringUtils.isEmpty(this.correo) && !StringUtils.isEmpty(this.fechaDeNacimiento) && !StringUtils.isEmpty(this.telefono));
  }

  public boolean isValidoParaActualizar() {
    return (codigo != 0 && !StringUtils.isEmpty(this.documento) && !StringUtils.isEmpty(this.correo) && !StringUtils.isEmpty(this.fechaDeNacimiento) && !StringUtils.isEmpty(this.telefono));
  }

}
