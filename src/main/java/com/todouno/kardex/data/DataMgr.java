package com.todouno.kardex.data;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.todouno.kardex.dto.ResultDB;

public class DataMgr {

  private DataSource dataSource;
  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "org.h2.Driver";
  static final String DB_URL = "jdbc:h2:~/kardex";

  public DataMgr() {
    
    try {
      Class.forName(JDBC_DRIVER);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    dataSource = new DriverManagerDataSource(DB_URL);
    executeDml("DROP ALL OBJECTS");
    executeDml("CREATE TABLE IF NOT EXISTS TIPOPRODUCTO ( codigo int auto_increment primary key NOT NULL, nombre VARCHAR(1000) NOT NULL , descripcion VARCHAR(1000) NOT NULL) ");
    executeDml("INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('Camisetas','En esta seccion se ingresan productos como: Camiseta super man, Camiseta Batman, entre otros')");
    executeDml("INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('Vasos','En esta seccion se ingresan productos como: Vaso Holk, Vaso mujer maravilla, entre otros')");
    executeDml("INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('Comics','En esta seccion se ingresan productos como: Comics archie, Comics los gemelos fantasticos, entre otros')");
    executeDml("INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('Jueguetes','En esta seccion se ingresan productos como: Figura Holk, Figura superman, entre otros')");
    executeDml("INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('Accesorios','En esta seccion se ingresan productos como: Manilla Thor, Cadena superman, entre otros')");
    
    executeDml("CREATE TABLE IF NOT EXISTS PRODUCTO ( codigo int auto_increment primary key NOT NULL, nombre VARCHAR(1000) NOT NULL , stock int  NOT NULL ,precio int  NOT NULL , tipoProducto int NOT NULL, foreign key (tipoProducto) references TIPOPRODUCTO(codigo) ) ");
    
    executeDml("INSERT INTO  PRODUCTO(nombre, stock, precio, tipoProducto) VALUES('Vaso holk','12','13400',2)");
    executeDml("INSERT INTO  PRODUCTO(nombre, stock, precio, tipoProducto) VALUES('Vaso spiderman','20','12300',2)");
    executeDml("INSERT INTO  PRODUCTO(nombre, stock, precio, tipoProducto) VALUES('Camiseta thor','10','72600',1)");
    executeDml("INSERT INTO  PRODUCTO(nombre, stock, precio, tipoProducto) VALUES('Camiseta batman','30','34950',1)");
    executeDml("INSERT INTO  PRODUCTO(nombre, stock, precio, tipoProducto) VALUES('Camiseta archie','0','12000',1)");
    
    executeDml("CREATE TABLE IF NOT EXISTS VENDEDOR ( codigo int auto_increment primary key NOT NULL, nombres VARCHAR(1000) NOT NULL , documento VARCHAR(1000) NOT NULL , telefono VARCHAR(1000) NOT NULL, correo VARCHAR(1000) NOT NULL, fechaNacimiento DATE NOT NULL) ");
    executeDml("INSERT INTO  VENDEDOR(nombres, documento, telefono, correo, fechaNacimiento ) VALUES('Julian Hernesto Martinez Bedoya','10907897654','3208974523','Julian@gmail.com','1994-02-02')");
    executeDml("CREATE TABLE IF NOT EXISTS FACTURA ( codigo int auto_increment primary key NOT NULL, fecha DATE NOT NULL , totalFactura DOUBLE NOT NULL, total INT NOT NULL, iva DOUBLE NOT NULL, vendedor int NOT NULL, foreign key (vendedor) references VENDEDOR(codigo) ) ");
    executeDml("CREATE TABLE IF NOT EXISTS DETALLE ( codigo int auto_increment primary key NOT NULL, cantidad VARCHAR(1000) NOT NULL , producto int NOT NULL, factura int NOT NULL, foreign key (producto) references PRODUCTO(codigo), foreign key (factura) references FACTURA(codigo) ) ");
    executeDml("CREATE TABLE IF NOT EXISTS USUARIO ( correo VARCHAR(1000) NOT NULL primary key, contrasena VARCHAR(1000) NOT NULL) ");
    executeDml("INSERT INTO  USUARIO(correo,contrasena) VALUES('eygarcia@softcaribbean.com','1234')");
    //executeDml("INSERT INTO  USUARIO(correo,contrasena) VALUES('smunevar@softcaribbean.com','1234')");
  }
 
  
  /**
   * This method implements SELECT query execution logic without parameters in Database System using
   * Spring-JDBC.
   * 
   * @param query Text that represents query to be executed.
   * @return Set of rows returned by the query.
   * @throws AppException If there is any problem in the execution.
   */
  public SqlRowSet executeQuery(String query) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(query, mapSqlParameterSource);
      return sqlRowSet;
    } catch (Exception e) {
      e.printStackTrace();
    }


    return null;
  }

  /**
   * This method implements SELECT query execution logic in Database System using Spring-JDBC.
   * 
   * @param query Text that represents query to be executed.
   * @param parameterMap Object containing all parameters required to bind SQL variables.
   * @return Set of rows returned by the query.
   * @throws AppException If there is any problem in the execution.
   */
  public SqlRowSet executeQuery(String query, MapSqlParameterSource parameterMap) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(query, parameterMap);

      return sqlRowSet;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * This method implements INSERT/UPDATE/DELETE query execution logic without parameters in
   * Database System using Spring-JDBC.
   * 
   * @param query Text that represents query to be executed.
   * @return the number of rows affected.
   * @throws AppException If there is any problem in the execution.
   */
  public int executeDml(String query) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);

      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      int affectedRows = namedParameterJdbcTemplate.update(query, mapSqlParameterSource);

      return affectedRows;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
  }

  /**
   * This method implements INSERT/UPDATE/DELETE query execution logic in Database System using
   * Spring-JDBC.
   * 
   * @param query Text that represents DML to be executed.
   * @param parameterMap Object containing all parameters required to bind SQL variables.
   * @return the number of rows affected.
   * @throws AppException If there is any problem in the execution.
   */
  public int executeDml(String query, MapSqlParameterSource parameterMap) {
    try {
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(dataSource);
      KeyHolder keyHolder = new GeneratedKeyHolder();
      int affectedRows = namedParameterJdbcTemplate.update(query, parameterMap, keyHolder);

      return affectedRows;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;
  }

  public ResultDB executeDmlWithKey(String query, MapSqlParameterSource parameterMap) {

    ResultDB resultDB = new ResultDB();
    NamedParameterJdbcTemplate namedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(dataSource);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    int affectedRows = namedParameterJdbcTemplate.update(query, parameterMap, keyHolder);
    Long generatedId = keyHolder.getKey().longValue();
    resultDB.setResult(affectedRows);
    resultDB.setKey(generatedId);
    return resultDB;
  }

  /**
   * This method set a dataSource.
   * 
   * @param dataSource for set
   */
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
}

}
