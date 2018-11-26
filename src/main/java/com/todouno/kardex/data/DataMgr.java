package com.todouno.kardex.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    executeDml("INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('LACTEOS','En esta sección se ingresan productos como: leches, quesos, entre otros')");
    executeDml("INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('HARINAS','En esta sección se ingresan productos como: Harina normal, Harina precocida, entre otros')");
    executeDml("CREATE TABLE IF NOT EXISTS PRODUCTO ( codigo int auto_increment primary key NOT NULL, nombre VARCHAR(1000) NOT NULL , stock int  NOT NULL ,precio int  NOT NULL , tipoProducto int NOT NULL, foreign key (tipoProducto) references TIPOPRODUCTO(codigo) ) ");
    executeDml("CREATE TABLE IF NOT EXISTS VENDEDOR ( codigo int auto_increment primary key NOT NULL, nombres VARCHAR(1000) NOT NULL , telefono VARCHAR(1000) NOT NULL, correo VARCHAR(1000) NOT NULL, fechaNacimiento DATE NOT NULL) ");
    executeDml("CREATE TABLE IF NOT EXISTS FACTURA ( codigo int auto_increment primary key NOT NULL, fecha DATE NOT NULL , totalFactura INT NOT NULL) ");
    executeDml("CREATE TABLE IF NOT EXISTS DETALLE ( codigo int auto_increment primary key NOT NULL, cantidad VARCHAR(1000) NOT NULL , producto int NOT NULL, factura int NOT NULL, foreign key (producto) references PRODUCTO(codigo), foreign key (factura) references FACTURA(codigo) ) ");
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
  
  
  public static void main(String[] args) {

    DataMgr d = new DataMgr();
    
    
    String url = "jdbc:h2:mem:";

    try (Connection con = DriverManager.getConnection(url);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT 1+1")) {

      if (rs.next()) {

        System.out.println(rs.getInt(1));
      }

      int x = st.executeUpdate("CREATE TABLE TIPOPRODUCTO ( codigo int auto_increment primary key NOT NULL, nombre VARCHAR(1000) NOT NULL , descripcion VARCHAR(1000) NOT NULL) ");
      //int x = st.executeUpdate("CREATE TABLE PRODUCTO");
      x = st.executeUpdate(" INSERT INTO  TIPOPRODUCTO(nombre,descripcion) VALUES('ALGO','TEST')");
      ResultSet rs2 = st.executeQuery("SELECT * FROM TIPOPRODUCTO");
      
      if (rs2.next()) {

        System.out.println(rs2.getInt("codigo"));
        System.out.println(rs2.getString("nombre"));
        System.out.println(rs2.getString("descripcion"));
      }
      
      System.out.println(x);
    } catch (SQLException ex) {

      Logger lgr = Logger.getLogger(DataMgr.class.getName());
      lgr.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }

}
