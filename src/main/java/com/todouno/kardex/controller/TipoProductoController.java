package com.todouno.kardex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.todouno.kardex.dao.TipoProductoDao;
import com.todouno.kardex.dto.TipoProducto;

/**
 * Clase controladora de tipos de productos
 * 
 * @author edgar
 *
 */
@Controller
public class TipoProductoController {

  private TipoProductoDao tipoProductoDao;

  public TipoProductoController() {
    tipoProductoDao = new TipoProductoDao();
  }

  /**
   * Método que retorna una pagina con todas los tipos de productos en el sistema.
   * 
   * @return La página principal de tipos de productos.
   */
  @GetMapping("/tipoProductos") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("tipoProductos", tipoProductoDao.getTipoProductos());
    return "Administrador/TipoProducto/TipoProductos"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * 
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("tipoProducto")
  public TipoProducto setUpUserForm() {
    return new TipoProducto();
  }

  /**
   * Método que retorna una pagina para realizar el registro de un tipo de producto.
   * 
   * @return La página de registro de tipos de productos.
   */
  @GetMapping("/registrarTipoProducto") // Base
  public String registrarTipoProducto() {
    return "Administrador/TipoProducto/RegistrarTipoProducto"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite guardar un tipo de producto
   * 
   * @param tipoProducto Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarTipoProducto")
  public String registrarTipoProducto(@ModelAttribute("tipoProducto") TipoProducto tipoProducto,
      Model model) {

    // Consulta si tiene todos los campos llenos
    if (tipoProducto.isValidoParaRegistrar()) {

      String mensaje = tipoProductoDao.registrarTipoDeProducto(tipoProducto);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Tipo de producto registrado con éxito.");
        model.addAttribute("tipoProductos", tipoProductoDao.getTipoProductos());
        return "Administrador/TipoProducto/TipoProductos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/TipoProducto/RegistrarTipoProducto"; // Nombre del archivo jsp
      }

    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/TipoProducto/RegistrarTipoProducto"; // Nombre del archivo jsp
    }
  }

  /**
   * Método que obtiene la pagina de actualizar un tipo de producto dado un ID.
   * 
   * @param idActualizar Identificador del tipo de producto
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información del tipo de producto.
   */
  @GetMapping(value = "/actualizarTipoProducto")
  public String actualizarTipoProducto(@RequestParam("id") long idTipoProducto, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idTipoProducto <= 0) {
      model.addAttribute("tipoProductos", tipoProductoDao.getTipoProductos());
      return "Administrador/TipoProducto/TipoProductos"; // Nombre del archivo jsp
    }
    TipoProducto tipoProducto = tipoProductoDao.obtenerTipoProductoPorId(idTipoProducto);
    model.addAttribute("tipoProducto", tipoProducto);
    return "Administrador/TipoProducto/ActualizarTipoProductos"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite editar un tipo de producto.
   * 
   * @param tipoProducto Objeto con la información a editar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarTipoProducto")
  public String editarTipoProducto(@ModelAttribute("tipoProducto") TipoProducto tipoProducto,
      Model model) {

    // Consulta si tiene todos los campos llenos
    if (tipoProducto.isValidoParaActualizar()) {

      String mensaje = tipoProductoDao.editarTipoProducto(tipoProducto);

      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "Tipo de producto actualizado con éxito.");
        model.addAttribute("tipoProductos", tipoProductoDao.getTipoProductos());
        return "Administrador/TipoProducto/TipoProductos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return actualizarTipoProducto(tipoProducto.getCodigo(), model);
      }
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return actualizarTipoProducto(tipoProducto.getCodigo(), model);
    }
  }

  /**
   * Método que obtiene la pagina de eliminar un tipo de producto dado un ID.
   * 
   * @param idTipoProducto Identificador del tipo de producto
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información del tipo de producto.
   */
  @GetMapping(value = "/eliminarTipoProducto")
  public String eliminarTipoProducto(@RequestParam("id") long idTipoProducto, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idTipoProducto <= 0) {
      model.addAttribute("tipoProductos", tipoProductoDao.getTipoProductos());
      return "Administrador/TipoProducto/TipoProductos"; // Nombre del archivo jsp
    }
    TipoProducto tipoProducto = tipoProductoDao.obtenerTipoProductoPorId(idTipoProducto);
    model.addAttribute("tipoProducto", tipoProducto);
    return "Administrador/TipoProducto/EliminarTipoProductos"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar un tipo de producto.
   * 
   * @param tipoProducto Objeto con la información a eliminar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/borrarTipoProducto")
  public String borrarTipoProducto(@ModelAttribute("tipoProducto") TipoProducto tipoProducto, Model model) {
    
      String mensaje = tipoProductoDao.eliminarTipoProducto(tipoProducto);
      if (mensaje.equals("Eliminacion exitosa")) {
        model.addAttribute("result", "Tipo de producto eliminado con éxito.");
        model.addAttribute("tipoProductos", tipoProductoDao.getTipoProductos());
        return "Administrador/TipoProducto/TipoProductos"; // Nombre del archivo jsp        
      } else {
        model.addAttribute("wrong", mensaje);
        return eliminarTipoProducto(tipoProducto.getCodigo(), model);
      }
  }
}
