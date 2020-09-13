package com.todouno.kardex.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.todouno.kardex.config.SessionManager;
import com.todouno.kardex.constantes.Mensajes;
import com.todouno.kardex.dao.LoginDao;
import com.todouno.kardex.dao.ProductoDao;
import com.todouno.kardex.dao.TipoProductoDao;
import com.todouno.kardex.dao.UsuarioDao;
import com.todouno.kardex.dao.VendedorDao;
import com.todouno.kardex.dao.VentaDao;
import com.todouno.kardex.dto.Login;
import com.todouno.kardex.util.JwtUtil;

@Controller
public class AdminController {

  @Autowired
  private SessionManager sessionManager;

  private JwtUtil jwtUtil;
  private LoginDao loginDao;
  private TipoProductoDao tipoProductoDao;
  private ProductoDao productoDao;
  private VendedorDao vendedorDao;
  private VentaDao ventaDao;
  private UsuarioDao usuarioDao;

  public AdminController() {
    jwtUtil = new JwtUtil();
    loginDao = new LoginDao();
    tipoProductoDao = new TipoProductoDao();
    productoDao = new ProductoDao();
    vendedorDao = new VendedorDao();
    ventaDao = new VentaDao();
    usuarioDao = new UsuarioDao();
  }

  @GetMapping("/") // Base
  public String main(Model model) {
    return "Administrador/Login"; // Nombre del archivo jsp
  }

  /**
   * Mï¿½todo solicitado por los formularios de los archivos .jsp
   * <p>
   * Este metodo es usado en la etiqueta form de la siguiente manera: modelAttribute="login"
   * 
   * @return
   */
  @ModelAttribute("login")
  public Login setUpUserForm() {
    return new Login();
  }

  /**
   * Mï¿½todo para autenticar al usuario al usuario.
   * 
   * @param login Objeto con los datos de autenticacion
   * @param model Clase para enviar datos desde los servicios a los archivos .jsp
   * @param request Objeto con los datos de sesion que por el instante es nulo.
   * @return La pagina a donde fue redireccionado.
   */
  @PostMapping("/autenticar")
  public String authenticateUser(@ModelAttribute("login") Login login, Model model,
      HttpServletRequest request) {

    /*
     * Consulto si los datos no vienen nulos
     */
    if (!StringUtils.isEmpty(login.getCorreoEmpresarial())
        && !StringUtils.isEmpty(login.getContrasena())) {
      // Consulto en base de datos si se encuentra ese correo y esa
      // contraseï¿½a
      String resultado = loginDao.authenticate(login.getCorreoEmpresarial(), login.getContrasena());

      // Si el resultado no es vacio es por que si existe ese correo y esa
      // contraseï¿½a
      if (!resultado.isEmpty()) {

        // Creo un Json Web Token para validar si la sesiï¿½n esta
        // activa
        String jwt = jwtUtil.generateToken(resultado, login.getCorreoEmpresarial());

        // Guardo el JWT como atributo de sesiï¿½n
        request.getSession().setAttribute("token", jwt);

        // Guarda la sesion en el manejador de sesiones
        sessionManager.guardarSession("SESSION:" + login.getCorreoEmpresarial(), jwt);

        // Cargo las cantidades
        cargarRegistros(model);

        // Redirijo al index debido a que el usuario ya fue autenticado
        // con exito
        return "Administrador/IndexAdmin";

      } else {

        /**
         * Guardo en una variable el mensaje de error indicando que el usuario o la contraseï¿½a
         * fueron incorrectos debido a que no se encuentran en la base de datos y asi pueda ser
         * entendida por los archivos .JSP
         */
        model.addAttribute("wrong", Mensajes.USUARIOOCONTRASENAINCORRECTOS);
      }
      // Redirecciono al login debido a que la autenticaciï¿½n fue
      // incorrecta
      return "Administrador/Login";
    } else {
      /**
       * Guardo en una variable el mensaje de error indicando que el usuario o la contraseï¿½a son
       * nulos siendo estos datos son obligatorios, y asi pueda ser entendida por los archivos .JSP
       */
      model.addAttribute("wrong", Mensajes.USUARIOCONTRASEANULOS);
      // Redirecciono al login debido a que la autenticaciï¿½n fue
      // incorrecta
      return "Administrador/Login";
    }
  }

  @GetMapping("/indexAdmin") // Base
  public String indexAdmin(Model model) {

    cargarRegistros(model);
    return "Administrador/IndexAdmin"; // Nombre del archivo jsp
  }

  private void cargarRegistros(Model model) {
    model.addAttribute("catidadTipoProductos", tipoProductoDao.getCantidadRegistros());
    model.addAttribute("catidadProductos", productoDao.getCantidadRegistros());
    model.addAttribute("catidadVendedores", vendedorDao.getCantidadRegistros());
    model.addAttribute("catidadVentas", ventaDao.getCantidadRegistros());
  }

  @GetMapping("/logout")
  public String getLogOut(String token, HttpServletRequest request) {
    request.getSession().invalidate();
    String correo = jwtUtil.parseToken(token);
    sessionManager.eliminarSesion("SESSION:" + correo);
    return "Administrador/Login"; // Nombre del archivo jsp
  }

  @GetMapping("/recordar") // Base
  public String recordar() {
    return "Administrador/Recordar"; // Nombre del archivo jsp
  }

  @PostMapping("/recordarContrasena")
  public String recordarContrasena(@ModelAttribute("login") Login login, Model model,
      HttpServletRequest request) {
    
    if(login.getCorreoEmpresarial().equals("")) {
      model.addAttribute("wrong","Debes anotar por lo menos el correo.");
      return "Administrador/Recordar";
    }else {
      String mensaje = usuarioDao.enviarCorreo(login.getCorreoEmpresarial());
      if(mensaje.equals("Actualizacion")) {
        model.addAttribute("result","ContraseÃ±a recuparada con Éxito");
        return "Administrador/Login";
      }else {
        model.addAttribute("wrong",mensaje);
        return "Administrador/Recordar";
      }
    }

}

}
