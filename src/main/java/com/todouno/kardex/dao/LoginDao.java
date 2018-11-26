package com.todouno.kardex.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.todouno.kardex.data.DataMgr;

public class LoginDao {

	private DataMgr dataMgr;

	public LoginDao() {
		dataMgr = new DataMgr();
	}

	public String authenticate(String correoEmpresarial, String contrasena) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("correo", correoEmpresarial);
		mapSqlParameterSource.addValue("contrasena", contrasena);
		SqlRowSet sqlRowSet = dataMgr.executeQuery(
				"SELECT correo FROM usuario WHERE correo = :correo  AND contrasena = :contrasena",
				mapSqlParameterSource);
		return (sqlRowSet.next() ? "Administrador" : "");
	}

}
