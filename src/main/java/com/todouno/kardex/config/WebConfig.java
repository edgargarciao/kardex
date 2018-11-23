package com.todouno.kardex.config;

import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

/**
 * Clase que permite realizar la configuración web del sistema.
 * 
 * @author edgar
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.todouno.kardex.*"})
public class WebConfig extends WebMvcConfigurerAdapter {

  /*@Bean
  public JavaMailSender getJavaMailSender() {
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
      mailSender.setHost("smtp.gmail.com");
      mailSender.setPort(587);
       
      mailSender.setUsername("edgar.yesid.garcia.ortiz@gmail.com");
      mailSender.setPassword("94100209440");
       
      Properties props = mailSender.getJavaMailProperties();
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.debug", "true");
       
      return mailSender;
  }*/
  

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  /*@Bean
  public ViewResolver resourceBundleViewResolver() {
    ResourceBundleViewResolver viewResolver = new ResourceBundleViewResolver();
    viewResolver.setBasename("views");
    viewResolver.setOrder(1);
    return viewResolver;
  }*/

  @Bean
  public InternalResourceViewResolver resolver() {
    // 2. Registra los jsp
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setViewClass(JstlView.class);
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    //resolver.setOrder(2);
    return resolver;
  }



  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 3. Registrar los Recursos (Css,JS,font,entre otros)
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    registry.addResourceHandler("/servicios/resources/**").addResourceLocations("/resources/");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // Mapea la clase para la seguridad
    registry.addInterceptor(getSessionManager()).addPathPatterns("/**")
        .excludePathPatterns("/resources/**", "/admin", "/autenticar", "/recordar","/recordarContraseña", "/servicios/*", "/");
  }

  @Bean
  public SessionManager getSessionManager() {
    return new SessionManager();
  }

  @Bean(name = "multipartResolver")
  public StandardServletMultipartResolver resolvermu() {
    return new StandardServletMultipartResolver();
  }

}

