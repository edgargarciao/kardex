package com.todouno.kardex.dao;

import java.util.Properties;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.todouno.kardex.data.DataMgr;

public class UsuarioDao {

  private DataMgr dataMgr;
  private JavaMailSenderImpl javaMailSender;

  public UsuarioDao() {
    dataMgr = new DataMgr();
    javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setPort(587);
    javaMailSender.setProtocol("smtp");    
    javaMailSender.setUsername("testtodouno@gmail.com");
    javaMailSender.setPassword("Todouno1234");
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "starttls");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.user", "");
    props.put("mail.smtp.password", "Todouno1234");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    javaMailSender.setJavaMailProperties(props);
  }

  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * 
   * @return La cantidad de actividades registradas.
   */
  public String enviarCorreo(String correo) {
    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("correo", correo);
    SqlRowSet sqlRowSet = dataMgr.executeQuery(
        " SELECT * FROM usuario where correo = :correo", map);

    String pass = "";
    if (sqlRowSet.next()) {
      pass = sqlRowSet.getString("contrasena");
    }

    String mensaje = "El correo no esta registrado en el sistema. Contacte al administrador.";
    if (!pass.equals("")) {

      String subject = "Recuperacion de credencial TODO1";
      String body = "Su password es : " + pass;

      SimpleMailMessage smm = new SimpleMailMessage();

      smm.setFrom("testtodouno@gmail.com");
      smm.setTo(correo);
      smm.setSubject(subject);
      smm.setText(body);
      try {
        javaMailSender.send(smm);
        mensaje = "Actualizacion";
      }catch(Exception e) {
    	e.printStackTrace();
        mensaje = "Correo no autorizado. Debes permitir el acceso de aplicaciones no seguras en la configuracion de google.";
      }
      
    }
    return mensaje;
  }
}
