package com.todouno.kardex.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.todouno.kardex.dto.ResultDB;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DataMgrTest {

	@Mock
	private DataSource dataSource;
	
	@InjectMocks
	private DataMgr dataMgr;
	
	@Test
	public void whenExecuteQueryWithoutMapThenReturnException() {
		
		// Act
		SqlRowSet sqlRowSet = dataMgr.executeQuery("");
		
		// Arrange
		assertNull(sqlRowSet);
	}
	
	@Test
	public void whenExecuteQueryWithMapThenReturnException() {
		
		// Act
		SqlRowSet sqlRowSet = dataMgr.executeQuery("", null);
		
		// Arrange
		assertNull(sqlRowSet);
	}
	
	
	@Test
	public void whenExecuteDmlWithoutMapThenReturnException() {
		
		// Act
		int rows = dataMgr.executeDml("");
		
		// Arrange
		assertEquals(-1, rows);
	}

	
	@Test
	public void whenExecuteDmlWithMapThenReturnTheNumberOfRowsDeleteds() {		
		//Arrange
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("telefono", "3208974523");
		
	    try {
	        Class.forName(DataMgr.JDBC_DRIVER);
	      } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	      }
	    dataSource = new DriverManagerDataSource(dataMgr.DB_URL);
	    dataMgr.setDataSource(dataSource);
	    dataMgr.executeDml("DROP ALL OBJECTS");
	    dataMgr.executeDml("CREATE TABLE IF NOT EXISTS VENDEDOR ( codigo int auto_increment primary key NOT NULL, nombres VARCHAR(1000) NOT NULL , documento VARCHAR(1000) NOT NULL , telefono VARCHAR(1000) NOT NULL, correo VARCHAR(1000) NOT NULL, fechaNacimiento DATE NOT NULL) ");
	    dataMgr.executeDml("INSERT INTO  VENDEDOR(nombres, documento, telefono, correo, fechaNacimiento ) VALUES('Julian Hernesto Martinez Bedoya','10907897654','3208974523','Julian@gmail.com','1994-02-02')");
	    
		
		// Act
		int rows = dataMgr.executeDml("DELETE FROM VENDEDOR WHERE telefono = :telefono",map );
		
		// Arrange
		assertEquals(1, rows);
	}
	
	
	@Test
	public void whenExecuteDmlWithMapThenReturnException() {
		
		// Act
		int rows = dataMgr.executeDml("", null);
		
		// Arrange
		assertEquals(0, rows);
	}
	
	@Test
	public void whenExecuteExecuteDmlWithKeyThenReturnTheNumberOfRowsDeleteds() {		
		//Arrange
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("telefono", "3208974523");
		
	    try {
	        Class.forName(DataMgr.JDBC_DRIVER);
	      } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	      }
	    dataSource = new DriverManagerDataSource(dataMgr.DB_URL);
	    dataMgr.setDataSource(dataSource);
	    dataMgr.executeDml("DROP ALL OBJECTS");
	    dataMgr.executeDml("CREATE TABLE IF NOT EXISTS VENDEDOR ( codigo int auto_increment primary key NOT NULL, nombres VARCHAR(1000) NOT NULL , documento VARCHAR(1000) NOT NULL , telefono VARCHAR(1000) NOT NULL, correo VARCHAR(1000) NOT NULL, fechaNacimiento DATE NOT NULL) ");
	    
	    
		// Act
	    ResultDB resultDB = dataMgr.executeDmlWithKey("INSERT INTO  VENDEDOR(nombres, documento, telefono, correo, fechaNacimiento ) VALUES('Julian Hernesto Martinez Bedoya','10907897654','3208974523','Julian@gmail.com','1994-02-02')",map );
		
		// Arrange
	    assertNotNull(resultDB);
		assertEquals(1, resultDB.getKey());
	}
}
