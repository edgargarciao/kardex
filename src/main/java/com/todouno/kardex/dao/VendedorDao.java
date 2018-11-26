package com.todouno.kardex.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.todouno.kardex.data.DataMgr;
import com.todouno.kardex.dto.Vendedor;

public class VendedorDao {

	private DataMgr dataMgr;

	public VendedorDao() {
		dataMgr = new DataMgr();
	}

	public List<Vendedor> getVendedores() {
		List<Vendedor> vendedores = new LinkedList<>();

		// Consulta para realizar en base de datos
		SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM VENDEDOR");

		// Recorre cada registro obtenido de base de datos
		while (sqlRowSet.next()) {
			// Objeto en el que sera guardada la informacion del registro
			Vendedor vendedor = new Vendedor();

			vendedor.setCodigo(sqlRowSet.getInt("codigo"));
			vendedor.setNombres(sqlRowSet.getString("nombres"));
			vendedor.setDocumento(sqlRowSet.getString("documento"));
			vendedor.setFechaDeNacimiento(sqlRowSet.getDate("fechaNacimiento"));
			vendedor.setTelefono(sqlRowSet.getString("telefono"));
			vendedor.setCorreo(sqlRowSet.getString("correo"));

			// Guarda el registro para ser retornado
			vendedores.add(vendedor);
		}
		return vendedores;
	}

	public String registrarVendedor(Vendedor vendedor) {
		// Agrego los datos del registro (nombreColumna/Valor)

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("nombres", vendedor.getNombres());
		map.addValue("telefono", vendedor.getTelefono());
		map.addValue("correo", vendedor.getCorreo());
		map.addValue("fechaNacimiento", vendedor.getFechaDeNacimiento());
		map.addValue("documento", vendedor.getDocumento());

		// Armar la sentencia de actualización debase de datos
		String query = "INSERT INTO VENDEDOR(nombres,telefono,correo,fechaNacimiento,documento) VALUES(:nombres,:telefono,:correo,:fechaNacimiento,:documento)";

		// Ejecutar la sentencia
		int result = 0;
		try {
			result = dataMgr.executeDml(query, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Si hubieron filas afectadas es por que si hubo registro, en caso
		// contrario muestra el mensaje
		// de error.
		return (result == 1) ? "Registro exitoso" : "Error en el sistema. Por favor contacte al administrador.";
	}

	public Vendedor obtenerVendedorPorId(long idVendedor) {
		// Lista para retornar con los datos
		Vendedor vendedor = new Vendedor();

		// Consulta para realizar en base de datos
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("codigo", idVendedor);
		SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM VENDEDOR WHERE codigo = :codigo", map);

		// Consulto si la categoria existe
		if (sqlRowSet.next()) {
			// Almaceno los datos de la categoria
			vendedor.setCodigo(sqlRowSet.getInt("codigo"));
			vendedor.setNombres(sqlRowSet.getString("nombres"));
			vendedor.setDocumento(sqlRowSet.getString("documento"));
			vendedor.setFechaDeNacimiento(sqlRowSet.getDate("fechaNacimiento"));
			vendedor.setTelefono(sqlRowSet.getString("telefono"));
			vendedor.setCorreo(sqlRowSet.getString("correo"));
		}

		// Retorna la categoria desde base de datos
		return vendedor;
	}

	public String editarVendedor(Vendedor vendedor) {
		// Agrego los datos del registro (nombreColumna/Valor)

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("codigo", vendedor.getCodigo());
		map.addValue("nombres", vendedor.getNombres());
		map.addValue("telefono", vendedor.getTelefono());
		map.addValue("correo", vendedor.getCorreo());
		map.addValue("fechaNacimiento", vendedor.getFechaDeNacimiento());
		map.addValue("documento", vendedor.getDocumento());

		// Armar la sentencia de actualización debase de datos
		String query = "UPDATE VENDEDOR SET nombres = :nombres, telefono = :telefono,correo = :correo ,fechaNacimiento = :fechaNacimiento ,documento = :documento  WHERE codigo = :codigo";

		// Ejecutar la sentencia
		int result = 0;
		try {
			result = dataMgr.executeDml(query, map);
		} catch (Exception e) {
			new Exception();
		}
		// Si hubieron filas afectadas es por que si hubo actualizacion, en caso
		// contrario muestra el
		// mensaje
		// de error.
		return (result == 1) ? "Actualizacion exitosa" : "Error en el sistema. Por favor contacte al administrador.";
	}

	public String eliminarVendedor(Vendedor vendedor) {
		// Agrego los datos de la eliminación (nombreColumna/Valor)
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("codigo", vendedor.getCodigo());

		// Armar la sentencia de actualización debase de datos
		String query = "DELETE FROM VENDEDOR WHERE codigo = :codigo";

		// Ejecutar la sentencia
		int result = 0;
		try {
			result = dataMgr.executeDml(query, map);
		} catch (Exception e) {
			new Exception();
		}
		// Si hubieron filas afectadas es por que si hubo registro, en caso
		// contrario muestra el mensaje
		// de error.
		return (result == 1) ? "Eliminacion exitosa" : "Error en el sistema. Por favor contacte al administrador.";
	}

	public Map<Integer, String> getMapaDeVendedores() {
		// Lista para retornar con los datos
		Map<Integer, String> vendendores = new HashMap<Integer, String>();

		// Consulta para realizar en base de datos
		SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM VENDEDOR ORDER BY codigo ASC ");

		// Recorre cada registro obtenido de base de datos
		while (sqlRowSet.next()) {
			vendendores.put(sqlRowSet.getInt("codigo"), sqlRowSet.getString("nombres"));
		}

		// Retorna todos las categorias desde base de datos
		return vendendores;
	}

	public Object getCantidadRegistros() {
		return getVendedores().size();
	}

}
