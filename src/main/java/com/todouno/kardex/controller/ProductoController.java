package com.todouno.kardex.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todouno.kardex.constantes.Mensajes;
import com.todouno.kardex.dao.ProductoDao;
import com.todouno.kardex.dao.TipoProductoDao;
import com.todouno.kardex.dto.Producto;
import com.todouno.kardex.dto.TipoProducto;

/**
 * Clase controladora de tipos de productos
 * 
 * @author edgar
 *
 */
@Controller
public class ProductoController {

  private ProductoDao productoDao;
  private TipoProductoDao tipoProductoDao;

  public ProductoController() {
    productoDao = new ProductoDao();
    tipoProductoDao = new TipoProductoDao();
  }

  /**
   * Metodo que retorna una pagina con todas los productos en el sistema.
   * 
   * @return La pagina principal de productos.
   */
  @GetMapping("/productos") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("productos", productoDao.getProductos());
    return "Administrador/Producto/Productos"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * 
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("producto")
  public Producto setUpUserForm() {
    return new Producto();
  }

  /**
   * Método que retorna una pagina para realizar el registro de un tipo de producto.
   * 
   * @return La pagina de registro de tipos de productos.
   */
  @GetMapping("/registrarProducto") // Base
  public String registrarProducto(Model model) {
    model.addAttribute("tipoProductos", tipoProductoDao.getMapaDeTipoDeProductos());
    return "Administrador/Producto/RegistrarProducto"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite guardar un producto
   * 
   * @param tipoProducto Objeto con la informacion a guardar
   * @param model Modelo con la informacion necesaria para transportar a los archivos .JSP
   * @return La pagina a donde debe redireccionar despues de la accion.
   */
  @PostMapping(value = "/guardarProducto")
  public String registrarProducto(@ModelAttribute("producto") Producto producto,
      Model model) {

    // Consulta si tiene todos los campos llenos
    if (producto.isValidoParaRegistrar()) {

      String mensaje = productoDao.registrarProducto(producto);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", String.format(Mensajes.MENSAJEEXITO, "Producto","registrado"));
        model.addAttribute("productos", productoDao.getProductos());
        return "Administrador/Producto/Productos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        model.addAttribute("tipoProductos", tipoProductoDao.getMapaDeTipoDeProductos());
        return "Administrador/Producto/RegistrarProducto"; // Nombre del archivo jsp
      }

    } else {
      model.addAttribute("wrong", Mensajes.LLENARTODOSLOSCAMPOS);
      model.addAttribute("tipoProductos", tipoProductoDao.getMapaDeTipoDeProductos());
      return "Administrador/Producto/RegistrarProducto"; // Nombre del archivo jsp
    }
  }

  /**
   * Metodo que obtiene la pagina de actualizar un producto dado un ID.
   * 
   * @param idActualizar Identificador del producto
   * @param model Objeto para enviar informacion a los archivos .JSP
   * @return La pagina con la informacion del producto.
   */
  @GetMapping(value = "/actualizarProducto")
  public String actualizarProducto(@RequestParam("id") long idProducto, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idProducto <= 0) {
      return index(model);
    }
    Producto producto = productoDao.obtenerProductoPorId(idProducto);
    model.addAttribute("producto", producto);
    
    model.addAttribute("idTipoProductoSeleccionado", producto.getTipoProducto().getCodigo());
    model.addAttribute("nombreTipoProductoSeleccionado", producto.getTipoProducto().getNombre());
    Map<Integer, String> tipoProductos = tipoProductoDao.getMapaDeTipoDeProductos();
    tipoProductos.remove(producto.getTipoProducto().getCodigo());
    model.addAttribute("tipoProductos", tipoProductos);
    
    return "Administrador/Producto/ActualizarProductos"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite editar un producto.
   * 
   * @param producto Objeto con la informacion a editar.
   * @param model Modelo con la informacion necesaria para transportar a los archivos .JSP
   * @return La pagina a donde debe redireccionar despues de la accion.
   */
  @PostMapping(value = "/editarProducto")
  public String editarProducto(@ModelAttribute("producto") Producto producto,
      Model model) {

    // Consulta si tiene todos los campos llenos
    if (producto.isValidoParaActualizar()) {

      String mensaje = productoDao.editarProducto(producto);

      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", String.format(Mensajes.MENSAJEEXITO, "Producto","actualizado"));
        model.addAttribute("productos", productoDao.getProductos());
        return "Administrador/Producto/Productos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return actualizarProducto(producto.getCodigo(), model);
      }
    } else {
      model.addAttribute("wrong", Mensajes.LLENARTODOSLOSCAMPOS);
      return actualizarProducto(producto.getCodigo(), model);
    }
  }

  /**
   * Metodo que obtiene la pagina de eliminar un producto dado un ID.
   * 
   * @param idTipoProducto Identificador del producto
   * @param model Objeto para enviar informacion a los archivos .JSP
   * @return La pagina con la informacion del producto.
   */
  @GetMapping(value = "/eliminarProducto")
  public String eliminarProducto(@RequestParam("id") long idProducto, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idProducto <= 0) {
      return index(model);
    }
    Producto producto = productoDao.obtenerProductoPorId(idProducto);
    model.addAttribute("producto", producto);
    return "Administrador/Producto/EliminarProductos"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar un producto.
   * 
   * @param tipoProducto Objeto con la informacion a eliminar.
   * @param model Modelo con la informacion necesaria para transportar a los archivos .JSP
   * @return La pagina a donde debe redireccionar despues de la accion.
   */
  @PostMapping(value = "/borrarProducto")
  public String borrarProducto(@ModelAttribute("producto") Producto producto, Model model) {
    
      String mensaje = productoDao.eliminarProducto(producto);
      if (mensaje.equals("Eliminacion exitosa")) {
        model.addAttribute("result", String.format(Mensajes.MENSAJEEXITO, "Producto","eliminado"));
        model.addAttribute("productos", productoDao.getProductos());
        return "Administrador/Producto/Productos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return eliminarProducto(producto.getCodigo(), model);
      }
  }
}
