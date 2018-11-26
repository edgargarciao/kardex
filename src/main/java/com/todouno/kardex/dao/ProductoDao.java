package com.todouno.kardex.dao;

import java.util.LinkedList;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.todouno.kardex.data.DataMgr;
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.TipoProducto;

public class ProductoDao {

  private DataMgr dataMgr;
  private TipoProductoDao tipoProductoDao;

  public ProductoDao() {
    dataMgr = new DataMgr();
    tipoProductoDao = new TipoProductoDao();
  }


  public List<Producto> getProductos() {
    List<Producto> productos = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = dataMgr.executeQuery(" SELECT * FROM PRODUCTO");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Producto producto = new Producto();

      producto.setCodigo(sqlRowSet.getInt("codigo"));
      producto.setNombre(sqlRowSet.getString("nombre"));
      producto.setTipoProducto(tipoProductoDao.obtenerTipoProductoPorId(sqlRowSet.getInt("tipoProducto")));
      producto.setCantidad(sqlRowSet.getInt("stock"));
      producto.setPrecio(sqlRowSet.getInt("precio"));

      // Guarda el registro para ser retornado
      productos.add(producto);
    }
    return productos;
  }

  public String registrarProducto(Producto producto) {
    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();    
    map.addValue("nombre", producto.getNombre());
    map.addValue("stock", producto.getCantidad());
    map.addValue("precio", producto.getPrecio());
    map.addValue("tipoProducto", producto.getTipoProducto().getCodigo());
    

    // Armar la sentencia de actualización debase de datos
    String query = "INSERT INTO PRODUCTO(nombre,stock,precio,tipoProducto) VALUES(:nombre,:stock,:precio,:tipoProducto)";

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

  public Producto obtenerProductoPorId(long idProducto) {
    // Lista para retornar con los datos
    Producto producto = new Producto();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("codigo", idProducto);
    SqlRowSet sqlRowSet =
        dataMgr.executeQuery(" SELECT * FROM PRODUCTO WHERE codigo = :codigo", map);

    // Consulto si la categoria existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de la categoria
      producto.setCodigo(sqlRowSet.getInt("codigo"));
      producto.setNombre(sqlRowSet.getString("nombre"));
      producto.setCantidad(sqlRowSet.getInt("stock"));
      producto.setPrecio(sqlRowSet.getInt("precio"));
      producto.setTipoProducto(tipoProductoDao.obtenerTipoProductoPorId(sqlRowSet.getInt("tipoProducto")));
    }

    // Retorna la categoria desde base de datos
    return producto;
  }

  public String editarProducto(Producto producto) {
    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();    
    map.addValue("codigo", producto.getCodigo());
    map.addValue("nombre", producto.getNombre());
    map.addValue("stock", producto.getCantidad());
    map.addValue("precio", producto.getPrecio());
    map.addValue("tipoProducto", producto.getTipoProducto().getCodigo());

    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE PRODUCTO SET nombre = :nombre, stock = :stock, precio = :precio, tipoProducto = :tipoProducto WHERE codigo = :codigo";

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

  public String eliminarProducto(Producto producto) {
    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("codigo", producto.getCodigo());

    // Armar la sentencia de actualización debase de datos
    String query = "DELETE FROM PRODUCTO WHERE codigo = :codigo";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = dataMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo eliminacion, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Eliminacion exitosa"
        : "Error en el sistema. Por favor contacte al administrador.";
  }

}
