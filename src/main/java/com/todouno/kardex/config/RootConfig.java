package com.todouno.kardex.config;

import org.springframework.context.annotation.ComponentScan;

/**
 * Clase que permite escanear toda la configuraci�n existente en el paquete actual.
 * @author ufps
 *
 */
@ComponentScan(basePackages = { "com.todouno.kardex.config.*" })	
public class RootConfig {
}
