package com.todouno.kardex.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  public VentaController() {
    ventaDao = new VentaDao();
    vendedorDao = new VendedorDao();

  }

  /**
   * Método que retorna una pagina con todas las ventas en el sistema.
   * 
   * @return La página principal de ventas.
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
   * Método que retorna una pagina para realizar el registro de un tipo de producto.
   * 
   * @return La página de registro de tipos de productos.
   */
  @GetMapping("/registrarVenta") // Base
  public String registrarProducto(Model model) {
    model.addAttribute("vendedores", vendedorDao.getMapaDeVendedores());
    return "Administrador/Venta/RegistrarVenta"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite guardar un producto
   * 
   * @param tipoProducto Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.

  @PostMapping(value = "/guardarProducto")
  public String registrarProducto(@ModelAttribute("producto") Producto producto,
      Model model) {

    // Consulta si tiene todos los campos llenos
    if (producto.isValidoParaRegistrar()) {

      String mensaje = productoDao.registrarProducto(producto);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Producto registrado con éxito.");
        model.addAttribute("productos", productoDao.getProductos());
        return "Administrador/Producto/Productos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        model.addAttribute("tipoProductos", tipoProductoDao.getMapaDeTipoDeProductos());
        return "Administrador/Producto/RegistrarProducto"; // Nombre del archivo jsp
      }

    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      model.addAttribute("tipoProductos", tipoProductoDao.getMapaDeTipoDeProductos());
      return "Administrador/Producto/RegistrarProducto"; // Nombre del archivo jsp
    }
  }   */

}
