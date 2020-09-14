package com.todouno.kardex.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class JwtUtilTest {

	@InjectMocks
	private JwtUtil jwtUtil;
	
	@Test
	public void whenCallGetInstanciaThenReturnAInstanceOfObject() {
		// Act
		JwtUtil k = jwtUtil.getInstancia();
		JwtUtil t = jwtUtil.getInstancia();
		
		//Assert
		assertNotNull(k);
		assertNotNull(t);
	}
	
	@Test
	public void whenCallGenerateTokenThenReturnATokenCreated() {
		// Act
		String k = jwtUtil.generateToken("test", "a@gmail.com");		
		
		//Arrange
		assertNotNull(k);
	}
	
	@Test
	public void whenCallParseTokenThenReturnATokenCreated() {
		// Arrange
		String k = jwtUtil.generateToken("test", "a@gmail.com");
		
		//Act
		String l = jwtUtil.parseToken(k);
		
		//Arrange
		assertNotNull(l);
		assertEquals("a@gmail.com", l);
	}
	
	@Test
	public void whenCallParseTokenThenThrowAException() {

		//Act
		String l = jwtUtil.parseToken("sasasas");
		
		//Arrange
		assertNotNull(l);		
	}
	
	@Test
	public void whenCallParseTokenThenThrowAException2() {

		//Act
		String l = jwtUtil.parseToken(null);
		
		//Arrange
		assertNotNull(l);		
	}
}
