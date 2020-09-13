package com.todouno.kardex.dao;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.todouno.kardex.data.DataMgr;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class LoginDaoTest {

	@Mock
	private DataMgr dataMgr;
	
	@InjectMocks
	private LoginDao loginDao;
	
	@Test
	public void whenCallauthenticateThenReturnTheInfo() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(true);
		
		// Act
		String result = loginDao.authenticate("", "");
		
		// Assert
		assertEquals(result, "Administrador");
		assertNotNull(result);
	}
	
	@Test
	public void whenCallauthenticateThenReturnTheInfoEmpty() {
		// Arrange
		SqlRowSet sqlRowSet =Mockito.mock(SqlRowSet.class);
		Mockito.when(dataMgr.executeQuery(Mockito.anyString(), Mockito.any())).thenReturn(sqlRowSet);
		Mockito.when(sqlRowSet.next()).thenReturn(false);
		
		// Act
		String result = loginDao.authenticate("", "");
		
		// Assert
		assertEquals(result, "");
		assertNotNull(result);
	}

}
