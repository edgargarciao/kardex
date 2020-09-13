package com.todouno.kardex.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todouno.kardex.dao.ProductoDao;
import com.todouno.kardex.dao.TipoProductoDao;
import com.todouno.kardex.dao.VendedorDao;
import com.todouno.kardex.dao.VentaDao;
import com.todouno.kardex.dto.Factura;
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.TipoProducto;

/**
 * Clase controladora de tipos de productos
 * 
 * @author edgar
 *
 */
@Controller
public class VentaController {

	private VentaDao ventaDao;
	private VendedorDao vendedorDao;
	private ProductoDao productoDao;

	public VentaController() {
		ventaDao = new VentaDao();
		vendedorDao = new VendedorDao();
		productoDao = new ProductoDao();

	}

	/**
	 * Metodo que retorna una pagina con todas las ventas en el sistema.
	 * 
	 * @return La pagina principal de ventas.
	 */
	@GetMapping("/ventas") // Base
	public String index(Model model) {
		// Cargamos los contenidos para poder mostrarlas en el cuadro.
		model.addAttribute("ventas", ventaDao.getVentas());
		return "Administrador/Venta/Ventas"; // Nombre del archivo jsp
	}

	/**
	 * Modelo con el que se realizara el formulario
	 * 
	 * @return Un objeto para ser llenado desde el archivo .JSP
	 */
	@ModelAttribute("venta")
	public Factura setUpUserForm() {
		return new Factura();
	}

	/**
	 * Metodo que retorna una pagina para realizar el registro de un tipo de
	 * producto.
	 * 
	 * @return La pagina de registro de tipos de productos.
	 */
	@GetMapping("/registrarVenta") // Base
	public String registrarVenta(Model model) {
		model.addAttribute("productos", productoDao.getMapaDeProductos());
		model.addAttribute("precioproductos", productoDao.getMapaDePrecioProductos());
		model.addAttribute("cantidadproductos", productoDao.getMapaDeCantidadProductos());
		model.addAttribute("vendedores", vendedorDao.getMapaDeVendedores());
		return "Administrador/Venta/RegistrarVenta"; // Nombre del archivo jsp
	}

	@PostMapping(value = "/servicios/guardarVenta")
	public @ResponseBody ResponseEntity<String> guardarVenta(@RequestBody Factura venta) {

		// Consulta si tiene todos los campos llenos
		if (venta.isValidoParaRegistrar()) {

			String mensaje = ventaDao.registrarVenta(venta);

			if (mensaje.equals("Registro exitoso")) {
				return new ResponseEntity<String>("REGISTRO EXITOSO", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("REGISTRO NO EXITOSO", HttpStatus.OK);
			}
			//
		} else {
			return new ResponseEntity<String>("CAMPOS INVALIDOS", HttpStatus.OK);
		}

	}
}
