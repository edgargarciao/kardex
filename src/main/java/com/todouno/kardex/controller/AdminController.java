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
import com.todouno.kardex.dao.LoginDao;
import com.todouno.kardex.dto.Login;
import com.todouno.kardex.util.JwtUtil;

@Controller
public class AdminController {

  @Autowired
  private SessionManager sessionManager;
  
  private JwtUtil jwtUtil;
  private LoginDao loginDao;

  public AdminController() {
    jwtUtil = new JwtUtil();
    loginDao = new LoginDao();
  }

  @GetMapping("/") // Base
  public String main(Model model) {
    return "Administrador/Login"; // Nombre del archivo jsp
  }

  /**
   * M�todo solicitado por los formularios de los archivos .jsp
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
   * M�todo para autenticar al usuario al usuario.
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
      // Consulto en base de datos si se encuentra ese correo y esa contrase�a
      String resultado = loginDao.authenticate(login.getCorreoEmpresarial(), login.getContrasena());

      // Si el resultado no es vacio es por que si existe ese correo y esa contrase�a
      if (!resultado.isEmpty()) {

        // Creo un Json Web Token para validar si la sesi�n esta activa
        String jwt = jwtUtil.generateToken(resultado, login.getCorreoEmpresarial());
     
        // Guardo el JWT como atributo de sesi�n
        request.getSession().setAttribute("token", jwt);

        // Guarda la sesion en el manejador de sesiones
        sessionManager.guardarSession("SESSION:" + login.getCorreoEmpresarial(), jwt);

        // Redirijo al index debido a que el usuario ya fue autenticado con exito
        return "Administrador/IndexAdmin";

      } else {

        /**
         * Guardo en una variable el mensaje de error indicando que el usuario o la contrase�a
         * fueron incorrectos debido a que no se encuentran en la base de datos y asi pueda ser
         * entendida por los archivos .JSP
         */
        model.addAttribute("wrong", "Usuario o contrase�a incorrectos.");
      }
      // Redirecciono al login debido a que la autenticaci�n fue incorrecta
      return "Administrador/Login";
    } else {
      /**
       * Guardo en una variable el mensaje de error indicando que el usuario o la contrase�a son
       * nulos siendo estos datos son obligatorios, y asi pueda ser entendida por los archivos .JSP
       */
      model.addAttribute("wrong", "El usuario y la contrase�a no pueden ser nulos.");
      // Redirecciono al login debido a que la autenticaci�n fue incorrecta
      return "Administrador/Login";
    }
  }

  @GetMapping("/indexAdmin") // Base
  public String indexAdmin(Model model) {
    return "Administrador/IndexAdmin"; // Nombre del archivo jsp
  }

  @GetMapping("/logout")
  private String getLogOut(String token, HttpServletRequest request) {
    request.getSession().invalidate();
    String correo = jwtUtil.parseToken(token);
    sessionManager.eliminarSesion("SESSION:" + correo);
    return "Administrador/Login"; // Nombre del archivo jsp
  }

}
