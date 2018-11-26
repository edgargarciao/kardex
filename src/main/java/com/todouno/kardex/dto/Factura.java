package com.todouno.kardex.dto;

import java.sql.Date;
import java.util.List;

public class Factura {

	private int codigo;
	private Date fecha;
	private double totalFactura;
	private Vendedor vendedor;
	private long iva;
	private long total;
	private List<DetalleFactura> detalles;

	// Este es un comodin
	private int producto;

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

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}

	public double getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(double totalFactura) {
		this.totalFactura = totalFactura;
	}

	public long getIva() {
		return iva;
	}

	public void setIva(long iva) {
		this.iva = iva;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public boolean isValidoParaRegistrar() {
		// TODO Auto-generated method stub
		return false;
	}

}
