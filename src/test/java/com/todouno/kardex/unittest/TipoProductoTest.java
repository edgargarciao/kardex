package com.todouno.kardex.unittest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.springframework.ui.Model;

import com.todouno.kardex.controller.TipoProductoController;

@RunWith(JUnit4.class)
public class TipoProductoTest {

	@Mock
	private TipoProductoController tipoProductoController;
	
	@Mock
	private Model model;
	
	@Test
	public void givenTheSystemWithoutTipoProductoWhenCallIndexThenReturnTheModelNull(){
		
		
		tipoProductoController.index(model);
	}
}
