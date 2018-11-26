package com.todouno.kardex.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.todouno.kardex.dao.VendedorDao;
import com.todouno.kardex.dao.VendedorDao;
import com.todouno.kardex.dto.Vendedor;
import com.todouno.kardex.dto.Vendedor;

/**
 * Clase controladora de tipos de productos
 * 
 * @author edgar
 *
 */
@Controller
public class VendedorController {

  private VendedorDao vendedorDao;

  public VendedorController() {
    vendedorDao = new VendedorDao();
  }

  /**
   * Método que retorna una pagina con todas los vendedores en el sistema.
   * 
   * @return La página principal de vendedores.
   */
  @GetMapping("/vendedores") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("vendedores", vendedorDao.getVendedores());
    return "Administrador/Vendedor/Vendedores"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * 
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("vendedor")
  public Vendedor setUpUserForm() {
    return new Vendedor();
  }

  /**
   * Método que retorna una pagina para realizar el registro de un vendedor.
   * 
   * @return La página de registro de vendedores.
   */
  @GetMapping("/registrarVendedor") // Base
  public String registrarVendedor() {
    return "Administrador/Vendedor/RegistrarVendedor"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite guardar un vendedor
   * 
   * @param vendedor Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarVendedor", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public String guardarVendedor(@ModelAttribute("vendedor") Vendedor vendedor,
      Model model) {

    // Consulta si tiene todos los campos llenos
    if (vendedor.isValidoParaRegistrar()) {

      String mensaje = vendedorDao.registrarVendedor(vendedor);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Vendedor registrado con éxito.");
        model.addAttribute("vendedores", vendedorDao.getVendedores());
        return "Administrador/Vendedor/Vendedores"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Vendedor/RegistrarVendedor"; // Nombre del archivo jsp
      }

    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Vendedor/RegistrarVendedor"; // Nombre del archivo jsp
    }
  }

  /**
   * Método que obtiene la pagina de actualizar un vendedor dado un ID.
   * 
   * @param idVendedor Identificador del vendedor
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información del vendedor.
   */
  @GetMapping(value = "/actualizarVendedor")
  public String actualizarVendedor(@RequestParam("id") long idVendedor, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idVendedor <= 0) {
      model.addAttribute("vendedores", vendedorDao.getVendedores());
      return "Administrador/Vendedor/Vendedores"; // Nombre del archivo jsp
    }
    Vendedor vendedor = vendedorDao.obtenerVendedorPorId(idVendedor);
    model.addAttribute("vendedor", vendedor);
    return "Administrador/Vendedor/ActualizarVendedor"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite editar un vendedor.
   * 
   * @param vendedor Objeto con la información a editar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarVendedor")
  public String editarvendedor(@ModelAttribute("vendedor") Vendedor vendedor,
      Model model) {

    // Consulta si tiene todos los campos llenos
    if (vendedor.isValidoParaActualizar()) {

      String mensaje = vendedorDao.editarVendedor(vendedor);

      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "vendedor actualizado con éxito.");
        model.addAttribute("endedores", vendedorDao.getVendedores());
        return "Administrador/Vendedor/Vendedores"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return actualizarVendedor(vendedor.getCodigo(), model);
      }
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return actualizarVendedor(vendedor.getCodigo(), model);
    }
  }

  /**
   * Método que obtiene la pagina de eliminar un vendedor dado un ID.
   * 
   * @param idVendedor Identificador del vendedor
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información del vendedor.
   */
  @GetMapping(value = "/eliminarVendedor")
  public String eliminarVendedor(@RequestParam("id") long idVendedor, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idVendedor <= 0) {
      model.addAttribute("vendedores", vendedorDao.getVendedores());
      return "Administrador/Vendedor/Vendedores"; // Nombre del archivo jsp
    }
    Vendedor vendedor = vendedorDao.obtenerVendedorPorId(idVendedor);
    model.addAttribute("vendedor", vendedor);
    return "Administrador/Vendedor/EliminarVendedor"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar un vendedor.
   * 
   * @param vendedor Objeto con la información a eliminar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/borrarVendedor")
  public String borrarVendedor(@ModelAttribute("vendedor") Vendedor vendedor, Model model) {
    
      String mensaje = vendedorDao.eliminarVendedor(vendedor);
      if (mensaje.equals("Eliminacion exitosa")) {
        model.addAttribute("result", "Vendedor eliminado con éxito.");
        model.addAttribute("vendedores", vendedorDao.getVendedores());
        return "Administrador/Vendedor/Vendedores"; // Nombre del archivo jsp        
      } else {
        model.addAttribute("wrong", mensaje);
        return eliminarVendedor(vendedor.getCodigo(), model);
      }
  }
}
