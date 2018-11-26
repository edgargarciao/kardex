package com.todouno.kardex.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.todouno.kardex.data.DataMgr;
import com.todouno.kardex.dto.TipoProducto;

public class TipoProductoDao {

  private DataMgr dataMgr;

  public TipoProductoDao() {
    dataMgr = new DataMgr();
  }


  public List<TipoProducto> getTipoProductos() {
    List<TipoProducto> tiposDeProductos = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM TIPOPRODUCTO");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      TipoProducto tipoProducto = new TipoProducto();

      tipoProducto.setCodigo(sqlRowSet.getInt("codigo"));
      tipoProducto.setNombre(sqlRowSet.getString("nombre"));
      tipoProducto.setDescripcion(sqlRowSet.getString("descripcion"));

      // Guarda el registro para ser retornado
      tiposDeProductos.add(tipoProducto);
    }
    return tiposDeProductos;
  }

  public String registrarTipoDeProducto(TipoProducto tipoProducto) {
    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("nombre", tipoProducto.getNombre());
    map.addValue("descripcion", tipoProducto.getDescripcion());

    // Armar la sentencia de actualización debase de datos
    String query = "INSERT INTO TIPOPRODUCTO(nombre,descripcion) VALUES(:nombre,:descripcion)";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = dataMgr.executeDml(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Registro exitoso"
        : "Error en el sistema. Por favor contacte al administrador.";
  }

  public TipoProducto obtenerTipoProductoPorId(long idTipoProducto) {
    // Lista para retornar con los datos
    TipoProducto tipoProducto = new TipoProducto();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("codigo", idTipoProducto);
    SqlRowSet sqlRowSet =
        dataMgr.executeQuery(" SELECT * FROM TIPOPRODUCTO WHERE codigo = :codigo", map);

    // Consulto si la categoria existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de la categoria
      tipoProducto.setCodigo(sqlRowSet.getInt("codigo"));
      tipoProducto.setNombre(sqlRowSet.getString("nombre"));
      tipoProducto.setDescripcion(sqlRowSet.getString("descripcion"));
    }

    // Retorna la categoria desde base de datos
    return tipoProducto;
  }

  public String editarTipoProducto(TipoProducto tipoProducto) {
    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("codigo", tipoProducto.getCodigo());
    map.addValue("nombre", tipoProducto.getNombre());
    map.addValue("descripcion", tipoProducto.getDescripcion());

    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE TIPOPRODUCTO SET nombre = :nombre, descripcion = :descripcion WHERE codigo = :codigo";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = dataMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo actualizacion, en caso contrario muestra el
    // mensaje
    // de error.
    return (result == 1) ? "Actualizacion exitosa"
        : "Error en el sistema. Por favor contacte al administrador.";
  }

  public String eliminarTipoProducto(TipoProducto tipoProducto) {
    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("codigo", tipoProducto.getCodigo());

    // Armar la sentencia de actualización debase de datos
    String query = "DELETE FROM TIPOPRODUCTO WHERE codigo = :codigo";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = dataMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Eliminacion exitosa"
        : "Error en el sistema. Por favor contacte al administrador.";
  }


  public Map<Integer, String> getMapaDeTipoDeProductos() {
    // Lista para retornar con los datos
    Map<Integer, String> tipoProductos = new HashMap<Integer, String>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM TIPOPRODUCTO ORDER BY codigo ASC ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      tipoProductos.put(sqlRowSet.getInt("codigo"), sqlRowSet.getString("nombre"));
    }

    // Retorna todos las categorias desde base de datos
    return tipoProductos;
  }

}
