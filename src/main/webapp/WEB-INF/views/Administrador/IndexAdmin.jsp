<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
	<%@ include file = "General/css.jsp" %>
</head>
<body>

   <!-- Left Panel -->

  <%@ include file = "General/LeftPanel.jsp" %>

    <!-- Left Panel -->
 	
    <!-- Right Panel -->

    <div id="right-panel" class="right-panel">

    	<!-- Header-->
        <header id="header" class="header">

        	<div class="header-menu">
            	<div class="col-sm-7">
                	<a id="menuToggle" class="menutoggle pull-left">
                		<i class="fa fa fa-tasks">
                		</i>
                	</a>
                    <div class="page-header float-left">
                    	<div class="page-title">
                        	<ol class="breadcrumb text-right">
                            	<li class="active">
                            		<a href="#">Panel de control</a>
                            	</li>                                            
	   						</ol>
						</div>
					</div>    
				</div>
				<%@ include file = "General/Configuracion.jsp" %>
			</div>
    
		</header><!-- /header -->
    

            <div class="breadcrumbs">
                <div class="col-sm-4">
                    <div class="page-header float-left">
                        <div class="page-title">
                            <h1>Panel de control</h1>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="page-header float-right">
                        <div class="page-title">
                            <ol class="breadcrumb text-right">
                                 
                                 <a class="btn btn-primary" href="${contextPath}/generarInforme">Descargar informe de conteo</a>
                               
                            </ol>
                        </div>
                    </div>
                </div>
            </div>

        <div class="content mt-3">

            <div class="col-lg-3 col-md-6">
                <div class="social-box google-plus">
                    <i class="fa fa-list"></i>
                            <strong><span class="count">${catidadCategorias}</span></strong>
                            <span>Vendedores</span>
                    </div>
                <!--/social-box-->
            </div><!--/.col--> 


            <div class="col-lg-3 col-md-6">
                <div class="social-box google-plus">
                    <i class="fa fa-list-ol"></i>
                            <strong><span class="count">${catidadSubCategorias}</span></strong>
                            <span>Productos</span>
                    </div>
                <!--/social-box-->
            </div><!--/.col--> 


            <div class="col-lg-3 col-md-6">
                <div class="social-box google-plus">
                    <i class="fa fa-edit"></i>
                            <strong><span class="count">${catidadContenidos}</span></strong>
                            <span>Ventas</span>
                    </div>
                <!--/social-box-->
            </div><!--/.col--> 
           

        </div> <!-- .content -->
    </div><!-- /#right-panel -->

    <!-- Right Panel -->

	<!-- Carga de los archivos Javascript -->
	<%@ include file = "General/scripts.jsp" %>
	
   
</body>
</html>
